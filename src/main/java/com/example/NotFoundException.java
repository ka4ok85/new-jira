package com.example;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6285867531715388561L;

	public NotFoundException(String id) {
        super(String.format("No1 entry found with id: <%s>", id));
    }
}
