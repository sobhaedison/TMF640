package com.wipro.dataservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatedParty {
    private String uuid;
    private String id;
    private String role;
}
