package com.lozumi.namsgui.model;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;

public class Student extends User {
    private final String studentNo;
    private boolean gender;
    private String grade;
    private String department;
    private final ArrayList<Team> teamList;

    public Student(String id, String name, String phoneNo, String email, String studentNo, boolean gender, String grade, String department) {
        super(id, name, phoneNo, email);
        this.studentNo = studentNo;
        this.gender = gender;
        this.grade = grade;
        this.department = department;
        this.teamList = new ArrayList();
    }

    public String getStudentNo() {
        return this.studentNo;
    }

    public boolean getGender() {
        return this.gender;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getDepartment() {
        return this.department;
    }

    public ArrayList<Team> getTeamList() {
        return this.teamList;
    }

    public String toString() {
        return "Student{id='" + this.getId() + '\'' + ", name='" + this.getName() + '\'' + ", phoneNo='" + this.getPhoneNo() + '\'' + ", email='" + this.getEmail() + '\'' + ", studentNo='" + this.studentNo + '\'' + ", gender=" + this.gender + ", grade='" + this.grade + '\'' + ", department='" + this.department + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Student student = (Student)o;
            return this.studentNo.equals(student.studentNo);
        } else {
            return false;
        }
    }
}
