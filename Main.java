package com.company;

public class Main {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new mServer();
            }
        }).start();
	    new Thread(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        }).start();
    }
}
