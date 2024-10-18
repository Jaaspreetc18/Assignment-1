package com.example.data_choice;

public class DataModel {
    private String studentName;
    private String subject;
    private double grade;
    private String semester;

    public DataModel(String studentName, String subject, double grade, String semester) {
        this.studentName = studentName;
        this.subject = subject;
        this.grade = grade;
        this.semester = semester;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
