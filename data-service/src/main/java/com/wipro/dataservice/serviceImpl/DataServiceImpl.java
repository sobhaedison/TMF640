package com.wipro.dataservice.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipro.dataservice.constants.ServiceConstant;
import com.wipro.dataservice.dto.RelatedParty;
import com.wipro.dataservice.dto.ServiceCharacteristic;
import com.wipro.dataservice.dto.SupportingServiceCreatePostRequest;
import com.wipro.dataservice.entity.*;

import com.wipro.dataservice.exclusion.SupportingServiceExclusionStrategy;
import com.wipro.dataservice.repository.*;
import com.wipro.dataservice.request.MobileAccessPostRequest;
import com.wipro.dataservice.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @Author-Sobha
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {
    @Autowired
    MobileActivationRepository mobileActivationRepository;

    @Autowired
    ServiceSpecificationRepository serviceSpecificationRepository;

    @Autowired
    ServiceCharacteristicsRepository serviceCharacteristicsRepository;

    @Autowired
    MobileAccessServiceRepository mobileAccessServiceRepository;

    @Autowired
    SupportingServiceMasterRepository supportingServiceMasterRepository;

    @Autowired
    SupportingServiceRepository supportingServiceRepository;

    @Autowired
    RelatedPartyRepository relatedPartyRepository;

    @Override
    public MobileAccessPostRequest activateMobileService(MobileAccessPostRequest mobileAccessPostRequest) {
        try {
            MobileAccessService mobileAccessService;
            mobileAccessPostRequest = createUniqueId(mobileAccessPostRequest);
            MobileActivation mobileActivation = saveMobileActivation(mobileAccessPostRequest
                    .getServiceCharacteristic().get(0));

            if (mobileActivation != null) {
                ServiceSpecification serviceSpecification = saveServiceSpecification(
                        mobileAccessPostRequest.getServiceSpecification(), ServiceConstant.ACTIVATION_SERVICE.getValue(),
                        mobileActivation.getUuid());
                if (serviceSpecification != null) {
                    Object value = mobileAccessPostRequest.getServiceCharacteristic().get(0).getValue();
                    JSONObject jsonObject = (JSONObject) JSONValue.parse(new ObjectMapper().writeValueAsString(value));
                    String imsi = jsonObject.get("imsi").toString();
                    mobileAccessService = MobileAccessService.builder()
                            .uuid(mobileAccessPostRequest.getUuid())
                            .imsi(imsi)
                            .mobileActivationId(mobileActivation.getUuid())
                            .name(mobileAccessPostRequest.getName())
                            .description(mobileAccessPostRequest.getDescription())
                            .state(mobileAccessPostRequest.getState())
                            .serviceSpecificationId(serviceSpecification.getUuid())
                            .build();
                    mobileAccessService = mobileAccessServiceRepository.save(mobileAccessService);
                    if (mobileAccessService != null) {
                        List<com.wipro.dataservice.entity.RelatedParty> relatedPartyList =
                                saveRelatedParty(mobileAccessPostRequest.getRelatedParty(),
                                        mobileActivation.getUuid(), mobileAccessService.getUuid());
                        mobileAccessService.setRelatedParty(relatedPartyList);

                        List<ServiceCharacteristics> serviceCharacteristicsList =
                                saveServiceCharacteristics(mobileAccessPostRequest.getServiceCharacteristic(),
                                        ServiceConstant.ACTIVATION_SERVICE.getValue(), mobileActivation.getUuid(),
                                        mobileAccessService.getUuid(), null);
                        if (!serviceCharacteristicsList.isEmpty())
                            serviceCharacteristicsRepository.saveAll(serviceCharacteristicsList);

                        List<SupportingService> supportingServiceList = saveSupportingService(
                                mobileAccessPostRequest.getSupportingService(), mobileActivation.getUuid(),
                                mobileAccessService.getUuid());
                        if (!supportingServiceList.isEmpty()) {
                            supportingServiceRepository.saveAll(supportingServiceList);
                            mobileAccessService.setSupportingService(supportingServiceList);
                        }
                        Gson gson = new GsonBuilder()
                                .setExclusionStrategies(new SupportingServiceExclusionStrategy())
                                //.serializeNulls() <-- uncomment to serialize NULL fields as well
                                .create();
                        mobileAccessService = mobileAccessServiceRepository.save(mobileAccessService);
                        String json = gson.toJson(mobileAccessService);
                        if (json != null) {
                            return mobileAccessPostRequest;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return null;
    }


    public MobileAccessPostRequest createUniqueId(MobileAccessPostRequest mobileAccessPostRequest) {
        try {
            if (mobileAccessPostRequest.getUuid() != null || mobileAccessPostRequest.getUuid() != "")
                mobileAccessPostRequest.setUuid(UUID.randomUUID().toString());
            else
                mobileAccessPostRequest.setUuid(mobileAccessPostRequest.getUuid());
            com.wipro.dataservice.dto.ServiceSpecification serviceSpecification = createServiceSpecificationId(
                    mobileAccessPostRequest.getServiceSpecification());
            mobileAccessPostRequest.setServiceSpecification(serviceSpecification);

            List<ServiceCharacteristic> serviceCharacteristic = createServiceCharacteristicsId(
                    mobileAccessPostRequest.getServiceCharacteristic());
            mobileAccessPostRequest.setServiceCharacteristic(serviceCharacteristic);

            List<RelatedParty> relatedPartyList = createRelatedPartyId(
                    mobileAccessPostRequest.getRelatedParty());
            mobileAccessPostRequest.setRelatedParty(relatedPartyList);

            List<SupportingServiceCreatePostRequest> supportingServiceCreatePostRequestList = createSupportingServicesId(
                    mobileAccessPostRequest.getSupportingService());
            mobileAccessPostRequest.setSupportingService(supportingServiceCreatePostRequestList);

            return mobileAccessPostRequest;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public List<RelatedParty> createRelatedPartyId(List<RelatedParty> relatedPartyList) {
        for (RelatedParty relatedParty : relatedPartyList) {
            relatedParty.setUuid(relatedParty.getUuid() == null ? UUID.randomUUID().toString() : relatedParty.getUuid() == "" ?
                    UUID.randomUUID().toString() : relatedParty.getUuid());
        }
        return relatedPartyList;
    }

    public List<SupportingServiceCreatePostRequest> createSupportingServicesId(
            List<SupportingServiceCreatePostRequest> supportingServiceCreatePostRequestList) {
        for (SupportingServiceCreatePostRequest serviceCreatePostRequest : supportingServiceCreatePostRequestList) {
            serviceCreatePostRequest.setUuid(serviceCreatePostRequest.getUuid() == null ?
                    UUID.randomUUID().toString() : serviceCreatePostRequest.getUuid() == "" ?
                    UUID.randomUUID().toString() : serviceCreatePostRequest.getUuid());
            com.wipro.dataservice.dto.ServiceSpecification serviceSpecification = createServiceSpecificationId(
                    serviceCreatePostRequest.getServiceSpecification());
            serviceCreatePostRequest.setServiceSpecification(serviceSpecification);

            List<ServiceCharacteristic> serviceCharacteristic = createServiceCharacteristicsId(
                    serviceCreatePostRequest.getServiceCharacteristic());
            serviceCreatePostRequest.setServiceCharacteristic(serviceCharacteristic);
        }
        return supportingServiceCreatePostRequestList;
    }

    public List<ServiceCharacteristic> createServiceCharacteristicsId(
            List<ServiceCharacteristic> serviceCharacteristicList) {
        for (ServiceCharacteristic serviceCharacteristic : serviceCharacteristicList) {
            serviceCharacteristic.setUuid(serviceCharacteristic.getUuid() == null ?
                    UUID.randomUUID().toString() : serviceCharacteristic.getUuid() == "" ?
                    UUID.randomUUID().toString() : serviceCharacteristic.getUuid());
        }
        return serviceCharacteristicList;
    }

    public com.wipro.dataservice.dto.ServiceSpecification createServiceSpecificationId(
            com.wipro.dataservice.dto.ServiceSpecification serviceSpecification) {
        serviceSpecification.setUuid(serviceSpecification.getUuid() == null ?
                UUID.randomUUID().toString() : serviceSpecification.getUuid() == "" ?
                UUID.randomUUID().toString() : serviceSpecification.getUuid());
        return serviceSpecification;
    }

    public MobileActivation saveMobileActivation(ServiceCharacteristic serviceCharacteristic) {
        try {
            Object value = serviceCharacteristic.getValue();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(new ObjectMapper().writeValueAsString(value));
            String imsi = jsonObject.get("imsi").toString();
            MobileActivation mobileActivation = MobileActivation.builder()
                    .uuid(UUID.randomUUID().toString())
                    .imsi(imsi).build();
            return mobileActivationRepository.save(mobileActivation);
        } catch (Exception ex) {
            log.error(ex.getMessage());

        }
        return null;
    }

    public ServiceSpecification saveServiceSpecification(com.wipro.dataservice.dto.ServiceSpecification serviceSpecification,
                                                         int serviceType, String mobileActivationId) {
        try {
            ServiceSpecification serviceSpecification1 = ServiceSpecification.builder()
                    .uuid(serviceSpecification.getUuid())
                    .serviceType(serviceType)
                    .href(serviceSpecification.getHref())
                    .mobileActivationId(mobileActivationId)
                    .build();
            return serviceSpecificationRepository.save(serviceSpecification1);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public List<ServiceCharacteristics> saveServiceCharacteristics(
            List<ServiceCharacteristic> serviceCharacteristicList, int serviceType,
            String mobileActivationId, String mobileAccessServiceId, String supportingServiceId) {
        try {
            List<ServiceCharacteristics> serviceCharacteristicsList = new ArrayList<>();
            for (ServiceCharacteristic service : serviceCharacteristicList) {
                ServiceCharacteristics serviceCharacteristics = ServiceCharacteristics.builder()
                        .uuid(service.getUuid())
                        .serviceType(serviceType)
                        .name(service.getName())
                        .value(service.getValue().toString())
                        .mobileActivationId(mobileActivationId)
                        .supportServiceId(supportingServiceId)
                        .mobileAccessServiceId(mobileAccessServiceId)
                        .build();
                serviceCharacteristicsList.add(serviceCharacteristics);
            }
            return serviceCharacteristicsRepository.saveAll(serviceCharacteristicsList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public List<SupportingService> saveSupportingService(
            List<SupportingServiceCreatePostRequest> supportingServiceCreatePostRequests,
            String mobileActivationId, String mobileAccessServiceId) {
        try {
            List<SupportingService> supportingServiceList = new ArrayList<>();
            for (SupportingServiceCreatePostRequest support : supportingServiceCreatePostRequests) {
                ServiceSpecification serviceSpecification = saveServiceSpecification(
                        support.getServiceSpecification(), ServiceConstant.SUPPORTING_SERVICE.getValue(),
                        mobileActivationId);
                if (serviceSpecification != null) {
                    SupportingService service = SupportingService.builder()
                            .uuid(support.getUuid())
                            .mobileAccessService(mobileAccessServiceRepository.findById(mobileAccessServiceId).get())
                            .supportingServiceMaster(supportingServiceMasterRepository.findByName(support.getName()))
                            .state(support.getState())
                            .serviceSpecificationId(serviceSpecification.getUuid())
                            .mobileActivationId(mobileActivationId)
                            .build();
                    supportingServiceList.add(service);
                }
            }
            if (!supportingServiceRepository.saveAll(supportingServiceList).isEmpty()) {
                List<ServiceCharacteristics> serviceCharacteristicsList = new ArrayList<>();
                for (SupportingServiceCreatePostRequest supportingService : supportingServiceCreatePostRequests) {
                    for (ServiceCharacteristic serviceCharacteristic : supportingService.getServiceCharacteristic()) {
                        ServiceCharacteristics characteristics = ServiceCharacteristics.builder()
                                .uuid(serviceCharacteristic.getUuid())
                                .serviceType(ServiceConstant.SUPPORTING_SERVICE.getValue())
                                .name(serviceCharacteristic.getName())
                                .value(serviceCharacteristic.getValue().toString())
                                .supportServiceId(supportingService.getUuid())
                                .build();
                        serviceCharacteristicsList.add(characteristics);
                    }
                }
                serviceCharacteristicsRepository.saveAll(serviceCharacteristicsList);
            }
            return supportingServiceList;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public List<com.wipro.dataservice.entity.RelatedParty> saveRelatedParty(List<RelatedParty> relatedPartyList,
                                                                            String mobileActivationId,
                                                                            String mobileAccessServiceId) {
        try {
            List<com.wipro.dataservice.entity.RelatedParty> relatedParties = new ArrayList<>();
            for (RelatedParty relatedParty : relatedPartyList) {
                com.wipro.dataservice.entity.RelatedParty party = com.wipro.dataservice.entity.RelatedParty.builder()
                        .uuid(relatedParty.getUuid())
                        .id(relatedParty.getId())
                        .role(relatedParty.getRole())
                        .mobileAccessService(mobileAccessServiceRepository.findById(mobileAccessServiceId).get())
                        .mobileActivationId(mobileActivationId)
                        .build();
                relatedParties.add(party);
            }
            return relatedPartyRepository.saveAll(relatedParties);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<MobileAccessPostRequest> getServiceDetails(String id) {
        MobileAccessService mobileAccessService = mobileAccessServiceRepository.findById(id).get();
        MobileAccessPostRequest mobileAccessPostRequest = MobileAccessPostRequest.builder()
                .uuid(mobileAccessService.getUuid())
                .name(mobileAccessService.getName())
                .state(mobileAccessService.getState())
                .description(mobileAccessService.getDescription())
                .build();
        mobileAccessPostRequest.setServiceSpecification(getMobileServiceSpecifications(mobileAccessService));
        mobileAccessPostRequest.setServiceCharacteristic(getServiceCharacteristics(mobileAccessService.getUuid(), null));
        mobileAccessPostRequest.setRelatedParty(getRelatedParty(mobileAccessService));
        mobileAccessPostRequest.setSupportingService(getSupportingServices(mobileAccessService));
        return new ResponseEntity<>(mobileAccessPostRequest, HttpStatus.ACCEPTED);
    }

    public com.wipro.dataservice.dto.ServiceSpecification getMobileServiceSpecifications(MobileAccessService mobileAccessService) {
        ServiceSpecification serviceSpecification = mobileAccessService.getServiceSpecification();
        com.wipro.dataservice.dto.ServiceSpecification serviceSpecification1 =
                com.wipro.dataservice.dto.ServiceSpecification.builder()
                        .uuid(serviceSpecification.getUuid())
                        .href(serviceSpecification.getHref())
                        .build();
        return serviceSpecification1;
    }

    public List<ServiceCharacteristic> getServiceCharacteristics(String mobileAccessServiceId,
                                                                 String supportingServiceId) {
        List<ServiceCharacteristics> serviceCharacteristicsList = new ArrayList<>();
        if (mobileAccessServiceId != null) {
            serviceCharacteristicsList = serviceCharacteristicsRepository
                    .findByMobileAccessServiceId(mobileAccessServiceId);
        } else if (supportingServiceId != null) {
            serviceCharacteristicsList = serviceCharacteristicsRepository
                    .findBySupportServiceId(supportingServiceId);
        }
        List<ServiceCharacteristic> serviceCharacteristics = new ArrayList<>();
        for (ServiceCharacteristics serviceCharacteristic : serviceCharacteristicsList) {
            ServiceCharacteristic characteristic = ServiceCharacteristic.builder()
                    .uuid(serviceCharacteristic.getUuid())
                    .name(serviceCharacteristic.getName())
                    .value(serviceCharacteristic.getValue())
                    .build();
            serviceCharacteristics.add(characteristic);
        }
        return serviceCharacteristics;
    }

    public List<RelatedParty> getRelatedParty(MobileAccessService mobileAccessService) {
        List<RelatedParty> relatedParties = new ArrayList<>();
        for (com.wipro.dataservice.entity.RelatedParty party : mobileAccessService.getRelatedParty()) {
            RelatedParty relatedParty = RelatedParty.builder()
                    .uuid(party.getUuid())
                    .role(party.getRole())
                    .id(party.getId())
                    .build();
            relatedParties.add(relatedParty);
        }
        return relatedParties;
    }

    public List<SupportingServiceCreatePostRequest> getSupportingServices(MobileAccessService mobileAccessService) {
        List<SupportingServiceCreatePostRequest> supportingServiceCreatePostRequestList =
                new ArrayList<>();
        for (SupportingService supportingService : mobileAccessService.getSupportingService()) {
            SupportingServiceCreatePostRequest request = SupportingServiceCreatePostRequest.builder()
                    .uuid(supportingService.getUuid())
                    .name(supportingService.getSupportingServiceMaster().getName())
                    .state(supportingService.getState())
                    .serviceCharacteristic(getServiceCharacteristics(null, supportingService.getUuid()))
                    .build();
            com.wipro.dataservice.dto.ServiceSpecification serviceSpecification =
                    com.wipro.dataservice.dto.ServiceSpecification.builder()
                            .uuid(mobileAccessService.getServiceSpecification().getUuid())
                            .href(mobileAccessService.getServiceSpecification().getHref())
                            .build();
            request.setServiceSpecification(serviceSpecification);
            supportingServiceCreatePostRequestList.add(request);
        }
        return supportingServiceCreatePostRequestList;
    }
}
