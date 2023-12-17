package com.tobeto.rentacar.services.concretes;

import com.tobeto.rentacar.core.utilities.mappers.ModelMapperService;
import com.tobeto.rentacar.entities.Customer;
import com.tobeto.rentacar.repositories.CustomerRepository;
import com.tobeto.rentacar.services.abstracts.CustomerService;
import com.tobeto.rentacar.services.dtos.requests.customer.AddCustomerRequest;
import com.tobeto.rentacar.services.dtos.requests.customer.UpdateCustomerRequest;
import com.tobeto.rentacar.services.dtos.responses.customer.GetCustomerListResponse;
import com.tobeto.rentacar.services.dtos.responses.customer.GetCustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {

    private final CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetCustomerListResponse> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        List<GetCustomerListResponse> responses = customers.stream()
                .map(customer -> this.modelMapperService.forResponse()
                        .map(customer, GetCustomerListResponse.class)).collect(Collectors.toList());
        return responses;
    }

    @Override
    public GetCustomerResponse getById(int id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow();
        GetCustomerResponse response = this.modelMapperService.forResponse()
                .map(customer, GetCustomerResponse.class);

        return response;
    }

    @Override
    public void add(AddCustomerRequest addCustomerRequest) {
        Customer customer=this.modelMapperService.forRequest().map(addCustomerRequest,Customer.class);
        this.customerRepository.save(customer);
    }

    @Override
    public void update(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer=this.modelMapperService.forRequest().map(updateCustomerRequest,Customer.class);
        this.customerRepository.save(customer);

    }

    @Override
    public void delete(int id) {
        this.customerRepository.deleteById(id);
    }
}