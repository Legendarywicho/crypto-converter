package com.luis_santiago.cryptoconverter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/14/18.
 */

public class Response {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("payload")
    @Expose
    private Payload payload;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
