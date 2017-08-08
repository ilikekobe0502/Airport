package com.whatmedia.ttia.sql;

public class VocabularyData {
    private String id;
    private String vocabulary;
    private String translation;
    private String example;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVocabulary() {
        return vocabulary != null ? vocabulary : "";
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getTranslation() {
        return translation != null ? translation : "";
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExample() {
        return example != null ? example : "";
    }

    public void setExample(String example) {
        this.example = example;
    }
}
