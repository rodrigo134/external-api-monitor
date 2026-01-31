package com.rodrigo134.apianalyzer.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

@Service
public class MonitorService {

    private final MeterRegistry meterRegistry;
    private final RestTemplate  restTemplate;
    private final Timer timer;

    public MonitorService(MeterRegistry meterRegistry,RestTemplate restTemplate){

        this.meterRegistry = meterRegistry;
        this.restTemplate = restTemplate;
        this.timer = Timer.builder("external.api.latency")
                .description("Tempo de resposta de API externa")
                .publishPercentileHistogram()
                .register(meterRegistry);
    }


    public Map<String,Object> monitor(String urlRequest){
        
        ResponseEntity<?> response = timer.record(() ->
                restTemplate.getForEntity(urlRequest, Object.class)
        );


        Map<String,Object> result= new HashMap<>();
        result.put("url",urlRequest);
        result.put("Code",response.getStatusCode());
        return result;



    }

}
