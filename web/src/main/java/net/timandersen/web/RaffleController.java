package net.timandersen.web;

import net.timandersen.DateProvider;
import net.timandersen.model.domain.Raffle;
import net.timandersen.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RaffleController {

    @Autowired
    private RaffleRepository repository;

    @Autowired
    private DateProvider dateProvider;


    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public ModelAndView newRaffle() {
        return new ModelAndView("raffle_new", "raffle", new Raffle());
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public ModelAndView createRaffle(HttpServletRequest request) {
        String cause = request.getParameter("cause");
        String startDate = request.getParameter("startDate");
        System.out.println("cause = " + cause);
        System.out.println("startDate = " + startDate);
        Raffle raffle = new Raffle();
        raffle.setCause((String) request.getAttribute("cause"));
        raffle.setStartDate(dateProvider.now());
        repository.save(raffle);
        return new ModelAndView("raffle_show", "raffle", raffle);
    }

    @RequestMapping(value = "/admin/raffle", method = RequestMethod.GET)
    public ModelAndView listActiveRaffles(@PathVariable String region, @PathVariable String callHistoryId, HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/admin/raffle/{raffleId}", method = RequestMethod.GET)
    public ModelAndView showRaffle(@PathVariable String raffleId, HttpServletRequest request) {
        return null;
    }

}
