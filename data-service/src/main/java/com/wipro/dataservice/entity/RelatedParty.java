package com.wipro.dataservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @Author-Sobha
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedParty implements Serializable {
    @Id
    private String uuid;

    private String id;

    private String role;

    @Column(name = "mobile_activation_id")
    private String mobileActivationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mobile_activation_id", referencedColumnName = "uuid", insertable = false, updatable = false)
    private MobileActivation mobileActivation;

    @ManyToOne
    @JoinColumn(name="mobile_access_service_id", nullable=false)
    private MobileAccessService mobileAccessService;
}
