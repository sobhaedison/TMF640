package com.wipro.dataservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;
@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Object> value) {

        String valueJson = null;
//        try {
//            valueJson = objectMapper.writeValueAsString(value);
//        } catch (final JsonProcessingException e) {
//            log.error("JSON writing error", e);
//        }

        return valueJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String valueJson) {

        Map<String, Object> value = null;
//        try {
//            value = objectMapper.readValue(valueJson, Map.class);
//        } catch (final IOException e) {
//            log.error("JSON reading error", e);
//        }

        return value;
    }

}
