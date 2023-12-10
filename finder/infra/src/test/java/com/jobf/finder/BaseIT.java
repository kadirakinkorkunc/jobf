package com.jobf.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

public class BaseIT
{

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected Integer port;

}
