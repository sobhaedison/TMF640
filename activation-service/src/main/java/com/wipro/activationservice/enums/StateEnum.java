package com.wipro.activationservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StateEnum {
    INITIAL, ACTIVE, SUSPENDED;

//    private String value;
//
//    StateEnum() {
//
//    }
//
//    public String getValue() {
//        return value;
//    }
//    @Override
//    public String toString() {
//        return value;
//    }
//
//    @JsonCreator
//    public static StateEnum create (String value) {
//        if(value == null) {
//            throw new IllegalArgumentException();
//        }
//        for(StateEnum v : values()) {
//            if(value.equals(v.getValue())) {
//                return v;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
}
