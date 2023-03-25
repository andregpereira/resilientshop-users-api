package com.github.andregpereira.resilientshop.userapi.services;

public class UsuarioAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1411861135672846093L;

	private String msg;

	public UsuarioAlreadyExistsException() {
	}

	public UsuarioAlreadyExistsException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
