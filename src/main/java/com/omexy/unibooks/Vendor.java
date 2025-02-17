package com.omexy.unibooks;

public class Vendor {
    private String vendorId;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;

    public Vendor(String vendorId, String name, String contactPerson, String email, String phone) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Vendor{" +
               "vendorId='" + vendorId + '\'' +
               ", name='" + name + '\'' +
               ", contactPerson='" + contactPerson + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               '}';
    }
} 