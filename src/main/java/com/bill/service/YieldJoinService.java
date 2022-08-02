package com.bill.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class YieldJoinService {
    public void demo() {
        Thread father = new Thread(new FatherTread());
        father.start();
    }
}

@Slf4j
class FatherTread implements Runnable {
    @Override
    public void run() {
        log.info("爸爸下班回家.");
        log.info("爸爸準備洗澡.");
        log.info("爸爸發現沒瓦斯了.");

        log.info("爸爸發現沒瓦斯了.");
        log.info("爸爸打電話請瓦斯工人送瓦斯.");
        Thread worker = new Thread(new WorkerTread());
        log.info("爸爸等待瓦斯工人 ...");
        worker.start();
        // insert code :
//        Thread.yield();//try catch註解(讓現在程式進入blocked pool->使其他程式有機會可以被執行(機率低!!!))

        try{
//            Thread.sleep(6000);//暫停指令1(15,19註解)(若要送完瓦斯在洗->知道明確時間且要大於送到的時間)
            worker.join();//暫停指令2(15,18註解)(要讓瓦斯送完再洗->不知道明確時間)
        }
        catch(InterruptedException e) {
            System.out.println("爸爸決定今天不洗熱水澡了 !");
        }

        log.info("爸爸開始洗澡 !");
        log.info("爸爸洗完澡了 !");
    }
}

@Slf4j
class WorkerTread implements Runnable {
    @Override
    public void run() {
        log.info("");
        log.info("瓦斯工人送瓦斯中 ... ");
        try {
            for (int i=1; i<=(int)(Math.random()*6)+3; i++) {
                Thread.sleep(1000);
                log.info(i+" 分鐘, ");
            }
        }
        catch (InterruptedException ie) {
            System.err.println("瓦斯工人送瓦斯途中發生意外 !");
        }
        log.info("");
        log.info("瓦斯工人將瓦斯送到家了 !");
        log.info("瓦斯工人將瓦斯安裝完畢 !");
        log.info("");
    }
}