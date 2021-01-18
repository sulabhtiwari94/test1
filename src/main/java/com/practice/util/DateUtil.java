/**
 *
 */
package com.practice.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author sulabhtiwari
 *
 */
public final class DateUtil {

	/**
	 * Will return current UTC Date Time.
	 *
	 * @return
	 */
	public static Date getCurrentUTCTime() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return calendar.getTime();
	}

}
