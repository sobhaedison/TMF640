package com.wipro.dataservice.dto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ServiceCharacteristic {
    private String uuid;
    private String name;
    private Object value;

}
