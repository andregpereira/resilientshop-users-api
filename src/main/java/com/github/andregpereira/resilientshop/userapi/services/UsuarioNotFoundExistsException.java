package com.github.andregpereira.resilientshop.userapi.services;

public class UsuarioNotFoundExistsException extends RuntimeException {

	private String msg;

	public UsuarioNotFoundExistsException() {
	}

	public UsuarioNotFoundExistsException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
