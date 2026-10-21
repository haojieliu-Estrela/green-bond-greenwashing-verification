package com.gbrag.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyRequest {

    @NotBlank(message = "待核验内容不能为空")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
