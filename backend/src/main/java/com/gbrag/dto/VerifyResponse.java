package com.gbrag.dto;

public class VerifyResponse {

    private String conclusion;
    private String evidence;

    public VerifyResponse() {
    }

    public VerifyResponse(String conclusion, String evidence) {
        this.conclusion = conclusion;
        this.evidence = evidence;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
