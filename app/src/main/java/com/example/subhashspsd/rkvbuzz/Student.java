package com.example.subhashspsd.rkvbuzz;

/**
 * Created by SubhashSpsd on 30-Sep-18.
 */

public class Student {
    private String Name;
    private String IdNo;
    private String Branch;

    public Student()
    {
    }
    public Student(String name, String id, String branch, String aClass) {
        this.Name = name;
        this.IdNo = id;
        this.Branch = branch;
    }

    public String getName() {
        return Name;
    }

    public String getIdNo() {
        return IdNo;
    }

    public String getBranch() {
        return Branch;
    }
}
