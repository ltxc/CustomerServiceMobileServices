package com.ltxc.google.csms.server.domain;

public interface IValidationStoredProcedure {
	boolean isValidated();
	String getValidationMessage();
}
