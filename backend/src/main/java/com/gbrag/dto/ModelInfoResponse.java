package com.gbrag.dto;

public class ModelInfoResponse {

    private String model;
    private String baseUrl;
    private String maskedKey;

    public ModelInfoResponse() {
    }

    public ModelInfoResponse(String model, String baseUrl, String maskedKey) {
        this.model = model;
        this.baseUrl = baseUrl;
        this.maskedKey = maskedKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMaskedKey() {
        return maskedKey;
    }

    public void setMaskedKey(String maskedKey) {
        this.maskedKey = maskedKey;
    }
}
