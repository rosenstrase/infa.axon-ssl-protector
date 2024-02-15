package infa.axon_ssl_protector;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public enum DateTime {
	INSTANCE, START_DATE, END_DATE;

	static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
	static final long TEN_YEARS_IN_MILLIS = 100 * 365 * ONE_DAY_IN_MILLIS;

	public Date getCurrentDateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public Date calculateEndDate(Date startDate, int validityTimeout) {
		return new Date(startDate.getTime() + validityTimeout * ONE_DAY_IN_MILLIS);
	}
}