package com.bill.service;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CyclicBarrierService {
    public void demo() {
        //建3條路障,需等new Teacher()執行完才能解除(每個Student執行到await()時CyclicBarrier()會-1 直到0解除);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CyclicBarrierTeacher());
        ExecutorService pool = Executors.newCachedThreadPool();

        CyclicBarrierStudent student1 = new CyclicBarrierStudent("A", cyclicBarrier);
        CyclicBarrierStudent student2 = new CyclicBarrierStudent("B", cyclicBarrier);
        CyclicBarrierStudent student3 = new CyclicBarrierStudent("C", cyclicBarrier);
        pool.execute(student1);
        pool.execute(student2);
        pool.execute(student3);
        pool.shutdown();
    }
}

@Slf4j
class CyclicBarrierTeacher implements Runnable {
    @Override
    public void run() {
        log.info("老師走路到教室上課中");
        try{
            Thread.sleep(3000);
        }catch(Exception e){}
        System.out.println("老師到達教室...");
        System.out.println("老師開始上課");
    }
}

@Slf4j
class CyclicBarrierStudent implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private String name;
    public CyclicBarrierStudent(String name, CyclicBarrier cyclicBarrier) {
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run() {
        log.info("[{}] 走路到教室中...", name);
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("[{}] 已到教室，等待老師來上課...", name);
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("[{}] 聽課中", name);
    }
}
