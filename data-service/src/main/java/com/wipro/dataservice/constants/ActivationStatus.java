package com.wipro.dataservice.constants;
/**
 * @Author-Sobha
 */
public enum ActivationStatus {
    ACTIVE("active"), NOT_ACTIVE("not-active");

    private String value;

    private ActivationStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
