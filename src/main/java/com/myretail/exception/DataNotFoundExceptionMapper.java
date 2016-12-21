package com.myretail.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;

import com.myretail.beans.MessageRestBean;



//@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		MessageRestBean errorMessage = new MessageRestBean(ex.getMessage(), "404");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}