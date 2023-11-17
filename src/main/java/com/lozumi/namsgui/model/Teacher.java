package com.lozumi.namsgui.model;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Teacher extends User {
    private final String teacherNo;
    private String department;

    public Teacher(String id, String name, String phoneNo, String email, String teacherNo, String department) {
        super(id, name, phoneNo, email);
        this.teacherNo = teacherNo;
        this.department = department;
    }

    public String getTeacherNo() {
        return this.teacherNo;
    }

    public String getDepartment() {
        return this.department;
    }

    public String toString() {
        return "Teacher{id='" + this.getId() + '\'' + ", name='" + this.getName() + '\'' + ", phoneNo='" + this.getPhoneNo() + '\'' + ", email='" + this.getEmail() + '\'' + ", teacherNo='" + this.teacherNo + '\'' + ", department='" + this.department + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Teacher teacher = (Teacher)o;
            return this.teacherNo.equals(teacher.teacherNo);
        } else {
            return false;
        }
    }
}
