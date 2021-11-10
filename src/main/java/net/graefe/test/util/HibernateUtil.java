package net.graefe.test.util;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import net.graefe.test.entity.CatFoodView;
import net.graefe.test.entity.CatView;
import net.graefe.test.entity.CatView2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil
{
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static CriteriaBuilderFactory criteriaBuilderFactory;
    private static EntityViewManager entityViewManager;

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static CriteriaBuilderFactory getCriteriaBuilderFactory()
    {
        if (criteriaBuilderFactory == null) {
            CriteriaBuilderConfiguration config = Criteria.getDefault();
            criteriaBuilderFactory = config.createCriteriaBuilderFactory(getSessionFactory());
        }
        return criteriaBuilderFactory;
    }

    public static EntityViewManager getEntityViewManager()
    {
        if (entityViewManager == null) {
            EntityViewConfiguration cfg = EntityViews.createDefaultConfiguration();
            cfg.addEntityView(CatFoodView.class);
            cfg.addEntityView(CatView.class);
            cfg.addEntityView(CatView2.class);
            entityViewManager = cfg.createEntityViewManager(criteriaBuilderFactory);
        }
        return entityViewManager;
    }

    public static void shutdown()
    {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
