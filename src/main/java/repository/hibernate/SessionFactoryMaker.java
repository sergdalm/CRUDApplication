package repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import until.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SessionFactoryMaker {
    FACTORY;
    private final SessionFactory sessionFactory;



    SessionFactoryMaker() {
        Configuration configuration = new Configuration();
        configuration.configure();

        sessionFactory = configuration
//                .addClass(model.Writer.class)
//                .addClass(model.Post.class)
//                .addClass(model.Label.class)
                .buildSessionFactory();

//
//        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.properties")
//                .build();
//        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
//        metadataSources.addAnnotatedClass(model.Writer.class);
//        metadataSources.addAnnotatedClass(model.Post.class);
//        metadataSources.addAnnotatedClass(model.Label.class);
//        Metadata metadata = metadataSources.getMetadataBuilder().build();
//
//
//        this.sessionFactory = metadata.getSessionFactoryBuilder().build();

//
//        String properties = "hibernate.properties";
//        InputStream inputStream = this.getClass().getClassLoader().
//                getResourceAsStream(properties);
//        InputStream inputStreamVar2 = ClassLoader.getSystemClassLoader().getResourceAsStream(properties);
//        Properties hibernateProperties = new Properties();
//
//
//
//        try {
//            hibernateProperties.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Configuration configuration = new Configuration();
//
//        // непонятно на сколько нужен serviceRegistry
//        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties()).build();
//
//        sessionFactory = configuration
//                .setProperties(hibernateProperties)
//                .addClass(model.Writer.class)
//                .addClass(model.Post.class)
//                .addClass(model.Label.class)
//                        .buildSessionFactory(serviceRegistry);
    }

//    Properties propertyLoad() {
//        Properties properties = null;
//        if (properties == null) {
//            properties = new Properties();
//            try {
//                properties.load(PropertiesUtil.class
//                        .getResourceAsStream("hibernate.properties"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return properties;
//    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
