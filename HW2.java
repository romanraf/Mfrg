package com.company;

public class HW2 {
    static public boolean Meth1(int a, int b) {
        int sum = a+b;
        return (sum <= 20) && (sum >= 10);
    }
    static public void Meth2(int a){
        if(a>=0)
            System.out.println("Положительное");
        else
            System.out.println("Отрицательное");
    }
    static public boolean Meth3(int a){
        return a < 0;
    }
    static public void Meth4(String str,int a){
        for(int i = 0;i < a;i++)
            System.out.println(str);
    }
    static public boolean Meth5(int a){
        int prefirst = ( (a%16) + (3*a%16) ) / 4 ;  // == 4 (a = 4n & a != 16k),
                                                    // == 0 (a=16k)

        int second = a % 25;
        boolean retRes = (prefirst == 0 && second == 0) | (prefirst == 4 && a%25 != 0);   // (0,0) - a = 400k
                                                                                    // (1,x) x!= 0 a = 4k, a!= 100s;
        return  retRes;
    }
}
