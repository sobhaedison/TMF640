package com.wipro.activationservice.service;

import com.wipro.activationservice.request.MobileAccessPostRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
/**
 * @Author-Sobha
 */
public interface TMF640Service {
    /**
     * Activate Mobile Service
     *
     * @param mobileAccessPostRequest
     * @param headers
     * @return
     */
    public ResponseEntity<MobileAccessPostRequest> activate(MobileAccessPostRequest mobileAccessPostRequest,
                                                            HttpHeaders headers);

    /**
     * Get Mobile Service
     * @param id
     * @return
     */
    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(String id);

    /**
     * Update Mobile Service
     * @param mobileAccessPostRequest
     * @param headers
     * @return
     */
    public ResponseEntity<MobileAccessPostRequest> updateActivation(MobileAccessPostRequest mobileAccessPostRequest,
                                                            HttpHeaders headers);
}
