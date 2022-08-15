package com.example.demo.ControlException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Curent {
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	public static boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
		return variable >= minValueInclusive && variable <= maxValueInclusive;
	}

	public static String GetMessageBonsoirOrBonjour() {

		int currentHour = java.time.LocalTime.now().getHour();

		String message = "Salut";

		if (between(currentHour, 0, 5) || between(currentHour, 18, 23))

		{
			message = "Bonsoir";

		} else if (between(currentHour, 6, 17)) {
			message = "Bonjour";

		}
		return message;

	}

	public static String getCurrentDate() {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return formatter.format(date);
	}

	public  boolean compareDateByMonths(String date, int value) { 
		String currentDate = getCurrentDate();
		Date datetoDate = null;
		Date currentDateToDate = null;

		try {
			datetoDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
			currentDateToDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("1 " + datetoDate.getMonth() + datetoDate.getYear() * 12);
		int getMonthsFromDate = datetoDate.getMonth() + datetoDate.getYear() * 12;
		int getMonthsFromCurrentDate = currentDateToDate.getMonth() + datetoDate.getYear() * 12;

		if ((getMonthsFromCurrentDate - getMonthsFromDate) < value)
			return false;

		return true;

	}

	public static boolean compareDateByMinutes(String date, int value) { // Retourne
																			// true
																			// si
																			// la
																			// difference
																			// entre
																			// la
																			// date
																			// introduite
																			// et
																			// la
																			// date
																			// actuelle
																			// moins
																			// que
																			// (valeu)
		String currentDate = getCurrentDate();
		Date dateToDate = null;
		Date currentDateToDate = null;

		try {
			dateToDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
			currentDateToDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(currentDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		@SuppressWarnings("deprecation")
		int getMonthsFromDate = dateToDate.getMonth() + dateToDate.getYear() * 12;
		@SuppressWarnings("deprecation")
		int getMonthsFromCurrentDate = currentDateToDate.getMonth() + currentDateToDate.getYear() * 12;
		@SuppressWarnings("deprecation")
		int getMinutesFromDate = dateToDate.getHours() * 60 + dateToDate.getMinutes();
		@SuppressWarnings("deprecation")
		int getMinutesFromCurrentDate = currentDateToDate.getHours() * 60 + currentDateToDate.getMinutes();

		if ((getMonthsFromCurrentDate == getMonthsFromDate)
				&& (getMinutesFromCurrentDate - getMinutesFromDate <= value))
			return true;

		return false;

	}

	public String getRandomString(int size) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = size;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public String generateVerificaionCode(int min, int max) {
		int verificationCode = (min + new Random().nextInt(max));

		return Integer.toString(verificationCode);

	}

}

