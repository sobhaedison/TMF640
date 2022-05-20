package com.wipro.dataservice.entity;

import com.wipro.dataservice.enums.StateEnum;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
/**
 * @Author-Sobha
 */
@Entity
@Getter
@Setter
@Table(name = "mobile_access_service")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileAccessService implements Serializable {
    @Id
    private String uuid;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "mobile_activation_id")
    private String mobileActivationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_activation_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private MobileActivation mobileActivation;

    private String name;

    private String description;

    private StateEnum state;

    @Column(name = "service_specification_id")
    private String serviceSpecificationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_specification_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private ServiceSpecification serviceSpecification;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mobileAccessService")
    private List<RelatedParty> relatedParty;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mobileAccessService")
    private List<SupportingService> supportingService;
}
