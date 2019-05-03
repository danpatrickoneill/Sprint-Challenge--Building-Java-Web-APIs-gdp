package com.lambdaschool.gdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GDPApplication
{

    public static GDPList appGDPList;


    public static void main(String[] args)
    {
        appGDPList = new GDPList();
        SpringApplication.run(GDPApplication.class, args);
    }

}
