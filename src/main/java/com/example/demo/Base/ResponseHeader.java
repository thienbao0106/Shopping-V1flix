package com.example.demo.Base;

import com.example.demo.Enum.ResponseType;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final LocalDateTime timestamp;
    private final String responseCode;
    private final String message;
    @Setter
    private Object responseData;
    private final String responseType;

    public ResponseHeader(LocalDateTime timestamp, String responseCode, String message, Object responseData, String responseType) {
        this.timestamp = timestamp;
        this.responseCode = responseCode;
        this.message = message;
        this.responseData = responseData;
        this.responseType = responseType;
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("responseType", this.responseType);
        responseMap.put("responseCode", this.responseCode);
        responseMap.put("message", this.message);
        responseMap.put("timestamp", this.timestamp);
        if (this.responseData != null && this.responseType.equals(ResponseType.SUCCESS.toString())) responseMap.put("data", this.responseData);
        if (this.responseData != null && this.responseType.equals(ResponseType.ERROR.toString())) responseMap.put("errors", this.responseData);
        return responseMap;
    }


}
