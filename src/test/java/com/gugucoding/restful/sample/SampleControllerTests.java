package com.gugucoding.restful.sample;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class SampleControllerTests {

    @Autowired(required = false)
    private TestRestTemplate testRestTemplate;

    @Test
    public void hello테스트(){
        String[] result =  testRestTemplate.getForObject("http://localhost:8080/api/v1/sample/hello",String[].class);
        log.info(Arrays.toString(result));

    }
}
