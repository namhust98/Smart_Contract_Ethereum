package com.example.issuer.ModelCampaign;

import java.util.Calendar;

public class CampaignModel {
    String address, name, category, description, numCoupon, endTime;

    public CampaignModel(String address, String name, String category, String description, String numCoupon, String endTime) {
        this.address = address;
        this.name = name;
        this.category = category;
        this.description = description;
        this.numCoupon = numCoupon;
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumCoupon() {
        return numCoupon;
    }

    public void setNumCoupon(String numCoupon) {
        this.numCoupon = numCoupon;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
