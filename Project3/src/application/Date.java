package application;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
The date class helps with verifying if a 
given date is accurate (i.e. not too old 
and not an impossibly made up one).
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Date implements Comparable<Date> { 
	private int year;
	private int month; 
	private int day;
	
	public static final int QUAD = 4; //value to help calculate if a year is a leap year
	public static final int CENT = 100; //value to help calculate if a year is a leap year
	public static final int QUATER = 400; //value to help calculate if a year is a leap year
	
	
	/**
	This parameterized constructor is taking a string in mm/dd/yyyy
	and creates a Date object that the user can input in a similar format.
	@param date of type String to create Date object 
	*/
	public Date(String date) {
		StringTokenizer dt = new StringTokenizer(date, "/");
		
		month = Integer.parseInt(dt.nextToken().trim());
		day = Integer.parseInt(dt.nextToken().trim());
		year = Integer.parseInt(dt.nextToken().trim());
	} 
	
	/**
	This constructor returns today’s date and also
	helps with checking if the given hire date of an
	employee is any later than the current (which shouldn't
	be possible).
	*/
	public Date() { 
		Calendar cal = Calendar.getInstance();
		
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
	} 
	
	/**
	Our getter method to help obtain the int value
	that represents a date's month.
	@return the month as an integer
	*/
	public int getMonth() {
		return month;
	}
	
	/**
	Our getter method that helps obtain the int
	value that represents a date's day.
	@return the day as an integer
	*/
	public int getDay() {
		return day;
	}
	
	/**
	Our getter method that helps obtain the int
	value that represents a date's year.
	@return the year as an integer
	*/
	public int getYear() {
		return year;
	}
	
	/**
	A helper method used to make it easier to discern if a year
	is a leap year, which means an extra day for February,
	or not.
	@param year of type integer that represents the date's year
	@return boolean true if the year is a leap year, false otherwise
	*/
	private boolean isLeapYear(int year) {
		boolean leapyr = false;
		
		if(year%QUAD != 0) { //not leap year
				
			return leapyr;
		}else if(year%CENT != 0) { //leap year
				
			leapyr = true;		
		}else if(year%QUAD == 0 && year%CENT == 0 && year%QUATER == 0) { //leap year
				
			leapyr = true;
		}else if(year%QUATER == 0) { //leap year 
			
			leapyr = true;
		}
		return leapyr;
	}
	
	/**
	This method is used to help verify that the date given,
	which is representing when an employee was hired, is not
	imaginary/impossible or even older than from 1900.
	@return boolean true if the date not too old and realistic, false otherwise
	*/
	public boolean isValid() { 
		
		Calendar cal = Calendar.getInstance();
		int oldestPublishedyr = 1900; //the oldest hire year allowed
		int lastDayFeb = 28;
		int lastDayFebLeap = 29;
		int monthsEnd = 30;
		int largestMonthsEnd = 31;
		
		Date currDate = new Date(); //gives us the current date
		
	
		
		if(year < oldestPublishedyr || year > currDate.getYear()) //YEAR check
		{ 
			return false; 
		}
		
		if(month > currDate.getMonth() && year == currDate.getYear()) //MONTH check
		{ 
			return false; 
		}else if(month > Calendar.DECEMBER+1 && year <= currDate.getYear()) {
			
			return false;
		}	
	
		if(year <= currDate.getYear() && (month == Calendar.JANUARY+1 || month == Calendar.MARCH+1 
				|| month == Calendar.MAY+1 || month == Calendar.JULY+1 || month == Calendar.AUGUST+1 
				|| month == Calendar.OCTOBER+1 || month == Calendar.DECEMBER+1) 
				&& (day < cal.getMinimum(Calendar.DAY_OF_MONTH) || day > largestMonthsEnd)) { //Making sure the range of days for these months are 31
				return false;
		} else if(year <= currDate.getYear() && (month == Calendar.APRIL+1 || month == Calendar.JUNE+1
				|| month == Calendar.SEPTEMBER+1 || month == Calendar.NOVEMBER+1) 
				&& (day < cal.getMinimum(Calendar.DAY_OF_MONTH) || day > monthsEnd)) { //Making sure the range of days for these months are 30
				return false;
		} 
		
		if((day > currDate.getDay() || day > cal.getMaximum(Calendar.DAY_OF_MONTH)) &&  month == currDate.getMonth() && year == currDate.getYear()) { //DAY check
			
			return false; 
		}else if(day < cal.getMinimum(Calendar.DAY_OF_MONTH) && year <= currDate.getYear()) {
			
			return false;
		}
		
		if(isLeapYear(year) == false && month == Calendar.FEBRUARY+1
				&& (day < cal.getActualMinimum(Calendar.DAY_OF_MONTH) || day > lastDayFeb)) {
			return false;
		}else if(isLeapYear(year) == true && month == Calendar.FEBRUARY+1
				&& (day < cal.getActualMinimum(Calendar.DAY_OF_MONTH) || day > lastDayFebLeap)) {
			return false;
		}
			
		return true;	
	}
	
	/**
	This method obtains the month, day an year variables of
	a date and returns a string variable to be processed and
	compared.
	@return a string variable of the month, day and year in mm/dd/yyyy format
	*/
	public String toString() {
		return  getMonth() + "/" + getDay() + "/" + getYear();
	}

	/**
	This method is what implements the Comparable functional interface.
	We are comparing the values of the dates so that they could follow
	and be compared to relative to real time.
	@param date is the Date type being compared to another date
    @return 1 if our date is lexicographically greater, -1 if otherwise, 0 if they are equal
	*/
	@Override
	public int compareTo(Date date) { //return 1, 0, or -1 
		int lessThan = -1;
		
		if(year == date.year && month == date.month && day == date.day){
			return 0; //they are the same date
		}
		if(year > date.year) {
			return 1;
		}
		if((year == date.year) && (month > date.month)){
			return 1;
		}
		if((year == date.year) && (month == date.month) && (day > date.day)){
			return 1; 
		}
		return lessThan;
	}
	
	/**
	We designed the test cases to thoroughly test 
	the isValid() method in this testbed main. 
	The Test Specifications document will exlpain
	the purpose of each test case. With each date
	and its respective boolean, there should either
	be an output that prints the given date or prints
	the string literal "Invalid Date!"
	@param args array of String argument
	*/
	public static void main(String[] args) {
		Date todaysDate = new Date();
		System.out.println("This is today's date: " + todaysDate.toString());
		
		Date date1 = new Date("01/0/2000"); //no such thing as day 0, invalid
		Boolean bool1 = date1.isValid();
		if(bool1 == true) {
			System.out.println(date1.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date2 = new Date("2/29/2008"); //valid
		Boolean bool2 = date2.isValid();
		if(bool2 == true) {
			System.out.println(date2.toString());
		}else {
			System.out.println("Invalid Date!");
		}

		Date date3 = new Date("02/32/2000"); //should print invalid date since no such thing as Feb 30th
		Boolean bool3 = date3.isValid();
		if(bool3 == true) {
			System.out.println(date3.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date4 = new Date("3/31/2005"); //valid
		Boolean bool4 = date4.isValid();
		if(bool4 == true) {
			System.out.println(date4.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date5 = new Date("10/12/1999"); //valid
		Boolean bool5 = date5.isValid();
		if(bool5 == true) {
			System.out.println(date5.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date6 = new Date("03/10/2022"); //invalid
		Boolean bool6 = date6.isValid();
		if(bool6 == true) {
			System.out.println(date6.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date7 = new Date("06/31/2000"); //invalid June only has 30 days
		Boolean bool7 = date7.isValid();
		if(bool7 == true) {
			System.out.println(date7.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date8 = new Date("31/31/2021"); //invalid
		Boolean bool8 = date8.isValid();
		if(bool8 == true) {
			System.out.println(date8.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date9 = new Date("3/31/1900"); //valid
		Boolean bool9 = date9.isValid();
		if(bool9 == true) {
			System.out.println(date9.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
		Date date11 = new Date("12/31/1899"); //invalid
		Boolean bool11 = date11.isValid();
		if(bool11 == true) {
			System.out.println(date11.toString());
		}else {
			System.out.println("Invalid Date!");
		}
		
	}
		
}

