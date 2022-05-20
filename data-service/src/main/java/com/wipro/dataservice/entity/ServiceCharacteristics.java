package com.wipro.dataservice.entity;


import com.wipro.dataservice.converter.HashMapConverter;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author-Sobha
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCharacteristics implements Serializable {
    @Id
    private String uuid;

    private String name;

    private int serviceType;

    private String value;

    @Column(name = "mobile_activation_id")
    private String mobileActivationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_activation_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private MobileActivation mobileActivation;

    private String mobileAccessServiceId;

    private String supportServiceId;
}
