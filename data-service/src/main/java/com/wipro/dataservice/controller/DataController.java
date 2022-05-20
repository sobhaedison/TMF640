package com.wipro.dataservice.controller;

import com.wipro.dataservice.request.MobileAccessPostRequest;
import com.wipro.dataservice.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author-Sobha
 */
@RestController
public class DataController {
    @Autowired
    DataService dataService;

    /**
     * Activate Mobile Service
     *
     * @param mobileAccessPostRequest
     * @param headers
     * @return
     */
    @PostMapping("/mobileAccess")
    public ResponseEntity<MobileAccessPostRequest> activate(@RequestBody MobileAccessPostRequest mobileAccessPostRequest,
                                                            @RequestHeader HttpHeaders headers) {
        mobileAccessPostRequest = dataService.activateMobileService(mobileAccessPostRequest);
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.OK);
    }

    /**
     * Get Mobile Service
     *
     * @param id
     * @return
     */
    @GetMapping("/mobileAccess/{id}")
    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(@PathVariable String id) {
        return dataService.getServiceDetails(id);
    }

    /**
     * Update Mobile Service
     *
     * @param mobileAccessPostRequest
     * @param headers
     * @return
     */
    @PutMapping("/mobileAccess")
    public ResponseEntity<MobileAccessPostRequest> updateActivation(@RequestBody MobileAccessPostRequest mobileAccessPostRequest,
                                                                    @RequestHeader HttpHeaders headers) {
        mobileAccessPostRequest = dataService.activateMobileService(mobileAccessPostRequest);
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.OK);
    }
}
