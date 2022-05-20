package com.wipro.dataservice.request;

import com.wipro.dataservice.dto.*;
import com.wipro.dataservice.enums.StateEnum;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author-Sobha
 */
@Data
@Builder
public class MobileAccessPostRequest {
    private String uuid;

    private String name;

    private String description;

    private StateEnum state;

    private ServiceSpecification serviceSpecification;

    private List<ServiceCharacteristic> serviceCharacteristic = new ArrayList<ServiceCharacteristic>();

    private List<RelatedParty> relatedParty = new ArrayList<RelatedParty>();

    private List<SupportingServiceCreatePostRequest> supportingService = null;
}
