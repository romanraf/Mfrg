package com.company;

public class Employee {
    public String FullName;
    public String position;
    public String email;
    public String PhoneNum;
    public int salary;
    public int age;
    public Employee(String Fname,String Pos,String Eml, String Pnum,int Slr,int Age){
        FullName = Fname;
        position = Pos;
        email = Eml;
        PhoneNum = Pnum;
        salary = Slr;
        age = Age;
    }
    public void ShowInfo()
    {
        System.out.println(FullName + '\n' + position + '\n' + email + '\n' + PhoneNum + '\n'+
                    salary + '\n' + age);

    }
};

