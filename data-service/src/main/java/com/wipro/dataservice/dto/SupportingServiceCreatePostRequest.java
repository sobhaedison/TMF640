package com.wipro.dataservice.dto;

import com.wipro.dataservice.enums.StateEnum;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SupportingServiceCreatePostRequest {
    private String uuid;

    private String name;

    private StateEnum state;

    private ServiceSpecification serviceSpecification;

    private List<ServiceCharacteristic> serviceCharacteristic = new ArrayList<>();
}
