package com.wipro.activationservice.dto;

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
public class SupportingServiceCreatePostRequest {
    private String uuid;

    private String name;

    private StateEnum state;

    private ServiceSpecification serviceSpecification;

    private List<ServiceCharacteristic> serviceCharacteristic = new ArrayList<>();
}
