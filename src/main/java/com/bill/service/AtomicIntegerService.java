package com.bill.service;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AtomicIntegerService {
    public void demo() {
        log.info(Thread.currentThread().getName());
        WorkAutoInteger1 work1 = new WorkAutoInteger1();
        WorkAutoInteger2 work2 = new WorkAutoInteger2();

        //多工
        Thread worker1 = new Thread(work1, "[1] worker1");
        Thread worker2 = new Thread(work1, "[2] worker2");
        Thread worker3 = new Thread(work2, "[3] worker3");

        worker1.start();
        worker2.start();
        worker3.start();

        //讓主程式暫停(Thread.sleep())
        try{
            Thread.sleep(1000);
        }catch(Exception e1){
            System.out.println(e1.getMessage());
        }

        System.out.println("主程式結束");
    }
}

@Slf4j
class WorkAutoInteger1 implements Runnable {
    /*
    @Override
    public void run(){
        for(int i=1;i<=50;i++){
            System.out.println("工人"+Thread.currentThread().getName()+"搬了第"+i+"塊磚頭");
        }
    }
    */
    @Override
    public void run() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        for(atomicInt.set(1); atomicInt.get()<=50;) {
            log.info("工人"+Thread.currentThread().getName()+"搬了第"+atomicInt.getAndIncrement()+"磚頭");
        }
    }
}

@Slf4j
class WorkAutoInteger2 implements Runnable {
    @Override
    public void run() {
        for(int i=1; i<=50; i++) {
            log.info("工人"+Thread.currentThread().getName()+"搬了第"+i+"塊水泥");

            if(i==25){
                //讓Worker2暫停(Thread.sleep())
                System.out.println("工人"+Thread.currentThread().getName()+"休息");
                try{
                    Thread.sleep(1000);
                }catch(Exception e1){
                    System.out.println(e1.getMessage());
                }
            }
        }
    }
}