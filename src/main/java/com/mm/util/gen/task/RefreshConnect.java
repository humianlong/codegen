package com.mm.util.gen.task;

import com.mm.util.gen.controller.SelectorController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshConnect {

    @Scheduled(cron = "0 0 3 * * ?")
    public void execute(){
        SelectorController.dbMap.forEach((k, v) -> {
            v.close();
        });
        SelectorController.jtMap.clear();
        SelectorController.dbMap.clear();
    }

}
