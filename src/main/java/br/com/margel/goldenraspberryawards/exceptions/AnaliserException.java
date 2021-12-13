package br.com.margel.goldenraspberryawards.exceptions;

public class AnaliserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AnaliserException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
