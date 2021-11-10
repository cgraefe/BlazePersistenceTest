package net.graefe.test;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import net.graefe.test.entity.Cat;
import net.graefe.test.entity.CatFood;
import net.graefe.test.entity.CatView;
import net.graefe.test.entity.CatView2;
import net.graefe.test.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BlazeEntityViews
{

    /**
     * Setup test data fixture.
     */
    public void setup()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        CatFood food1 = new CatFood(1L, 23L, "Whiskas");
        CatFood food2 = new CatFood(2L, 1L, "Purina");

        session.save(food1);
        session.save(food2);

        session.save(new Cat(1L, "Lucy", food1));
        session.save(new Cat(2L, "Tom", food2));
        session.save(new Cat(3L, "Kitty", null));

        transaction.commit();
    }

    /**
     * When fetching the Cat entity, the @ManyToOne mapping works.
     */
    public void queryEntity()
    {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        CriteriaBuilderFactory cbf = HibernateUtil.getCriteriaBuilderFactory();

        Cat cat = cbf.create(em, Cat.class).where("id").eq(2L).getSingleResult();
        assertThat(cat.getName(), is("Tom"));
        assertThat(cat.getFavoriteFood().getBrand(), is("Purina"));
    }

    /**
     * When fetching the CatView with mapped CatFood entity instead getFavoriteFood() returns null, although it should not.
     */
    public void queryEntityView()
    {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        CriteriaBuilderFactory cbf = HibernateUtil.getCriteriaBuilderFactory();

        EntityViewManager evm = HibernateUtil.getEntityViewManager();
        CriteriaBuilder<Cat> cb = cbf.create(em, Cat.class).where("id").eq(2L);
        CriteriaBuilder<CatView> viewBuilder = evm.applySetting(EntityViewSetting.create(CatView.class), cb);
        CatView catView = viewBuilder.getSingleResult();
        assertThat(catView.getFavoriteFood(), is(not(nullValue())));
        assertThat(catView.getFavoriteFood().getBrand(), is("Purina"));
    }


    /**
     * When fetching the CatView with mapped CatFoodView getFavoriteFood() returns the expected non-null result.
     */
    public void queryEntityView2()
    {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        CriteriaBuilderFactory cbf = HibernateUtil.getCriteriaBuilderFactory();

        EntityViewManager evm = HibernateUtil.getEntityViewManager();
        CriteriaBuilder<Cat> cb = cbf.create(em, Cat.class).where("id").eq(2L);
        CriteriaBuilder<CatView2> viewBuilder = evm.applySetting(EntityViewSetting.create(CatView2.class), cb);
        CatView2 catView = viewBuilder.getSingleResult();
        assertThat(catView.getFavoriteFood(), is(not(nullValue())));
        assertThat(catView.getFavoriteFood().getBrand(), is("Purina"));
    }

    public void run()
    {
        setup();

        queryEntity();
        queryEntityView2();
        queryEntityView();
    }

    public static void main(String[] args)
    {
        BlazeEntityViews blazeEntityViews = new BlazeEntityViews();
        blazeEntityViews.run();
    }
}
