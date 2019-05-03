package com.lambdaschool.gdp.controller;

import com.lambdaschool.gdp.GDPApplication;
import com.lambdaschool.gdp.exceptions.ResourceNotFoundException;
import com.lambdaschool.gdp.model.GDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/data")
public class GDPController
{
    private static final Logger logger = LoggerFactory.getLogger(GDPController.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss 'on' yyyy-MM-dd");

    // localhost:8081/gdp/data/names
    @GetMapping(value="/names", produces = "application/json")
    public ResponseEntity<?> getGDPsAlphaSort()
    {
        logger.info(String.format("/names accessed at %s.",  simpleDateFormat.format(new Date())));
        GDPApplication.appGDPList.gdpList.sort((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        return new ResponseEntity<>(GDPApplication.appGDPList.gdpList, HttpStatus.OK);
    }

    //localhost:8081/gdp/data/economy
    @GetMapping(value="/economy", produces = "application/json")
    public ResponseEntity<?> getGDPsDescendingSort()
    {
        logger.info(String.format("/economy accessed at %s.",  simpleDateFormat.format(new Date())));
        GDPApplication.appGDPList.gdpList.sort((g1, g2) -> Integer.parseInt(g2.getGDP()) - Integer.parseInt(g1.getGDP()));
        return new ResponseEntity<>(GDPApplication.appGDPList.gdpList, HttpStatus.OK);
    }

    //localhost:8081/gdp/data/gdp/{name}
    @GetMapping(value="/gdp/{name}", produces = "application/json")
    public ResponseEntity<?> getSingleGDPByName(@PathVariable String name)
    {
        String userParams = name;
        GDP result;

        logger.info(String.format("/gdp/%s accessed at %s.", userParams, simpleDateFormat.format(new Date())));
        result = GDPApplication.appGDPList.findGDP(gdp -> gdp.getName().toLowerCase().equals(name.toLowerCase()));
        if (result == null)
        {
            throw new ResourceNotFoundException(String.format("Couldn't find GDP data for country with name: %s", userParams));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //localhost:8081/gdp/data/country/{id}
    @GetMapping(value="/country/{id}", produces = "application/json")
    public ResponseEntity<?> getSingleGDPById(@PathVariable long id)
    {
        String userParams = String.valueOf(id);
        GDP result;

        logger.info(String.format("/gdp/%s accessed at %s.", userParams, simpleDateFormat.format(new Date())));
        result = GDPApplication.appGDPList.findGDP(gdp -> gdp.getId() == id);
        if (result == null)
        {
            throw new ResourceNotFoundException(String.format("Couldn't find GDP data for country with id: %s", userParams));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //localhost:8081/gdp/data/country/stats/median
    @GetMapping(value="/country/stats/median", produces = "application/json")
    public ResponseEntity<?> getMedianGDP()
    {
        GDP result;
        int median = GDPApplication.appGDPList.gdpList.size() / 2;

        logger.info(String.format("/gdp/stats/median accessed at %s.", simpleDateFormat.format(new Date())));
        // Make sure things are currently sorted by GDP
        GDPApplication.appGDPList.gdpList.sort((g1, g2) -> Integer.parseInt(g1.getGDP()) - Integer.parseInt(g2.getGDP()));
        result = GDPApplication.appGDPList.gdpList.get(median);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // *** SERVER-SIDE RENDERING ENDPOINTS ***

    // Displays GDP-sorted table
    //localhost:8081/gdp/data/economy/table
    @GetMapping(value="/economy/table")
    public ModelAndView displayGDPTable() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("gdp");
        GDPApplication.appGDPList.gdpList.sort((g1, g2) -> Integer.parseInt(g2.getGDP()) - Integer.parseInt(g1.getGDP()));
        mav.addObject("gdpList", GDPApplication.appGDPList.gdpList);

        return mav;
    }

    // Displays GDP-sorted table of qualifying (greater-than) GDPs
    // //localhost:8081/gdp/data/economy/greatest/{GDP}
    @GetMapping(value="/economy/greatest/{GDP}")
    public ModelAndView displayQualifyingGDPTable(@PathVariable int GDP) {

        ModelAndView mav = new ModelAndView();
        ArrayList<GDP> result;

        mav.setViewName("gdp");
        result = GDPApplication.appGDPList.findGDPs(gdp -> Integer.parseInt(gdp.getGDP()) > GDP);
        result.sort((g1, g2) -> Integer.parseInt(g2.getGDP()) - Integer.parseInt(g1.getGDP()));
        if (result.isEmpty())
        {
            throw new ResourceNotFoundException(String.format("Couldn't find GDP data for countries with GDP greater than %d", GDP));
        }

            mav.addObject("gdpList", result);

            return mav;

    }
}
