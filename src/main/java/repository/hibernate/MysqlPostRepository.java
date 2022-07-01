package repository.hibernate;

import model.Label;
import model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.PostRepository;

import java.util.List;

public class MysqlPostRepository implements PostRepository {
    private final static PostRepository INSTANCE = new MysqlPostRepository();
    private static final SessionFactory sessionFactory = SessionFactoryMaker.FACTORY.getSessionFactory();

    private MysqlPostRepository() {
    }

    @Override
    public Post getById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Post post = session.get(Post.class, id);
            session.close();
            return post;
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
    public Post save(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int id = (int) session.save(post);
            transaction.commit();
            return session.get(Post.class, id);
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
    public Post update(Post post) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Post oldPost = session.get(Post.class, post.getId());
            session.evict(oldPost);
            oldPost.update(post);
            session.update(post);
            transaction.commit();
            return session.get(Post.class, post.getId());
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
    public List<Post> getAll() {
        Session session = sessionFactory.openSession();
        try {
            List<Post> posts = session.createQuery("FROM Post").list();
            for (Post post : posts) {
                List<Label> labels = post.getLabels();
            }
            return posts;
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
    public List<Post> getPostsByWriterId(Integer writerId) {
        return MysqlPostRepository.getInstance().getPostsByWriterId(writerId);
    }

    @Override
    public boolean deleteById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Post post = new Post();
            post.setId(id);
            session.delete(post);
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
    public void matchPostWithWriter(Integer postId, Integer writerId) {
    }

    @Override
    public void matchPostWithLabels(Integer postId, List<Label> labels) {

    }

    public static PostRepository getInstance() {
        return INSTANCE;
    }

}
