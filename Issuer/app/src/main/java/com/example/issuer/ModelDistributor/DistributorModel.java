package com.example.issuer.ModelDistributor;

public class DistributorModel {
    private String address, redeemed, acquired;

    public DistributorModel(String address, String redeemed, String acquired){
        this.acquired = acquired;
        this.address = address;
        this.redeemed = redeemed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(String redeemed) {
        this.redeemed = redeemed;
    }

    public String getAcquired() {
        return acquired;
    }

    public void setAcquired(String acquired) {
        this.acquired = acquired;
    }
}
