package com.nyd.osworks.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nyd.osworks.domain.exception.DomainException;
import com.nyd.osworks.domain.model.Customer;
import com.nyd.osworks.domain.repository.CustomerRepository;


@Service
public class CRUDCustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Customer save(Customer customer) {
		
		Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
		
		if(existingCustomer != null && !existingCustomer.equals(customer)) {
			throw new DomainException("This email has already been registered");
		}
		
		return customerRepository.save(customer);
	}
	
	public void delete(Long customerId) {
		customerRepository.deleteById(customerId);
	}
	
}
