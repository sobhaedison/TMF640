package com.wipro.dataservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceSpecification {
    private String uuid;
    private String href;
}
