package org.scms.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class FullAjaxExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory base;

	public FullAjaxExceptionHandlerFactory(ExceptionHandlerFactory base) {
		this.base = base;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new FullAjaxExceptionHandler(base.getExceptionHandler());
	}

}
