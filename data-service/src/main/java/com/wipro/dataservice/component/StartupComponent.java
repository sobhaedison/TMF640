package com.wipro.dataservice.component;

import com.wipro.dataservice.entity.SupportingServiceMaster;
import com.wipro.dataservice.repository.SupportingServiceMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author-Sobha
 */

@Component
public class StartupComponent {
    @Autowired
    SupportingServiceMasterRepository supportingServiceMasterRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void onStartUp(){
        if(supportingServiceMasterRepository.findAll().isEmpty()){
            List<SupportingServiceMaster> supportingServiceMasterList = new ArrayList<>();
            supportingServiceMasterList.add(
                    new SupportingServiceMaster(1L,"calling"));
            supportingServiceMasterList.add(
                    new SupportingServiceMaster(2L,"voicemail"));
            supportingServiceMasterList.add(
                    new SupportingServiceMaster(3L,"messaging"));
            supportingServiceMasterList.add(
                    new SupportingServiceMaster(4L,"data"));
            supportingServiceMasterRepository.saveAll(supportingServiceMasterList);

        }

    }
}
