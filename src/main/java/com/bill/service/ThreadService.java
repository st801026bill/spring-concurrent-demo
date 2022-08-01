package com.bill.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadService {

    public void demo() {
        WorkThread worker1 = new WorkThread();
        WorkThread worker2 = new WorkThread();

        worker1.setName("[1] worker1");
        worker2.setName("[2] worker2");
        //設優先權(OS不一定要看優先權)->數字越大優先權越高
        worker1.setPriority(1);
        worker2.setPriority(10);

        worker1.start();
        worker2.start();

        log.info("main thread end");
    }
}

class WorkThread extends Thread {
    @Override
    public void run() {
        for(int rock=50; rock>0; ){
            System.out.println(Thread.currentThread().getName()+"搬了一塊石頭，剩"+(--rock)+"塊石頭");
        }
    }
}
