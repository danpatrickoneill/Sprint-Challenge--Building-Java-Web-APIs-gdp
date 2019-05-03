package com.lambdaschool.gdp.controller;

import com.lambdaschool.gdp.GDPApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/data")
public class GDPController
{
    private static final Logger logger = LoggerFactory.getLogger(GDPController.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss 'on' yyyy-MM-dd");

    // localhost:8081/gdp/data/names
    @GetMapping(value="/names", produces = "application/json")
    public ResponseEntity<?> getAllGDPs()
    {
        logger.info(String.format("/names access at %s.",  simpleDateFormat.format(new Date())));
        GDPApplication.appGDPList.gdpList.sort((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        return new ResponseEntity<>(GDPApplication.appGDPList.gdpList, HttpStatus.OK);
    }

}
