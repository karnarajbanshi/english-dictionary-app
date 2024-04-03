package com.example.dictionary.dto;

public class DictionaryData {
    private String word;
    private String partOfSpeech;
    private String defination;
    private String synonyms;
    private String antonyms;
    private String pronunceaudio;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefination() {
        return defination;
    }

    public void setDefination(String defination) {
        this.defination = defination;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }

    public String getPronunceaudio() {
        return pronunceaudio;
    }

    public void setPronunceaudio(String pronunceaudio) {
        this.pronunceaudio = pronunceaudio;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
