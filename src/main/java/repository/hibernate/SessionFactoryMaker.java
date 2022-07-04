package repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SessionFactoryMaker {
    FACTORY;
    private final SessionFactory sessionFactory;

    SessionFactoryMaker() {
        String properties = "hibernate.properties";
        InputStream inputStream = this.getClass().getClassLoader().
                getResourceAsStream(properties);
        InputStream inputStreamVar2 = ClassLoader.getSystemClassLoader().getResourceAsStream(properties);
        Properties hibernateProperties = new Properties();
        try {
            hibernateProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration configuration = new Configuration();
        sessionFactory = configuration
                .setProperties(hibernateProperties)
                .addAnnotatedClass(model.Writer.class)
                .addAnnotatedClass(model.Post.class)
                .addAnnotatedClass(model.Label.class)
                        .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
