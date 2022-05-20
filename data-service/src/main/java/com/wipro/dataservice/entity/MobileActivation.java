package com.wipro.dataservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * @Author-Sobha
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileActivation implements Serializable {
    @Id
    private String uuid;

    private String imsi;
}
