package com.bill.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RunnableService {
    public void demo() {
        System.out.println(Thread.currentThread().getName());
        WorkRunnable1 work1=new WorkRunnable1();
        WorkRunnable2 work2=new WorkRunnable2();

        //多工
        Thread worker1=new Thread(work1,"[1] worker1");//設名子
        Thread worker2=new Thread(work1);
        Thread worker3=new Thread(work2);
        worker2.setName("[2] worker2");//設名子
        worker3.setName("[3] worker3");

        //設優先權(OS不一定要看優先權)->數字越大優先權越高
        worker1.setPriority(1);
        worker2.setPriority(10);
        worker3.setPriority(3);

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

class WorkRunnable1 implements Runnable{
    public void run(){
        for(int i=1;i<=50;i++){
            System.out.println("工人"+Thread.currentThread().getName()+"搬了第"+i+"塊磚頭");
        }
    }
}

class WorkRunnable2 implements Runnable{
    public void run(){
        for(int i=1;i<=50;i++){
            System.out.println("工人"+Thread.currentThread().getName()+"搬了第"+i+"塊水泥");

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

