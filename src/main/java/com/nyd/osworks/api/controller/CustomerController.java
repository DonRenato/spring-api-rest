package com.nyd.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nyd.osworks.domain.model.Customer;
import com.nyd.osworks.domain.repository.CustomerRepository;
import com.nyd.osworks.domain.services.CRUDCustomerService;


@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CRUDCustomerService crudCustomer;

	@GetMapping()
	public List<Customer> list() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> listById(@PathVariable Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		
		if(customer.isPresent()) 
			return ResponseEntity.ok(customer.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Customer create(@Valid @RequestBody Customer customer) {
		return crudCustomer.save(customer);
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> update(@Valid @PathVariable Long customerId, @RequestBody Customer customer) {
		
		
		if(!customerRepository.existsById(customerId))
			return ResponseEntity.notFound().build();
		
		customer.setId(customerId);
		customer = crudCustomer.save(customer);
		
		
		return ResponseEntity.ok(customer);	
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> delete(@PathVariable Long customerId) {
		if (!customerRepository.existsById(customerId))	
				return ResponseEntity.notFound().build();
		
		crudCustomer.delete(customerId);
		return ResponseEntity.noContent().build();
	}
	
	
}
