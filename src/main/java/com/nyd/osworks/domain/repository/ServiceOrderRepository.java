package com.nyd.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nyd.osworks.domain.model.ServiceOrder;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

}
