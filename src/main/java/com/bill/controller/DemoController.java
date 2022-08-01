package com.bill.controller;

import com.bill.service.RunnableService;
import com.bill.service.WaitNotifyService;
import com.bill.service.ThreadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "", description = "")
@RestController
public class DemoController {

    @Autowired
    private ThreadService threadService;
    @Autowired
    private RunnableService runnableService;
    @Autowired
    private WaitNotifyService waitNotifyService;
    /**
     *  程式要執行多工要覆蓋run()
     *  Bad!!->類別不能在使用繼承
     *  工人"各"搬50塊(產生多工問題)!!!
     */
    @Operation(summary = "", description = "")
    @PostMapping("/thread")
    public String thread() {
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
    public String runnable() {
        runnableService.demo();
        return "success";
    }

    /**
     *  wait(),notify()的使用(規定要做完某些是下一個程式才能執行)
     *  synchronized
     */
    @Operation(summary = "", description = "")
    @PostMapping("/wait_notify")
    public String waitNotify() {
        waitNotifyService.demo();
        return "success";
    }
}
