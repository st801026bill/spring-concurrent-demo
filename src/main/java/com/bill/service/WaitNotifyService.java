package com.bill.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaitNotifyService {
    public void demo() {
        Cookies cookies = new Cookies();
        Put put = new Put(cookies);
        Eat eat = new Eat(cookies);
        Thread dog = new Thread(eat);
        Thread master = new Thread(put);
        dog.start();
        master.start();
    }
}

@Slf4j
class Cookies {
    private static volatile boolean empty = true;
    public synchronized void put(int no) {
        while(!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("主人放了第 " + no + " 塊餅乾.");
        empty = false;
        notify();   //讓小狗進入Locked pool並等待執行

    }
    public synchronized void eat(int no) {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("小白狗吃了第 " + no + " 塊餅乾.");
        empty = true;
        notify();   //讓主人進入Locked pool並等待執行
    }
}

class Eat implements Runnable {
    Cookies cookies;
    Eat(Cookies cookies) {
        this.cookies = cookies;
    }
    @Override
    public void run() {
        for(int i=1;i<=10;i++) {
            cookies.eat(i);
        }
    }
}
class Put implements Runnable {
    Cookies cookies;
    Put(Cookies cookies) {
        this.cookies = cookies;
    }
    @Override
    public void run() {
        for(int i=1;i<=10;i++) {
            cookies.put(i);
        }
    }
}

