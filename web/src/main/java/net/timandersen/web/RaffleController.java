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
import java.util.List;

@Controller
public class RaffleController {

    @Autowired
    private RaffleRepository repository;

    @Autowired
    private DateProvider dateProvider;


    @RequestMapping(value = "/raffle/new", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("raffle_new", "raffle", new Raffle());
    }

    @RequestMapping(value = "/raffle/edit/{raffleId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String raffleId) {
        Raffle raffle = repository.findById(Long.valueOf(raffleId));
        return new ModelAndView("raffle_show", "raffle", raffle);
    }

    @RequestMapping(value = "/raffle/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws ParseException {
        String cause = request.getParameter("cause");
        Raffle raffle = new Raffle();
        raffle.setCause(cause);
        raffle.setStartDate(dateProvider.now());
        repository.save(raffle);
        return "redirect:/drawing/raffle/" + raffle.getId();
    }

    @RequestMapping(value = "/raffle/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        List<Raffle> raffles = repository.findAll();
        return new ModelAndView("raffle_list", "raffles", raffles);
    }

    @RequestMapping(value = "/raffle/{raffleId}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable String raffleId, HttpServletRequest request) {
        Raffle raffle = repository.findById(Long.valueOf(raffleId));
        return new ModelAndView("raffle_show", "raffle", raffle);
    }

}
