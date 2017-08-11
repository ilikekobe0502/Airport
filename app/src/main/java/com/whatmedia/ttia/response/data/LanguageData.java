package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;


public class LanguageData {
    @SerializedName("TFtw")
    private String textTW;
    @SerializedName("TFcn")
    private String textCN;
    @SerializedName("TFjp")
    private String textJP;
    @SerializedName("TFen")
    private String textEN;


    public String getTextTW() {
        return textTW;
    }

    public void setTextTW(String textTW) {
        this.textTW = textTW;
    }

    public String getTextCN() {
        return textCN;
    }

    public void setTextCN(String textCN) {
        this.textCN = textCN;
    }

    public String getTextJP() {
        return textJP;
    }

    public void setTextJP(String textJP) {
        this.textJP = textJP;
    }

    public String getTextEN() {
        return textEN;
    }

    public void setTextEN(String textEN) {
        this.textEN = textEN;
    }
}
