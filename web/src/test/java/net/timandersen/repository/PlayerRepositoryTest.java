package net.timandersen.repository;

import net.timandersen.enums.Gender;
import net.timandersen.model.domain.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:contexts/application-context.xml")
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository repository;

    @Test
    public void saveFindDelete() {
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
        Player player = new Player("John", "Doe", Gender.MALE, "johndoe@johndoe.com", 1);
        repository.save(player);
        Player persistedPlayer = repository.findAll().get(0);
        assertEquals("Doe", persistedPlayer.getLastName());
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private void deleteAll() {
        for (Player player : repository.findAll()) {
            repository.delete(player);
        }
    }
}
