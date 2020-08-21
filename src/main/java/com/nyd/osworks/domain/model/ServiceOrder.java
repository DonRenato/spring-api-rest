package com.nyd.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.nyd.osworks.domain.exception.DomainException;



@Entity
public class ServiceOrder {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Customer customer;
	
	private String description;
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private ServiceOrderStatus status;
	
	private OffsetDateTime OpenningDate;
	private OffsetDateTime EndDate;
	
	@OneToMany(mappedBy = "serviceOrder")
	private List<Comment> comments = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		return OpenningDate;
	}
	public void setOpenningDate(OffsetDateTime openningDate) {
		OpenningDate = openningDate;
	}
	public OffsetDateTime getEndDate() {
		return EndDate;
	}
	public void setEndDate(OffsetDateTime endDate) {
		EndDate = endDate;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceOrder other = (ServiceOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	public void finished() {
		if(CanNotBeFinishedorCanceled())
			throw new DomainException("Service order don't be finished");
		
		setStatus(ServiceOrderStatus.FINISHED);
		setEndDate(OffsetDateTime.now());
		
	}
	public void canceled() {
		if(CanNotBeFinishedorCanceled())
			throw new DomainException("Service order don't be canceled");
		
		setStatus(ServiceOrderStatus.CANCELED);
		setEndDate(OffsetDateTime.now());
	}
	
	public boolean CanBeFinishedorCanceled() {
		return ServiceOrderStatus.OPEN.equals(getStatus());
	}
	
	public boolean CanNotBeFinishedorCanceled() {
		return !CanBeFinishedorCanceled();
	}
	
	
	
	
	
}
