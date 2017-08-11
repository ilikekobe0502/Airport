package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/11.
 */

public class ExchangeRateData {
    @SerializedName("success")
    private String success;
    @SerializedName("source")
    private String source;
    @SerializedName("target")
    private String target;
    @SerializedName("rate")
    private String rate;
    @SerializedName("amount")
    private String amount;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
