package net.timandersen.web;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static junit.framework.Assert.assertEquals;

public class RaffleControllerTest {

    @Test
    public void newRaffle() throws Exception {
        RaffleController controller = new RaffleController();
        ModelAndView modelAndView = controller.create();
        assertEquals("raffle_new", modelAndView.getViewName());
    }

}
