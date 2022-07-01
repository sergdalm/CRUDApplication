package repository.hibernate;


import model.Post;
import model.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.WriterRepository;

import java.util.List;
import java.util.Optional;

public class MysqlWriterRepository implements WriterRepository {

    private static final WriterRepository INSTANCE = new MysqlWriterRepository();
    private static final SessionFactory sessionFactory = SessionFactoryMaker.FACTORY.getSessionFactory();

    private MysqlWriterRepository() {
    }

    @Override
    public Writer getById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Writer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Writer save(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int id = -1;
        try {
            transaction = session.beginTransaction();
            id = (int) session.save(writer);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        if (id < 0)
            return null;
        return session.get(Writer.class, id);
    }

    @Override
    public Writer update(Writer writer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Writer oldWriter = session.get(Writer.class, writer.getId());
            session.evict(oldWriter);
            oldWriter.update(writer);
            session.update(oldWriter);
            transaction.commit();
            return session.get(Writer.class, writer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public List<Writer> getAll() {
        Session session = sessionFactory.openSession();
        try {
            List<Writer> writers = session.createQuery("FROM Writer").list();
            for (Writer writer : writers) {
                List<Post> posts = writer.getPosts();
            }
            return writers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Optional<Writer> getWriterByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            Query<Writer> query = session.createQuery("FROM Writer w WHERE w.email = :email");
            query.setParameter("email", email);
            Writer writer = query.getSingleResult();
            return Optional.ofNullable(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Writer writer = new Writer();
            writer.setId(id);
            session.delete(writer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return false;
    }

    public List<Post> getPostsByWriterId(Integer writerId) {
        Session session = sessionFactory.openSession();
        try {
            Writer writer = session.get(Writer.class, writerId);
            return writer.getPosts();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public static WriterRepository getInstance() {
        return INSTANCE;
    }

}
