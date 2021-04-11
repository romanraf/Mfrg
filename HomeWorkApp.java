package com.company;

public class HomeWorkApp {
    public static void printThreeWords() {
        System.out.println("_Orange" + '\n'
                + "_Banana" + '\n'
                + "_Apple"
                );
    }
    public static void checkSumSign() {
        int a = 10;
        int b = 20;
        int sum = a+b;
        if(sum >= 0)
            System.out.println("Сумма положительная");
        else
            System.out.println("Сумма отрицательная");
    }
    public static void printColor(){
        int value = 150;
        if(value <= 0)
            System.out.println("Красный");
        else if(value <= 100)
            System.out.println("Желтый");
        else
            System.out.println("Зеленый");
    }
    public static void compareNumbers(){
        int a = 10;
        int b = 20;
        if( a>= b)
            System.out.println("a >= b");
        else
            System.out.println("a < b");
    }
    static public boolean Meth5(int a){
        int prefirst = ( (a%16) + (3*a%16) ) / 4 ; // == 4 (a = 4n & a != 16k),
                                                   // == 0 (a=16k)

        int first = 3*prefirst % 11;  // == 1 (a = 4n & a != 16k),
                                      // == 0 (a=16k)

        int second = a % 25;
        boolean retRes = (first == 0 && second == 0) | (first == 1 && a%25 != 0); // (0,0) - a == 400k
                                                                                  // (1,x) x!= 0 a = 4k, a!= 100s;
        return  retRes;
    }
    public static void main(String[] args) {
        // write your code here
        /*double b = Math.pow(25,5);
        int n_num = 9765625 %400;
        int c_num = 390625%400; //25^20
        double b_num = Math.pow(c_num,2); //390625
        int num = 1048576 %400;
        int num2 = num*4 %400;
        int num3 = 50625%400;
        System.out.println(num3);*/
       // System.out.println("    1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15");
       System.out.println(Meth5(100) + " " + Meth5(24) + " " + Meth5(400) + " " + Meth5(4));
       /* for (int i = 1; i < 16;i++)
            System.out.print("   "+i +"| ");
        for(int i = 1; i < 16;i++) {
            System.out.print(i+": ");
            for(int j = 1; j<16;j++) {
                System.out.print(" "+i*j%16);
            }
           System.out.print(" || "+ ((i%16) + (3*i%16)));
            System.out.println('\n');

        }
        System.out.println(2^1);*/
    }
}
