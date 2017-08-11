package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class QuestionnaireData {
    @SerializedName("QID")
    private String questId;
    @SerializedName("QContent")
    private String questContent;

    private int score = 0;

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public String getQuestContent() {
        return questContent;
    }

    public void setQuestContent(String questContent) {
        this.questContent = questContent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
