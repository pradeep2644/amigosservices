package com.amigoscode.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo : Check if email Valid
        // todo: Check if email not taken
        // store customer in db
        customerRepository.save(customer);
    }
}
