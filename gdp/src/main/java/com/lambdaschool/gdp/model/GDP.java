package com.lambdaschool.gdp.model;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static final AtomicLong counter = new AtomicLong();
    private long id;
    private String name;
    private String gdp;

    public GDP(String name, String gdp) {
        id = counter.incrementAndGet();

        this.name = name;
        this.gdp = gdp;
    }

    public GDP(GDP toClone)
    {
        id = toClone.getId();
        name = toClone.getName();
        gdp = toClone.getGDP();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGDP() {
        return gdp;
    }

    public void setGDP(String gdp) {
        this.gdp = gdp;
    }
}
