package com.wipro.activationservice.serviceImpl;

import com.wipro.activationservice.request.MobileAccessPostRequest;
import com.wipro.activationservice.service.TMF640Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author-Sobha
 */
@Service
@EnableAutoConfiguration
public class TMF640ServiceImpl implements TMF640Service {

    private static final String ACTIVATION_SERVICE = "activationService";
    private static final String GET_ACTIVATION_SERVICE = "getActivationService";
    private static final String UPDATE_ACTIVATION_SERVICE = "updateActivationService";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    public URI getURI(){
        List<ServiceInstance> instances = discoveryClient.getInstances("data-service");
        return instances.get(0).getUri();
    }

    @Override
    public ResponseEntity<MobileAccessPostRequest> activate(MobileAccessPostRequest mobileAccessPostRequest,
                                                            HttpHeaders headers) {
        String url = getURI().toString() + "/api/mobileAccess";
        return this.activateMobileServices(mobileAccessPostRequest,url,headers);
    }



    @Retry(name = ACTIVATION_SERVICE, fallbackMethod="activateMobileServicesFallback")
    public ResponseEntity<MobileAccessPostRequest> activateMobileServices(
            MobileAccessPostRequest mobileAccessPostRequest, String url,HttpHeaders reqHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        reqHeaders.forEach((key, value) -> {
            headers.add(key,value.get(0));
        });
        HttpEntity<MobileAccessPostRequest> entity = new HttpEntity<>(mobileAccessPostRequest, headers);
        ResponseEntity<MobileAccessPostRequest> requestResponseEntity = restTemplate.postForEntity(url, entity, MobileAccessPostRequest.class);
        return new ResponseEntity<>(requestResponseEntity.getBody(), requestResponseEntity.getStatusCode());
    }

    public ResponseEntity<MobileAccessPostRequest> activateMobileServicesFallback(
            MobileAccessPostRequest mobileAccessPostRequest, Throwable cause) {
        System.err.println("------Fallback method--------");
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
//    @Retry(name = GET_ACTIVATION_SERVICE, fallbackMethod="getActivateMobileServicesFallback")
    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String url = getURI().toString() + "/api/mobileAccess/"+id;
        ResponseEntity<MobileAccessPostRequest> requestResponseEntity = restTemplate.getForEntity(url, MobileAccessPostRequest.class);
        return new ResponseEntity<>(requestResponseEntity.getBody(), requestResponseEntity.getStatusCode());
    }

    public ResponseEntity<MobileAccessPostRequest> getActivateMobileServicesFallback(
            MobileAccessPostRequest mobileAccessPostRequest, Throwable cause) {
        System.err.println("------Fallback method--------");
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
//    @Retry(name = UPDATE_ACTIVATION_SERVICE, fallbackMethod="updateActivateMobileServicesFallback")
    public ResponseEntity<MobileAccessPostRequest> updateActivation(MobileAccessPostRequest mobileAccessPostRequest, HttpHeaders headers) {
        String url = getURI().toString() + "/api/mobileAccess";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.forEach((key, value) -> {
            requestHeaders.add(key,value.get(0));
        });
        Map<String, String> param = new HashMap<>();
        param.put("uuid",mobileAccessPostRequest.getUuid());
        HttpEntity<MobileAccessPostRequest> entity = new HttpEntity<>(mobileAccessPostRequest, headers);
        ResponseEntity<MobileAccessPostRequest> requestResponseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, entity, MobileAccessPostRequest.class, param);
        return new ResponseEntity<>(requestResponseEntity.getBody(), requestResponseEntity.getStatusCode());
    }

    public ResponseEntity<MobileAccessPostRequest> updateActivateMobileServicesFallback(
            MobileAccessPostRequest mobileAccessPostRequest, Throwable cause) {
        System.err.println("------Fallback method--------");
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
