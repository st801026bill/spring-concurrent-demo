package com.bill.demo;

public class Fibonacci {
    public int fib(int i) {
        if(i==0 || i==1) return i;
        return fib(i-1) + fib(i-2);
    }
}
