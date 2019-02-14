/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author desire
 */
public class CustomerDetails {
    
    private int customerId;
    private String customerName;
    private String address;
    private String address2;
    private String phoneNumber;
    private String city;
    private String PostalCode;
    private int active;
    private LocalDate createDate;
    private LocalDate lastUpdate;

    public CustomerDetails(int customerId, String customerName, String address, 
            String address2,String city,String phoneNumber, String PostalCode, 
            int active, LocalDate createDate, LocalDate lastUpdate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.PostalCode = PostalCode;
        this.active = active;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    

    

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostaCode) {
        this.PostalCode = PostaCode;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
}
