package com.amigoscode.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService{

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo : Check if email Valid
        // todo: Check if email not taken
        customerRepository.saveAndFlush(customer);

        // check if Fraudster
      FraudCheckResponse fraudCheckResponse = restTemplate.
              getForObject(
                      "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());

      if(fraudCheckResponse.isFraudSter())
          throw  new IllegalStateException("fraudster");
        // todo : send Notification
    }
}
