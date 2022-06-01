package com.amigoscode.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {


    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public com.amigoscode.clients.fraud.FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId){
        log.info("under Controller {}", customerId);
        boolean isFraudeulentCustomer = fraudCheckService.isFraudeulentCustomer(customerId);
        return new com.amigoscode.clients.fraud.FraudCheckResponse(isFraudeulentCustomer);
    }
}
