package com.example.controller;

import com.example.model.SmartPhone;
import com.example.service.SmartPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/smartphones")
public class HomeController {
    @Autowired
    private SmartPhoneService smartPhoneService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createSmartphonePage() {
        ModelAndView mav = new ModelAndView( "/new-phone" );
        mav.addObject( "sPhone", new SmartPhone() );
        return mav;
    }

    @RequestMapping(value = "/createnewPhone", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SmartPhone createSmartphone(@RequestBody SmartPhone smartphone) {
        return smartPhoneService.save( smartphone );
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<SmartPhone> allPhones() {
        return smartPhoneService.findAll();
    }

    @GetMapping("")
    public ModelAndView allPhonesPage() {
        ModelAndView modelAndView = new ModelAndView( "/all-phones" );

        modelAndView.addObject( "allPhones", allPhones() );
        return modelAndView;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SmartPhone deleteSmartphone(@PathVariable Integer id){
        return smartPhoneService.deleteById( id );
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editSmartphonePage(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("/edit-phone");
        SmartPhone smartphone = smartPhoneService.findById(id);
        mav.addObject("sPhone", smartphone);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SmartPhone editSmartphone(@PathVariable int id, @RequestBody SmartPhone smartphone) {
        smartphone.setId(id);
        return smartPhoneService.save(smartphone);
    }
}
