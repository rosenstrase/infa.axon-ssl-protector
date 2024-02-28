package gcs.infa.maven.axonsslprotector.util;

import java.util.Calendar;
import java.util.Date;

public enum DateTime {
	INSTANCE, START_DATE, END_DATE, validityPeriodOrNull, DEFAULT_VALIDITY_PERIOD;

	public static Date calculateEndDate(Date startDate, int validityDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_YEAR, validityDays);
		return calendar.getTime();
	}
}