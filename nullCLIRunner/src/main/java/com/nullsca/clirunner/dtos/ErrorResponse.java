package com.nullsca.clirunner.dtos;

public class ErrorResponse {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                '}';
    }
}
