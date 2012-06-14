package org.scms.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FullAjaxExceptionHandler extends ExceptionHandlerWrapper {

	private static final String WEB_XML = "/WEB-INF/web.xml";
	private static final String ERROR_DEFAULT_LOCATION_MISSING = "Either HTTP 500 or java.lang.Throwable error page is required in web.xml. Neither was found.";
	private static final String ATTRIBUTE_ERROR_EXCEPTION = "javax.servlet.error.exception";
	private static final String ATTRIBUTE_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
	private static final String ATTRIBUTE_ERROR_MESSAGE = "javax.servlet.error.message";
	private static final String ATTRIBUTE_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
	private static final String ATTRIBUTE_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

	private ExceptionHandler wrapped;
	private Map<Class<Throwable>, String> errorPageLocations;

	public FullAjaxExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void handle() throws FacesException {
		FacesContext context = FacesContext.getCurrentInstance();

		Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents()
				.iterator();
		if (unhandledExceptionQueuedEvents.hasNext()) {
			Throwable exception = unhandledExceptionQueuedEvents.next()
					.getContext().getException();
			unhandledExceptionQueuedEvents.remove();
			exception = unwrap(exception, FacesException.class);
			String errorPageLocation = findErrorPageLocation(exception);
			final HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			request.setAttribute(ATTRIBUTE_ERROR_EXCEPTION, exception);
			request.setAttribute(ATTRIBUTE_ERROR_EXCEPTION_TYPE,
					exception.getClass());
			request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
					exception.getMessage());
			request.setAttribute(ATTRIBUTE_ERROR_REQUEST_URI,
					request.getRequestURI());
			request.setAttribute(ATTRIBUTE_ERROR_STATUS_CODE,
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			NavigationHandler navHandler = context.getApplication()
					.getNavigationHandler();
			if (exception instanceof ViewExpiredException) {
				errorPageLocation += "?faces-redirect=true&expired=true";
			}
			navHandler.handleNavigation(context, null, errorPageLocation);
			addAfterPhaseListener(PhaseId.RENDER_RESPONSE, new Void() {
				@Override
				public void invoke() {
					request.removeAttribute(ATTRIBUTE_ERROR_EXCEPTION);
				}
			});
		}
		while (unhandledExceptionQueuedEvents.hasNext()) {
			unhandledExceptionQueuedEvents.next();
			unhandledExceptionQueuedEvents.remove();
		}

		wrapped.handle();
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	private String findErrorPageLocation(Throwable exception) {
		if (errorPageLocations == null) {
			errorPageLocations = findErrorPageLocations();
		}
		for (Map.Entry<Class<Throwable>, String> entry : errorPageLocations
				.entrySet()) {
			if (entry.getKey() == exception.getClass()) {
				return entry.getValue();
			}
		}

		for (Map.Entry<Class<Throwable>, String> entry : errorPageLocations
				.entrySet()) {
			if (entry.getKey() != null && entry.getKey().isInstance(exception)) {
				return entry.getValue();
			}
		}
		return errorPageLocations.get(null);
	}

	/**
	 * Parse <tt>web.xml</tt> and find all error page locations.
	 * 
	 * @return An ordered map of all error page locations. The key
	 *         <code>null</code> represents the default location.
	 * @throws IllegalArgumentException
	 *             When an error page location in <tt>web.xml</tt> is missing or
	 *             invalid.
	 */
	@SuppressWarnings("unchecked")
	// For the cast on Class<Throwable>.
	private static Map<Class<Throwable>, String> findErrorPageLocations() {
		Map<Class<Throwable>, String> errorPageLocations = new LinkedHashMap<Class<Throwable>, String>();
		String defaultLocation = null;
		InputStream input = null;

		try {
			input = getResourceAsStream(WEB_XML);

			if (input != null) { // Since Servlet 3.0, web.xml is optional.
				Document document = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(input);
				XPath xpath = XPathFactory.newInstance().newXPath();
				defaultLocation = xpath
						.compile("web-app/error-page[error-code=500]/location")
						.evaluate(document).trim();
				NodeList exceptionTypes = (NodeList) xpath.compile(
						"web-app/error-page/exception-type").evaluate(document,
						XPathConstants.NODESET);

				for (int i = 0; i < exceptionTypes.getLength(); i++) {
					Node node = exceptionTypes.item(i);
					Class<Throwable> exceptionClass = (Class<Throwable>) Class
							.forName(node.getTextContent().trim());
					String exceptionLocation = xpath.compile("location")
							.evaluate(node.getParentNode()).trim();

					if (exceptionClass == Throwable.class) {
						defaultLocation = exceptionLocation;
					} else {
						errorPageLocations.put(exceptionClass,
								exceptionLocation);
					}
				}
			}
		} catch (Exception e) {
			// This exception should never occur. If it occurs, then web.xml is
			// broken anyway.
			throw new RuntimeException(e);
		} finally {
			close(input);
		}

		if (defaultLocation == null || defaultLocation.isEmpty()) {
			throw new IllegalArgumentException(ERROR_DEFAULT_LOCATION_MISSING);
		} else {
			errorPageLocations.put(null, defaultLocation);
		}

		return errorPageLocations;
	}

	public static InputStream getResourceAsStream(String path) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream(path);
	}

	public static IOException close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				return e;
			}
		}

		return null;
	}

	public static <T extends Throwable> Throwable unwrap(Throwable exception,
			Class<T> type) {
		while (type.isInstance(exception) && exception.getCause() != null) {
			exception = exception.getCause();
		}

		return exception;
	}

	public interface Void {

		void invoke();
	}

	public static void addAfterPhaseListener(PhaseId phaseId,
			final Void callback) {
		addPhaseListener(new DefaultPhaseListener(phaseId) {

			private static final long serialVersionUID = -7760218897262285339L;

			@Override
			public void afterPhase(PhaseEvent event) {
				callback.invoke();
			}
		});
	}

	public static void addPhaseListener(PhaseListener phaseListener) {
		FacesContext.getCurrentInstance().getViewRoot()
				.addPhaseListener(phaseListener);
	}
}
