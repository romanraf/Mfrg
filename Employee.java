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

public class Main{
    public static void main(String[] args) {
        Employee[] empArr = new Employee[5];
        empArr[0] = new Employee("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30); 
        empArr[1] = new Employee("Vanov Ivan", "Engineer", "vivan@mailbox.com", "892312310", 30000, 40);
        empArr[2] = new Employee("Ivanov Van", "Engineer", "ivvan@mailbox.com", "892312311", 30000, 50);
        empArr[3] = new Employee("Ianov Ian", "Engineer", "iaian@mailbox.com", "892312312", 30000, 35);
        empArr[4] = new Employee("Vanov Van", "Engineer", "vvan@mailbox.com", "892312313", 30000, 60);
        for(Employee emp : empArr)
            if(emp.age >= 40)
                emp.ShowInfo();
    }
};

