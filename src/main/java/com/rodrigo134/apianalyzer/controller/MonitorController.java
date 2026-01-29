package com.rodrigo134.apianalyzer.controller;


import com.rodrigo134.apianalyzer.service.MonitorService;
import io.micrometer.core.instrument.Metrics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.monitor.Monitor;
import java.util.Map;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private MonitorService monitorService;

    public MonitorController(MonitorService monitorService){
        this.monitorService= monitorService;

    }
    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Map<String,Object>> getLatency(@RequestBody Map<String, Object> bodyrequest){
        String url = (String)bodyrequest.get("url");
        Map<String, Object> result = monitorService.monitor(url);


        return ResponseEntity.ok(result);

   }

}
