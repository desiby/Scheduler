/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author desib
 */
public class Appointment {
     
    private int appointmentId;
    private String type;
    private String startTime;
    private String endTime;
    private LocalDate createDate;
    private LocalDate lastUpdate;

    public Appointment(int appointmentId, String type, String startTime, String endTime, LocalDate createDate, LocalDate lastUpdate) {
        this.appointmentId = appointmentId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    
}
