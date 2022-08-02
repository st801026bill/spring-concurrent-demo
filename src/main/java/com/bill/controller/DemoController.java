package com.bill.controller;

import com.bill.service.SynchronizedService;
import com.bill.service.WaitNotifyService;
import com.bill.service.ThreadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "", description = "")
@RestController
@RequiredArgsConstructor
public class DemoController {
    private final ThreadService threadService;
    private final SynchronizedService runnableService;
    private final WaitNotifyService waitNotifyService;
    private final SynchronizedService synchronizedService;

    /**
     *  程式要執行多工要覆蓋run()
     *  Bad!!->類別不能在使用繼承
     *  工人"各"搬50塊(產生多工問題)!!!
     */
    @Operation(summary = "", description = "")
    @PostMapping("/thread")
    public String demoThread() {
        threadService.demo();
        return "success";
    }

    /**
     *  程式要執行多工要實作Runnable
     *  Better!!
     *  工人"各"般50塊
     */
    @Operation(summary = "", description = "")
    @PostMapping("/runnable")
    public String demoRunnable() {
        runnableService.demo();
        return "success";
    }

    /**
     *  wait(),notify()的使用(規定要做完某些是下一個程式才能執行)
     *  synchronized
     */
    @Operation(summary = "", description = "")
    @PostMapping("/wait_notify")
    public String demoWaitNotify() {
        waitNotifyService.demo();
        return "success";
    }

    @Operation(summary = "", description = "")
    @PostMapping("/synchronized")
    public String demoSynchronized() {
        synchronizedService.demo();
        return "success";
    }

}
