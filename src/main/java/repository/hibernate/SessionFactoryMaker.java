package repository.hibernate;

import model.Label;
import model.Post;
import model.Writer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SessionFactoryMaker {
    FACTORY;
    private final SessionFactory sessionFactory;

    SessionFactoryMaker() {
        Configuration configuration = new Configuration();
        String properties = "hibernate.properties";
        InputStream inputStream = this.getClass().getClassLoader().
                getResourceAsStream(properties);
        Properties hibernateProperties = new Properties();
        try {
            hibernateProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setProperties(hibernateProperties);
        configuration.addAnnotatedClass(Writer.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(Label.class);
        sessionFactory = configuration
                .addAnnotatedClass(Writer.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(Label.class)
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
