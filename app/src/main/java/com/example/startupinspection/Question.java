package com.example.startupinspection;

public class Question {
    private String question;
    private String option1;
    private String option2;
    private int ansNum;
    //private String comment;

    public Question() {}

    public Question(String question, String option1, String option2, int ansNum) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.ansNum = ansNum;
        //this.comment = comment;
    }

    /*public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }*/

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getAnsNum() {
        return ansNum;
    }

    public void setAnsNum(int ansNum) {
        this.ansNum = ansNum;
    }
}
