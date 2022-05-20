package com.wipro.activationservice.component;

import com.google.gson.Gson;

import org.json.simple.JSONObject;

import com.wipro.activationservice.request.MobileAccessPostRequest;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
/**
 * @Author-Sobha
 */

@Component
public class TestComponent {
    public MobileAccessPostRequest getPostRequestContent() throws ParseException {
        String str = "{\n \"name\": \"mobileAccess\",\n \"description\": \"This is Mobile Access Service\",\n \"state\": \"ACTIVE\",\n\"serviceSpecification\": {\n\"href\": \"serviceCatalogManagement/v2/serviceSpecification/mobileAccess/ed7ce908-9e89-11e8-98d0-529269fb1459\"\n},\n\"relatedParty\":[\n{\n\"role\": \"InstanceConsumerGroup\",\n\"id\":\"B2CConsumerAndSmallBusiness\"\n},\n{\n\"role\": \"PairingId\",\n\"id\": \"B2CFORCE\"\n}\n],\n\"serviceCharacteristic\": [\n{\n\"name\": \"privateId\",\n\"value\": {\n\"imsi\": \"505013486018303\"\n }\n },\n{\n\"name\": \"publicId\",\n\"value\": {\n\"serviceNumber\": \"61417123456\"\n}\n},\n{\n\"name\": \"serviceProviderId\",\n\"value\": \"0002\"\n},\n{\n\"name\": \"accessType\",\n\"value\": \"standard\"\n},\n{\n\"name\": \"internationalRoaming\",\n\"value\": {\n\"enabled\": \"true\",\n\"accessRestriction\": \"realTimeOnly\"\n}\n}\n],\n \"supportingService\": [\n {\n\"name\": \"calling\",\n\"state\": \"ACTIVE\",\n\"serviceSpecification\": {\n\"href\": \"serviceCatalogManagement/v2/serviceSpecification/calling/29e86538-e7d5-44b2-b086-e53071f277e7\"\n},\n\"serviceCharacteristic\": [\n{\n\"name\": \"callingServiceProfile\",\n\"value\": \"standardMobile\"\n},\n{\n\"name\": \"ratingContext\",\n\"value\": \"realTime\"\n},\n{\n\"name\": \"callRestriction\",\n\"value\": [\n\"barIdd\",\n\"barPremium\"\n]\n},\n{\n\"name\": \"iddDiversionAllowed\",\n\"value\": \"false\"\n}\n]\n},\n{\n\"name\": \"voicemail\",\n\"state\": \"ACTIVE\",\n\"serviceSpecification\": {\n\"href\": \"serviceCatalogManagement/v2/serviceSpecification/voicemail/a485ffb5-a39d-4836-b079-373995324167\"\n},\n\"serviceCharacteristic\": [\n{\n\"name\": \"type\",\n\"value\": \"deviceDetected\"\n},\n{\n\"name\": \"serviceStateLocation\",\n\"value\": \"NSW\"\n}\n]\n},\n{\n\"name\": \"messaging\",\n\"state\": \"ACTIVE\",\n\"serviceSpecification\": {\n\"href\": \"serviceCatalogManagement/v2/serviceSpecification/messaging/1fc01607-edc0-4d20-a231-8548b94dce61\"\n},\n\"serviceCharacteristic\": [\n{\n\"name\": \"ratingContext\",\n\"value\": \"realTime\"\n},\n{\n\"name\": \"mmsEnabled\",\n\"value\": \"true\"\n},\n{\n\"name\": \"rcs\",\n\"value\": {\n\"enabled\": \"true\",\n\"rcsProfile\": \"default\"\n}\n}\n]\n},\n{\n\"name\": \"data\",\n\"state\": \"ACTIVE\",\n\"serviceSpecification\": {\n\"href\": \"serviceCatalogManagement/v2/serviceSpecification/data/ad184bbb-0a64-4b4a-b2a3-4d8bb986d7f3\"\n},\n\"serviceCharacteristic\": [\n{\n\"name\": \"ratingContext\",\n\"value\": \"realTime\"\n},\n{\n\"name\": \"dataName\",\n\"value\": \"publicInternet\"\n},\n{\n\"name\": \"gradeOfService\",\n\"value\": \"standard\"\n},\n{\n\"name\": \"dataBandwidth\",\n\"value\": {\n\"speedTier\": \"standard\"\n}\n}\n]\n}\n]\n}";
        JSONParser parser1 = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser1.parse(str);
        Gson gson = new Gson();
        MobileAccessPostRequest obj = gson.fromJson(jsonObject.toString(), MobileAccessPostRequest.class);
        return obj;
    }
}
