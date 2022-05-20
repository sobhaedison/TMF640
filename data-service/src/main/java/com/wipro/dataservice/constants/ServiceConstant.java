package com.wipro.dataservice.constants;
/**
 * @Author-Sobha
 */
public enum ServiceConstant {

    ACTIVATION_SERVICE(1),SUPPORTING_SERVICE(2);

    private int value;

    private ServiceConstant(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
