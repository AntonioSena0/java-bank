package org.example.mapper;

import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.model.Customer;

public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request){

        return new Customer(request.name(), request.email(), request.password(), request.document(), request.phone(), request.role());

    }

    public CustomerResponse toCustomerResponse(Customer customer){

        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getDocument(), customer.getPhone(), customer.getRole(), customer.getCreatedAt());

    }

}
