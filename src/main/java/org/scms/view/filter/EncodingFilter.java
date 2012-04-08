package org.scms.view.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING = "encoding";
	private static final String EXCLUDE = "exclude";
	private FilterConfig filterConfig;
	private String encoding = null;

	public void init(FilterConfig paramFilterConfig) {
		this.filterConfig = paramFilterConfig;
		this.encoding = paramFilterConfig.getInitParameter(ENCODING);
		System.out
				.println("EncodingFilter ver. 1.4 (c) Coldbeans info@servletsuite.com");
	}

	public void setFilterConfig(String paramString) {
	}

	public void setFilterConfig(FilterConfig paramFilterConfig) {
		this.filterConfig = paramFilterConfig;
		this.encoding = this.filterConfig.getInitParameter(ENCODING);
		System.out
				.println("EncodingFilter ver. 1.4 (c) Coldbeans info@servletsuite.com");
	}

	public void doFilter(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse, FilterChain paramFilterChain)
			throws IOException, ServletException {
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) paramServletRequest;
		String str = localHttpServletRequest.getRequestURI();
		if (!(excluded(str)))
			try {
				paramServletRequest.setCharacterEncoding(this.encoding);
			} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
				this.filterConfig.getServletContext().log(
						localUnsupportedEncodingException.getMessage());
			}
		paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
	}

	public void destroy() {
	}

	private boolean excluded(String paramString) {
		if (paramString == null)
			return false;
		String str = null;
		if ((str = this.filterConfig.getInitParameter(EXCLUDE)) != null)
			str = str + ",";
		if (str == null)
			return false;
		return (str.indexOf(paramString + ",") >= 0);
	}
}
