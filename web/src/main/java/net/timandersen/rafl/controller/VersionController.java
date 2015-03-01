package net.timandersen.rafl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/version")
public class VersionController {

    @Autowired
    @Qualifier("version")
    String version;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String showVersion() {
        return version;
    }

}
