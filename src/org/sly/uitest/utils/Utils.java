package org.sly.uitest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	/** return the date from String format "11-Aug-1987" */
	public static Date getDateFromStandardFormat(String dateStr) {

		String format = "d-MMM-yyyy";

		return getDateFromFormat(dateStr, format);

	}

	public static Date getDateFromFormat(String dateStr, String format) {

		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;

	}

	public static Double getDoubleFromPercentageString(String str) {
		if (str == null || str.isEmpty() || str.indexOf("%") != str.length() - 1) {
			return null;
		}
		Double value = Double.valueOf(str.substring(0, str.indexOf("%")));
		return value;
	}

	public static void main(String[] args) {

		System.out.println(getDoubleFromPercentageString("60.00%"));

	}

}
