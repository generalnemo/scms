package org.scms.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtil {

	public static String encodedFilename(String fileName)
			throws UnsupportedEncodingException {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		if (request.getHeader("User-Agent").indexOf("Opera") != -1
				|| request.getHeader("User-Agent").indexOf("MSIE") == -1) {
			return new String(fileName.getBytes("UTF-8"), "iso-8859-1"); // mozilla
		} else {
			StringBuilder encodedFilename = new StringBuilder();
			char[] hexdigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'A', 'B', 'C', 'D', 'E', 'F' }; // MSIE
			byte[] fileNameBytes = fileName.getBytes("UTF-8");
			for (byte fileNameByte : fileNameBytes) {
				if ((fileNameByte | 0x7F) == 0xFFFFFFFF) {
					encodedFilename.append('%');
					encodedFilename
							.append(hexdigits[(fileNameByte & (15 * 16)) / 16]);
					encodedFilename.append(hexdigits[fileNameByte & 15]);
				} else {
					encodedFilename.append((char) fileNameByte);
				}
			}
			return encodedFilename.toString();
		}
	}

	public static String formatDate(Date date, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

}
