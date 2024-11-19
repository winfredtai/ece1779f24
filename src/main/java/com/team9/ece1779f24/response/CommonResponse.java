package com.team9.ece1779f24.response;

import com.team9.ece1779f24.enums.Status;
import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private String message;
    private T data;

    public CommonResponse(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public CommonResponse(Status status, T data) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }
}

