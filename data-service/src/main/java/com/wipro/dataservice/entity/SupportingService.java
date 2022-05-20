package com.wipro.dataservice.entity;

import com.wipro.dataservice.enums.StateEnum;
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
public class SupportingService implements Serializable {
    @Id
    private String uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_master_id")
    private SupportingServiceMaster supportingServiceMaster;

    private StateEnum state;

    @Column(name = "service_specification_id")
    private String serviceSpecificationId;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "service_specification_id", referencedColumnName = "uuid",insertable = false,updatable = false)
    private ServiceSpecification serviceSpecification;

    @Column(name = "mobile_activation_id")
    private String mobileActivationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_activation_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private MobileActivation mobileActivation;

    @ManyToOne
    @JoinColumn(name="mobile_access_service_id", nullable=false)
    private MobileAccessService mobileAccessService;
}
