package com.bill.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SynchronizedService {
    public void demo() {
        Company company = new Company();

        Driver driver1 = new Driver(company);
        driver1.setName("driver1");
        driver1.start();

        Driver driver2 = new Driver(company);
        driver2.setName("driver2");
        driver2.start();
    }
}

@Slf4j
class Company {
    private int sum = 0;    //is locked(大家共用這個變數且不能同一時間2個程式呼叫add())
    //synchronized 將class Company鎖住(同步化)(synchronized方法一)
    public synchronized void add(int a) {
        log.info(Thread.currentThread().getName());
        int tmp = sum;
        log.info("目前的合計金額是" + tmp + "元。");
        log.info("賺到" + a + "元了。");
        tmp += a;
        log.info("合計金額是" + tmp + "元。");
        sum = tmp;
    }
}

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
class Driver extends Thread {
    private Company company;

    @Override
    public void run() {
        for(int i=0; i<3; i++) {
            //synchronized方法二
            synchronized (company) {
                company.add(50);
            }
        }
    }
}
