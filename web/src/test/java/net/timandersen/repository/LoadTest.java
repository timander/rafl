package net.timandersen.repository;

import net.timandersen.enums.Gender;
import net.timandersen.model.domain.Player;
import net.timandersen.model.domain.Raffle;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:contexts/application-context.xml")
public class LoadTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RaffleRepository raffleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Raffle raffle;

    @Before
    public void setUp() throws SQLException {
        jdbcTemplate.execute("delete from ticket");
        jdbcTemplate.execute("delete from player");
        jdbcTemplate.execute("delete from raffle");

        raffle = new Raffle("load test", new Date());
        raffleRepository.save(raffle);
    }


    @Test
    public void generateLotsOfTickets() throws Exception {
        for (int i = 0; i < 10; i++) {
            insertRecordsWithThreadCount(20);
        }
    }


    public void insertRecordsWithThreadCount(final int threadCount) throws Exception {
        Callable<Long> task = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                for (int i = 0; i < 10; i++) {
                    Player player = new Player(randomString(), randomString(), randomGender(), null);
                    player.buyTicketFor(raffle);
                    session.save(player);
                }
                transaction.commit();
                session.close();
                return 0L;
            }
        };

        List<Callable<Long>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        executorService.invokeAll(tasks);
    }

    private Gender randomGender() {
        return new Random().nextBoolean() ? Gender.MALE : Gender.FEMALE;
    }

    private String randomString() {
        return RandomStringUtils.random(new Random().nextInt(15) + 5, true, false);
    }

}
