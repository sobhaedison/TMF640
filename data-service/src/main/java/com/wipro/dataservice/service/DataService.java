package com.wipro.dataservice.service;

import com.wipro.dataservice.request.MobileAccessPostRequest;
import org.springframework.http.ResponseEntity;

/**
 * @Author-Sobha
 */
public interface DataService {

    /**
     * Activate Mobile Service
     *
     * @param mobileAccessPostRequest
     * @return
     */
    public MobileAccessPostRequest activateMobileService(MobileAccessPostRequest mobileAccessPostRequest);

    /**
     * Get Service
     *
     * @param id
     * @return
     */

    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(String id);
}
