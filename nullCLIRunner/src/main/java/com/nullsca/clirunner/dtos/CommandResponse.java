package com.nullsca.clirunner.dtos;

public class CommandResponse {
    private String output;
    private int rc;

    public CommandResponse(String output, int rc) {
        this.output = output;
        this.rc = rc;
    }

    public CommandResponse() {

    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}



