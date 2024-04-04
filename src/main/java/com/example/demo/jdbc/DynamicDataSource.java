package com.example.demo.jdbc;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DynamicDataSource {

    DriverManagerDataSource ds = new DriverManagerDataSource();

    public DynamicDataSource(String in_url, String in_user, String in_pwd) {
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl(in_url);
        ds.setUsername(in_user);
        ds.setPassword(in_pwd);
    }

    public DriverManagerDataSource getDs() {
        return ds;
    }

}