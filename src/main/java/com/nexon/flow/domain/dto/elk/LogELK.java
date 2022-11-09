package com.nexon.flow.domain.dto.elk;

import lombok.Data;

@Data
public class LogELK {

    private String timestamp;
    private String hostname;
    private String hostIp;
    private String clientIp;
    private String clientUrl;
    private String method;
    private String userName;
    private String callFunction;
    private String type;
    private String parameter;

}