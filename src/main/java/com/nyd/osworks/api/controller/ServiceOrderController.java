package com.nyd.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nyd.osworks.api.model.ServiceOrderInputModel;
import com.nyd.osworks.api.model.ServiceOrderModel;
import com.nyd.osworks.domain.model.ServiceOrder;
import com.nyd.osworks.domain.repository.ServiceOrderRepository;
import com.nyd.osworks.domain.services.ServiceOrderManagementService;

@RestController
@RequestMapping("/os")
public class ServiceOrderController {
	
	@Autowired
	private ServiceOrderManagementService serviceOrderManagement;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderModel create(@Valid @RequestBody ServiceOrderInputModel serviceOrderInput) {
		ServiceOrder serviceOrder = toEntity(serviceOrderInput);
		return toModel(serviceOrderManagement.create(serviceOrder));
	}
	
	@GetMapping
	public List<ServiceOrderModel> list(){
		return toCollectionModel(serviceOrderRepository.findAll());
	}
	
	@GetMapping("/{serviceOrderId}")
	public ResponseEntity<ServiceOrderModel> search(@PathVariable Long serviceOrderId){
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(serviceOrder.isPresent()) {
			ServiceOrderModel model = toModel(serviceOrder.get());
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}
	@PutMapping("/{serviceOrderId}/finishing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void finished(@PathVariable Long serviceOrderId) {
		serviceOrderManagement.finished(serviceOrderId);
	}
	
	@PutMapping("/{serviceOrderId}/canceling")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	private void canceled(@PathVariable Long serviceOrderId) {
		serviceOrderManagement.canceled(serviceOrderId);
	}
	
	private ServiceOrderModel toModel(ServiceOrder serviceOrder) {
		return  modelMapper.map(serviceOrder, ServiceOrderModel.class);
	}
	
	private List<ServiceOrderModel> toCollectionModel(List<ServiceOrder> servicesOrder) {
		return  servicesOrder.stream()
				.map(serviceOrder -> toModel(serviceOrder))
				.collect(Collectors.toList());
	}
	
	private ServiceOrder toEntity(ServiceOrderInputModel serviceOrderInput) {
		return  modelMapper.map(serviceOrderInput, ServiceOrder.class);
	}
}
