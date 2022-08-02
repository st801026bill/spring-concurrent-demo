package com.bill.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CountDownService {
    public void demo() {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Student student1 = new Student("A", countDownLatch);
        Student student2 = new Student("B", countDownLatch);
        Student student3 = new Student("C", countDownLatch);
        Teacher teacher = new Teacher(countDownLatch);

        pool.execute(student1);
        pool.execute(student2);
        pool.execute(student3);
        pool.execute(teacher);

        pool.shutdown();
    }
}

@RequiredArgsConstructor
@Slf4j
class Teacher implements Runnable {
    private final CountDownLatch countDownLatch;

    @Override
    public void run() {
        log.info("老師等學生寫完功課");
        try {
            countDownLatch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("老師開始改學生功課");
    }
}

@Slf4j
class Student implements Runnable {
    private CountDownLatch countDownLatch;
    private String name;

    public Student(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.info("[{}] 正在寫功課", name);
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("[{}] 做完功課了", name);
        countDownLatch.countDown();
    }
}
