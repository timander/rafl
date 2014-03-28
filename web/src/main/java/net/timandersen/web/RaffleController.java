package net.timandersen.web;

import net.timandersen.DateProvider;
import net.timandersen.model.domain.Raffle;
import net.timandersen.repository.RaffleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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
    public String createRaffle(HttpServletRequest request) throws ParseException {
        String cause = request.getParameter("cause");
        Raffle raffle = new Raffle();
        raffle.setCause(cause);
        raffle.setStartDate(dateProvider.now());
        repository.save(raffle);
        return "redirect:/drawing/admin/raffle/" + raffle.getId();
    }

    @RequestMapping(value = "/admin/raffle", method = RequestMethod.GET)
    public ModelAndView listActiveRaffles(@PathVariable String region, @PathVariable String callHistoryId, HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/admin/raffle/{raffleId}", method = RequestMethod.GET)
    public ModelAndView showRaffle(@PathVariable String raffleId, HttpServletRequest request) {
        Raffle raffle = repository.findById(new Integer(raffleId));
        return new ModelAndView("raffle_show", "raffle", raffle);
    }

}
