package com.company;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    public static void main(String[] args) throws InterruptedException {
        Method1();
        Method2();
    }
    static void Method1(){
        float[] arr = new float[size];
        for(int i = 0;i < size;i++)
            arr[i] = 1;

        long startTime = System.currentTimeMillis();
        for(int i = 0;i < size;i++)
            arr[i] =  (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.println(System.currentTimeMillis() - startTime);
    }
    static void Method2() throws InterruptedException {
        float[] arr = new float[size];
        for(int i = 0;i < size;i++)
            arr[i] = 1;

        long startTime = System.currentTimeMillis();
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        Thread thread1 = new Thread(()->{
            System.arraycopy(arr, 0, arr1, 0, h);
            for(int i = 0;i < h;i++)
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        });
        Thread thread2 = new Thread(()->{
            System.arraycopy(arr, h, arr2, 0, h);
            for(int i = 0;i < h;i++)
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
