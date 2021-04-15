package com.company;

import java.util.Arrays;

public class HW3 {
    static public int[] Meth1() {
        int[] a = {1, 0, 0, 0, 1, 0, 1, 1, 0};
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0)
                a[i] = 1;
            else
                a[i] = 0;
        }
        return a;
    }

    static public void Meth2() {
        int[] a = new int[100];
        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }
    }

    static public void Meth3() {
        int[] a = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 6)
                a[i] *= 2;
        }
    }

    static public void Meth4() {
        int[][] a = new int[5][5];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (j == i)
                    a[i][j] = 1;
                else if (j + i == a[i].length - 1)
                    a[i][j] = 1;
                else
                    a[i][j] = 0;
            }
        }
    }

    static public int[] Meth5(int len, int InitialValue) {
        int[] a = new int[len];
        for (int i = 0; i < a.length; i++) {
            a[i] = InitialValue;
        }
        return a;
    }

    static public void Meth6(int[] mas) {
        int max = 0;
        int min = 0;
        if (mas.length > 0) {
            max = mas[0];
            min = mas[0];
        }

        for (int i = 0; i < mas.length; i++) {
            if (mas[i] > max)
                max = mas[i];
            if (mas[i] < min)
                min = mas[i];
        }
    }

    static public boolean Meth7(int[] arr){
        int rsum = 0;
        int lsum = 0;
        for (int i = 0; i < arr.length; i++) {
            rsum += arr[i];
        }
        for (int i = 0; i < arr.length; i++) {
            if(rsum == lsum)
                return true;
            rsum -= arr[i];
            lsum += arr[i];
        }
        return false;
    }
    static public int[] Meth8(int[] arr,int n){

        int len = n%arr.length;
        len = (len+arr.length)%arr.length;  // len принадлежит Z/arr.length
        // и действует на нее слева. Так как все стабилизаторы тривиальны
        // то длины орбит совпадают с подгруппой порожденной len
        // ее порядок arr.length/НОД(arr.length,len)
        // тогда число орбит НОД(arr.length,len)
        // 0,1,2,...,НОД(arr.length,len) лежат в разных орбитах, так как иначе (m) содержалась в (len)
        // m < НОД(arr.length,len) но m кратно НОД(arr.length,len)
        int arrSz = arr.length;
        int gcd = GCD(n,len);
        int first = 0;
        int newN  = 0;
        int nextPos = 0;
        int prevPos = 0;
        int transport = 0;
        int buffer = 0;
        for(int i = 0; i < gcd;i++){
            first = arr[i];
            prevPos = i;
            nextPos = (i+len) % arrSz;
            buffer = arr[i];
            transport = arr[i];
            do{
                buffer = arr[nextPos];
                arr[nextPos] = transport;
                transport = buffer;
                prevPos = nextPos;
                nextPos = (nextPos+len) % arrSz;
                // System.out.println(nextPos);
            }while(prevPos != i);
        }
        return arr;
    }
    static public int GCD(int a, int b){ // алгоритм Евклида
        if(b == 0)
           return a;
        return GCD(b, a%b);
    }
}
