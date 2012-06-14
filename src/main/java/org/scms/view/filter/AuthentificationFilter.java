package org.scms.view.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scms.view.bean.AuthentificationBean;

public class AuthentificationFilter implements Filter {

	private FilterConfig config = null;

	public void init(FilterConfig filterConfig) {
		this.config = filterConfig;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		if (request.getRequestedSessionId() != null
				&& !request.isRequestedSessionIdValid()) {
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				filterChain.doFilter(request, response);
				return;
			} else {
				response.sendRedirect(config.getInitParameter("login.url")+"?expired=true");
			}
			return;
		}
		if (session.getAttribute(AuthentificationBean.AUTH_KEY) == null) {
			response.sendRedirect(config.getInitParameter("login.url"));
		} else {
			filterChain.doFilter(request, response);
		}
	}

	public void destroy() {
		config = null;
	}

}
