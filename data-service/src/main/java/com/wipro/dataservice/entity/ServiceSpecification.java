package com.wipro.dataservice.entity;

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
public class ServiceSpecification implements Serializable {
    @Id
    private String uuid;

    private String href;

    private int serviceType;

    @Column(name = "mobile_activation_id")
    private String mobileActivationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_activation_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private MobileActivation mobileActivation;

}
