package com.example.xinshuo3;

public class People {
    private String score;
    private String username;
    private String major;
    private String grade;
    private String first_course;
    private String second_course;
    private String third_course;
    public People(String setUsername, String setMajor, String setGrade, String setFirst_course,
                  String setSecond_course, String setThird_course, String setScore) {
        score = setScore;
        username = setUsername;
        major = setMajor;
        grade = setGrade;
        first_course = setFirst_course;
        second_course = setSecond_course;
        third_course = setThird_course;
    }
    public String getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getFirst_course() {
        return first_course;
    }

    public String getSecond_course() {
        return second_course;
    }

    public String getThird_course() {
        return third_course;
    }
}
