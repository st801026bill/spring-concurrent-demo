package com.bill.demo;

import java.util.concurrent.RecursiveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class FibonacciTask extends RecursiveTask<Integer> {
    private int num;
    private int result;

    public FibonacciTask(int num) {
        this.num = num;
    }

    public int getResult() {return result;}

    @Override
    protected Integer compute() {
        if(num<29)
            result = new Fibonacci().fib(num);
        else {
            FibonacciTask task1 = new FibonacciTask(num - 1);
            task1.fork();
            FibonacciTask task2 = new FibonacciTask(num -2);
            result = task2.compute() + task1.join();
        }
        return result;
    }
}