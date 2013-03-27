package com.ltxc.google.csms.server.service.restful;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class DateParam {
	private final Date date;
	private final String dateFormatString = "yyyy-MM-dd HH:mm:ss Z";
	final DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

	public DateParam(String dateStr) throws WebApplicationException {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			this.date = null;
			return;
		}

		try {
			this.date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("Couldn't parse date string: " + e.getMessage())
					.build());
		}
	}

	public DateParam(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		if (date == null)
			return "";
		else
			return dateFormat.format(date);
	}

}
