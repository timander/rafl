package net.timandersen.repository;

import net.timandersen.model.domain.Player;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PlayerRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Player> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Player");
        return (List<Player>) query.list();
    }

    public void save(Player entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public void delete(Player entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public Player findById(Integer playerId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Player where id = :id");
        query.setInteger("id", playerId);
        List<Player> list = query.list();
        return firstOrNull(list);
    }

    private Player firstOrNull(List<Player> players) {
        return players.isEmpty() ? null : players.get(0);
    }
}
