package com.bill.service;

import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LockService {
    public void demo() {
        CompanyByLock company = new CompanyByLock();
        DriverByLock driver1 = new DriverByLock(company);
        driver1.setName("[1] driver 1");
        driver1.start();

        DriverByLock driver2 = new DriverByLock(company);
        driver2.setName("[2] driver 2");
        driver2.start();
    }
}

@Slf4j
class CompanyByLock {
    private final ReentrantLock lock = new ReentrantLock();
    private int sum = 0;
    public void add(int a) {
        try {
            lock.lock();
            log.info(Thread.currentThread().getName());
            int tmp = sum;
            log.info("目前的合計金額是" + tmp + "元。");
            log.info("賺到" + a + "元了。");
            tmp += a;
            log.info("合計金額是" + tmp + "元。");
            sum = tmp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
class DriverByLock extends Thread {
    private CompanyByLock company;

    @Override
    public void run() {
        for(int i=0; i<3; i++) {
            synchronized (company) {
                company.add(50);
            }
        }
    }
}