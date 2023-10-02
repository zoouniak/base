package com.zoouniak.yoursell.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String zipCode;
    private String addr;
    private String extraAddr;
}
