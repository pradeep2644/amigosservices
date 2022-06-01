package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService{

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

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
     /* FraudCheckResponse fraudCheckResponse = restTemplate.
              getForObject(
                      "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());
*/
        FraudCheckResponse fraudsterResponse = fraudClient.isFraudster(customer.getId());
        if(fraudsterResponse.isFraudSter())
          throw  new IllegalStateException("fraudster");
        // todo : send Notification

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode...",
                                customer.getFirstName())
                )

        );


    log.info("message has been sent {}", customer.getId());
    }
}
