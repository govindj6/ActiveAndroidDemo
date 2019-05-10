package com.example.activeandroiddemo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Employee")
public class Employee extends Model {
    @Column(name="Name")
    public String name;
    @Column(name="Address")  //new column
    public String address;

    public Employee(){
        super();
    }
    public Employee(String name,String address) {
        super();
        this.name = name;
        this.address=address;
    }
}

