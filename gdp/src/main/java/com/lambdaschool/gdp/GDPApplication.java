package com.lambdaschool.gdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GDPApplication
{

    public static GDPList appGDPList;


    public static void main(String[] args)
    {
        appGDPList = new GDPList();
        ApplicationContext ctx = SpringApplication.run(GDPApplication.class, args);

        DispatcherServlet dispatcherServlet =(DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}
