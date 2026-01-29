package com.rodrigo134.apianalyzer.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MonitorService {
    private final MeterRegistry meterRegistry;
    private final RestTemplate  restTemplate;

    public MonitorService(MeterRegistry meterRegistry,RestTemplate restTemplate){

        this.meterRegistry = meterRegistry;
        this.restTemplate = restTemplate;
    }

    public Map<String,Object> monitor(String urlRequest){
        Timer timer = Timer.builder("external.api.laatency")
                .description("Tempo de resposta de API externa")
                .publishPercentiles(0.95,0.99)
                .register(meterRegistry);

        ResponseEntity<?> response = timer.record(() ->
                restTemplate.getForEntity(urlRequest, Object.class)
        );


        Map<String,Object> result= new HashMap<>();
        result.put("url",urlRequest);
        result.put("Code",response.getStatusCode());
        return result;



    }

}
