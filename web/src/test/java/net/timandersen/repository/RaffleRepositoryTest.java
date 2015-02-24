package net.timandersen.repository;

import net.timandersen.DateProvider;
import net.timandersen.model.domain.Raffle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:contexts/application-context.xml")
public class RaffleRepositoryTest {

    @Autowired
    private RaffleRepository repository;

    @Test
    public void saveFindDelete() {
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
        Raffle raffle = new Raffle("Band Uniforms", new DateProvider().now());
        repository.save(raffle);
        Raffle bandRaffle = repository.findAll().get(0);
        assertEquals("Band Uniforms", bandRaffle.getCause());
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private void deleteAll() {
        for (Raffle raffle : repository.findAll()) {
            repository.delete(raffle);
        }
    }
}
