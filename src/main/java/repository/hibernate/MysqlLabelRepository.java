package repository.hibernate;

import model.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.LabelRepository;
import until.SessionFactoryUtil;

import java.util.List;

public class MysqlLabelRepository implements LabelRepository {
    private final static LabelRepository INSTANCE = new MysqlLabelRepository();
    private static final SessionFactory sessionFactory = SessionFactoryUtil.getInstance();

    private MysqlLabelRepository() {
    }

    @Override
    public Label getById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Label label = session.get(Label.class, id);
            session.close();
            return label;
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
    public Label save(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int id = (int) session.save(label);
            transaction.commit();
            return session.get(Label.class, id);
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
    public Label update(Label label) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Label oldLabel = session.get(Label.class, label.getId());
            session.evict(oldLabel);
            oldLabel.update(label);
            session.update(oldLabel);
            transaction.commit();
            return session.get(Label.class, label.getId());
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
    public List<Label> getAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Label").list();
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
    public boolean deleteById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Label label = new Label();
            label.setId(id);
            session.delete(label);
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

    @Override
    public void matchLabelWithPost(Label label, Integer postId) {
//        try (var preparedStatement =
//                     ConnectionManager.getPreparedStatement(MATCH_LABEL_WITH_POST)) {
//            preparedStatement.setInt(1, label.getId());
//            preparedStatement.setInt(2, postId);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<Label> getLabelsByPostId(Integer postId) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = null;
//        try {
////            transaction = session.beginTransaction();
//            Query<Label> query = session.createQuery("FROM Label l WHERE l.id = :").list();
//            query.setParameter("email", email);
//            Writer writer = query.getSingleResult();
////            transaction.commit();
//            return Optional.ofNullable(writer);
//        } catch (Exception e) {
//            e.printStackTrace();
////            if (transaction != null) {
////                transaction.rollback();
////            }
//        } finally {
//            if (session.isOpen()) {
//                session.close();
//            }
//        }
//        return Optional.empty();
        return null;
    }

    public static LabelRepository getInstance() {
        return INSTANCE;
    }
}
