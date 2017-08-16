package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class AchievementsData {
    @SerializedName("Achievement_Title")
    private String title;
    @SerializedName("Achievement_Content")
    private String content;
    @SerializedName("Achievement_Date")
    private String date;
    @SerializedName("Achievement_img")
    private String imgPath;
    @SerializedName("Result")
    private String result;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
