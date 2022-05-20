package com.wipro.dataservice.repository;

import com.wipro.dataservice.entity.SupportingServiceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author-Sobha
 */
public interface SupportingServiceMasterRepository  extends JpaRepository<SupportingServiceMaster,String> {
    public SupportingServiceMaster findByName(String name);
}
