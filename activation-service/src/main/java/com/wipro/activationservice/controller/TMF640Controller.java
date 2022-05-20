package com.wipro.activationservice.controller;


import com.wipro.activationservice.request.MobileAccessPostRequest;
import com.wipro.activationservice.service.TMF640Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author-Sobha
 */

@RestController
@RequestMapping("/TMF640")
public class TMF640Controller {
    @Autowired
    TMF640Service tmf640Service;

    /**
     * Activate mobile service
     * @param headers
     * @param mobileAccessPostRequest
     * @return
     */
    @PostMapping("/mobileAccess")
    public ResponseEntity<MobileAccessPostRequest> activate(@RequestHeader HttpHeaders headers, @RequestBody MobileAccessPostRequest mobileAccessPostRequest) {
        return tmf640Service.activate(mobileAccessPostRequest,headers);
    }

    /**
     * Get mobile service
     * @param id
     * @return
     */
    @GetMapping("/mobileAccess/{id}")
    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(@PathVariable String id){
        return tmf640Service.getServiceDetails(id);
    }

    /**
     * Update mobile service
     * @param headers
     * @param mobileAccessPostRequest
     * @return
     */
    @PutMapping("/mobileAccess")
    public ResponseEntity<MobileAccessPostRequest> updateActivation(@RequestHeader HttpHeaders headers, @RequestBody MobileAccessPostRequest mobileAccessPostRequest) {
        return tmf640Service.updateActivation(mobileAccessPostRequest,headers);
    }
}
