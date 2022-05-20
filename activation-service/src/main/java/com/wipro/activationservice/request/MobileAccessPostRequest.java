package com.wipro.activationservice.request;

import com.wipro.activationservice.dto.*;
import com.wipro.activationservice.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
