package com.nyd.osworks.api.model;

import javax.validation.constraints.NotBlank;

public class CommentInputModel {

	@NotBlank
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
