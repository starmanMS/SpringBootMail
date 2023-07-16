package com.mirrorseek.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetBean {
    private int code;
    private String reason;
}
