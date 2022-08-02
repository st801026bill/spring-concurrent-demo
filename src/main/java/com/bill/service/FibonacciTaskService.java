package com.bill.service;

import com.bill.demo.Fibonacci;
import com.bill.demo.FibonacciTask;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FibonacciTaskService {
    public void demo() {
        int num=45;

        //沒有使用平行運算
        log.info("==== 沒有使用平行運算 ====");
        long t1 = new Date().getTime();
        log.info("{}", new Fibonacci().fib(num));
        long t2 = new Date().getTime();
        log.info("花費時間: {}", t2-t1);

        //有使用平行運算
        log.info("==== 有使用平行運算 ====");
        long t3 = new Date().getTime();
        int processors = Runtime.getRuntime().availableProcessors();
        FibonacciTask task = new FibonacciTask(num);
        ForkJoinPool pool = new ForkJoinPool(processors);
        pool.invoke(task);
        log.info("{}", task.getResult());
        long t4 = new Date().getTime();
        log.info("花費時間: {}", t4-t3);
        log.info("CPU 數量: {}", processors);
    }
}