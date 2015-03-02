package net.timandersen.repository;

import net.timandersen.enums.Gender;
import net.timandersen.model.domain.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-contexts/test-context.xml")
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.execute("delete from ticket");
        jdbcTemplate.execute("delete from player");
        jdbcTemplate.execute("delete from raffle");
    }

    @Test
    public void saveFindDelete() {
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
        Player player = new Player("John", "Doe", Gender.MALE, "johndoe@johndoe.com");
        repository.save(player);
        Player persistedPlayer = repository.findAll().get(0);
        assertEquals("John", persistedPlayer.getFirstName());
        assertEquals("Doe", persistedPlayer.getLastName());
        assertEquals("johndoe@johndoe.com", persistedPlayer.getEmail());
        assertEquals(Gender.MALE, persistedPlayer.getGender());
        deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private void deleteAll() {
        for (Player player : repository.findAll()) {
            repository.delete(player);
        }
    }
}
