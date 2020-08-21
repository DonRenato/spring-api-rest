package com.nyd.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nyd.osworks.api.model.CommentInputModel;
import com.nyd.osworks.api.model.CommentModel;
import com.nyd.osworks.domain.exception.EntityNotFoundException;
import com.nyd.osworks.domain.model.Comment;
import com.nyd.osworks.domain.model.ServiceOrder;
import com.nyd.osworks.domain.repository.ServiceOrderRepository;
import com.nyd.osworks.domain.services.ServiceOrderManagementService;

@RestController
@RequestMapping({"/os/{serviceOrderId}/comments"})
public class CommentController {
	@Autowired
	ServiceOrderManagementService soms;
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ServiceOrderRepository osRepository;
	
	@GetMapping
	public List<CommentModel> list(@PathVariable Long serviceOrderId){
		ServiceOrder serviceOrder = osRepository.findById(
				serviceOrderId).orElseThrow(() -> new EntityNotFoundException("Service Order not found") );
		
		
		return toCollectionModel(serviceOrder.getComments());
	}
	
	

	@PostMapping
	@ResponseStatus((HttpStatus.CREATED))
	public CommentModel add(@Valid @PathVariable Long serviceOrderId, 
			@RequestBody CommentInputModel commentInput) {
		
		Comment comment = soms.addComment(serviceOrderId, commentInput.getDescription());
		
		return toModel(comment);
		
		
		
	}
	
	private CommentModel toModel(Comment comment) {
		return modelMapper.map(comment, CommentModel.class);
	}
	
	private List<CommentModel> toCollectionModel(List<Comment> comments) {
		return comments.stream().map(comment -> toModel(comment)).collect(Collectors.toList());
	}
	
	
}
