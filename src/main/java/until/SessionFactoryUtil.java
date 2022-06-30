package until;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class SessionFactoryUtil {
    private static final SessionFactory INSTANCE;

    static {
        Configuration configuration = new Configuration();
        INSTANCE = configuration.configure().buildSessionFactory();
    }

    private SessionFactoryUtil() {

    }

    public static SessionFactory getInstance() {
        return INSTANCE;
    }

    public static void close() {
        INSTANCE.close();
    }
}
