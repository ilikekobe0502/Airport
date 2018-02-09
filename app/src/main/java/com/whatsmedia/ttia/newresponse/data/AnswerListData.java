package com.whatsmedia.ttia.newresponse.data;


import com.google.gson.annotations.SerializedName;

public class AnswerListData {
    @SerializedName("id")
    private String id;
    @SerializedName("answer")
    private String answer;

    public AnswerListData(String id, String answer){
        this.setId(id);
        this.setAnswer(answer);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
