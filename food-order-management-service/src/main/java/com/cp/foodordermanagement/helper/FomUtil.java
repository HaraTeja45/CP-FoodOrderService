package com.cp.foodordermanagement.helper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class FomUtil {

	public LocalTime convertStringtoLocalTime(String timeString) {

		if (!Objects.isNull(timeString)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
			return LocalTime.parse(timeString, formatter);
		} else {
			return null;
		}

	}

}
