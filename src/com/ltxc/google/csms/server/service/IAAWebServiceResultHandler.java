package com.ltxc.google.csms.server.service;

public interface IAAWebServiceResultHandler {
	void onSuccess(String result);
	void onFailure(String error);
	void onInvalidResult(String error);
}
