package com.nyd.osworks.domain.services;


import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nyd.osworks.domain.exception.DomainException;
import com.nyd.osworks.domain.exception.EntityNotFoundException;
import com.nyd.osworks.domain.model.Comment;
import com.nyd.osworks.domain.model.Customer;
import com.nyd.osworks.domain.model.ServiceOrder;
import com.nyd.osworks.domain.model.ServiceOrderStatus;
import com.nyd.osworks.domain.repository.ServiceOrderRepository;
import com.nyd.osworks.domain.repository.CommentRepository;
import com.nyd.osworks.domain.repository.CustomerRepository;

@Service
public class ServiceOrderManagementService {

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	public ServiceOrder create(ServiceOrder serviceOrder) {
		Customer customer = customerRepository.findById(
				serviceOrder.getCustomer().getId()).orElseThrow(() -> new DomainException("Customer not found"));
		
		serviceOrder.setCustomer(customer);
		serviceOrder.setStatus(ServiceOrderStatus.OPEN);
		serviceOrder.setOpenningDate(OffsetDateTime.now());
		
		return serviceOrderRepository.save(serviceOrder);
	}
	
	public void finished(Long serviceOrderId) {
		ServiceOrder serviceOrder = searchOS(serviceOrderId);
		
		serviceOrder.finished();
		
		serviceOrderRepository.save(serviceOrder);
	}
	
	public void canceled(Long serviceOrderId) {
		ServiceOrder serviceOrder = searchOS(serviceOrderId);
		
		serviceOrder.canceled();
		
		serviceOrderRepository.save(serviceOrder);
	}
	
	
	public Comment addComment(Long serviceOrderId, String description) {
		ServiceOrder serviceOrder = searchOS(serviceOrderId);
		
		Comment  comment = new Comment();
		comment.setSendDate(OffsetDateTime.now());
		comment.setDescription(description);
		comment.setServiceOrder(serviceOrder);
		
		return commentRepository.save(comment);
		
	}


	private ServiceOrder searchOS(Long serviceOrderId) {
		return serviceOrderRepository.findById(
				serviceOrderId).orElseThrow(() -> new EntityNotFoundException("Service Order not found"));
	}
	
}
