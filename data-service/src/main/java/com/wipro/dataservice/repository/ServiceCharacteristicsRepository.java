package com.wipro.dataservice.repository;

import com.wipro.dataservice.entity.ServiceCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @Author-Sobha
 */
public interface ServiceCharacteristicsRepository extends JpaRepository<ServiceCharacteristics,String> {

    List<ServiceCharacteristics> findByMobileAccessServiceId(String id);

    List<ServiceCharacteristics> findBySupportServiceId(String id);
}
