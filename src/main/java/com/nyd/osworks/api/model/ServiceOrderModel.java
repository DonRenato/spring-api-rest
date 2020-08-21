package com.nyd.osworks.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.nyd.osworks.domain.model.ServiceOrderStatus;

public class ServiceOrderModel {

	private Long id;
	private CustomerResumeModel customer;
	private String description;
	private BigDecimal price;
	private ServiceOrderStatus status;
	private OffsetDateTime openningDate;
	private OffsetDateTime endDate;
	
	
	
	public CustomerResumeModel getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerResumeModel customer) {
		this.customer = customer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public ServiceOrderStatus getStatus() {
		return status;
	}
	public void setStatus(ServiceOrderStatus status) {
		this.status = status;
	}
	public OffsetDateTime getOpenningDate() {
		return openningDate;
	}
	public void setOpenningDate(OffsetDateTime openningDate) {
		this.openningDate = openningDate;
	}
	public OffsetDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
}


