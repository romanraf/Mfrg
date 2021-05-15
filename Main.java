package com.company;

import java.util.zip.DataFormatException;

public class Main {
    static class MySizeArrayException extends IndexOutOfBoundsException{
        MySizeArrayException(){};
        MySizeArrayException(String s){
            super(s);
        }
    }
    static class MyArrayDataException extends NumberFormatException{
        MyArrayDataException(){};
    }
    public static void main(String[] args) {
        String[][] str = new String[4][4];
        for(int i = 0;i < str.length;i++){
            for(int j = 0;j < str[i].length;j++)
                str[i][j] = "1";
        }
        try{
            System.out.println(Meth(str));
        }
        catch(MyArrayDataException | MySizeArrayException e){
            e.printStackTrace();
        }
    }
    public static int Meth(String[][] str) throws MySizeArrayException, MyArrayDataException{
        if(str.length != 4){
            throw new MySizeArrayException("Array size should be 4x4");
        }
        for(int i = 0;i < str.length;i++){
            if(str[i].length != 4)
                throw new MySizeArrayException("Array size should be 4x4");
        }
        int num = 0;
        for(int i = 0;i < str.length;i++){
            for(int j = 0;j < str[i].length;j++){
                try{
                    num += Integer.parseInt(str[i][j]);
                }
                catch(NumberFormatException){
                    throw new MyArrayDataException();
                }
            }
        }
        return num;
    }
}
