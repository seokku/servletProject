package com.webjjang.main.controller;

import javax.servlet.http.HttpServletRequest;

public interface Controller {
	
	// String return : jsp나 sendRedirect 정보

	public String execute(HttpServletRequest request) throws Exception;
}
