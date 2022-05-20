package com.wipro.activationservice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.activationservice.component.TestComponent;
import com.wipro.activationservice.request.MobileAccessPostRequest;
import com.wipro.activationservice.service.TMF640Service;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActivationServiceApplicationTests {

    @Autowired
    TestComponent testComponent;

    @Autowired
    TMF640Service service;

    private String uuid;


    @Test
    @Order(1)
    public void activateService() throws Exception {
        MobileAccessPostRequest mobileAccessPostRequest = testComponent.getPostRequestContent();
        System.err.println(mobileAccessPostRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "d1b1fc53-6d49-411b-afd7-be9b18dd5cab");
        headers.add("X-Correlation-ID", "d7e1fb39-344f-4076-9957-94f7521b929b");
        ResponseEntity<MobileAccessPostRequest> responseEntity =
                service.activate(mobileAccessPostRequest, headers);
        System.err.println("response:"+responseEntity.getBody());
        uuid = responseEntity.getBody().getUuid();
        HttpStatus status = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    @Order(2)
    public void getService() throws Exception {
        ResponseEntity<MobileAccessPostRequest> responseEntity =
                service.getServiceDetails(uuid);
        HttpStatus status = responseEntity.getStatusCode();
        assertEquals(HttpStatus.ACCEPTED, status);
    }

}
