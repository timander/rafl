package net.timandersen.repository;

import net.timandersen.model.domain.Raffle;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RaffleRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Raffle> findAll() {
        sessionFactory.getCurrentSession().beginTransaction();
        Query query = sessionFactory.getCurrentSession().createQuery("from Raffle");
        return (List<Raffle>) query.list();
    }

    public void save(Raffle entity) {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(entity);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public void delete(Raffle entity) {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().delete(entity);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public Raffle findById(Integer raffleId) {
        sessionFactory.getCurrentSession().beginTransaction();
        Query query = sessionFactory.getCurrentSession().createQuery("from Raffle where id = :id");
        query.setInteger("id", raffleId);
        List<Raffle> list = query.list();
        return firstOrNull(list);
    }

    private Raffle firstOrNull(List<Raffle> raffles) {
        return raffles.isEmpty() ? null : raffles.get(0);
    }
}
