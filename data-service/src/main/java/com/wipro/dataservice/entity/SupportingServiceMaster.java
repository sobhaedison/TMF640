package com.wipro.dataservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * @Author-Sobha
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupportingServiceMaster implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
