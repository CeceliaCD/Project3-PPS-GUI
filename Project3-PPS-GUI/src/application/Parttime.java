package application;

import java.text.DecimalFormat;

/**
The parttime class is one of the subclasses of the Employee
superclass. It defines the components of an employee that
works at a company parttime. It is responsible for obtaining
and setting the hourly rate of a parttime employee as well as
calculating what they earn for the current pay period.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Parttime extends Employee {
	
	private double hourlyRate; //how much a parttimer is paid per hour
	private static final double OVERTIME = 1.5; //represents time and a half regular hourly for more than 80 hours
	private int hoursWorked; //hours that a parttimer worked, int bc paid every hour worked
	
	/**
	The parameterized constructor that specifies what specific attributes a parttime
	employee must have.
	@param eProfile is profile consisting of employee's name, department and date of hire
	@param paidSalary is what an employee is paid each period
	@param hrlyrate is an employee's hourly rate
	@param hrsWorked is how many hours an employee worked
	*/
	public Parttime(Profile eProfile, double paidSalary, double hrlyrate, int hrsWorked) {
		// TODO Auto-generated constructor stub
		super(eProfile, paidSalary);
		this.hourlyRate = hrlyrate;
		this.hoursWorked = hrsWorked;
	}
	
	/**
	Getter method to obtain an employee's hourly rate of type double.
	@return double value representing hourly rate
	*/
	public double getDoubleHourlyRate() {
		return hourlyRate;
	}
	
	/**
	Getter method to obtain an employee's hourly rate of type double and
	and transform it to type String via DecimalFormat.
	@return the string value of hourly rate
	*/
	public String getHourlyRate() {
		return new DecimalFormat("$##.00").format(hourlyRate);	
	}
	
	/**
	Getter method to obtain how many hours an employee worked during 
	a pay period.
	@return the integer value of the hours an employee worked
	*/
	public int getHoursWorked() {
		return hoursWorked;
	}
	
	/**
	Setter method to give the rate at which employees are paid per hour.
	@param rate is the double representation of the rate the employee is paid
	*/
	public void setHourlyRate(double rate) {
		this.hourlyRate = rate;
	}
	
	/**
	Setter method to give the hours that the employee worked that period.
	@param hours is the integer value that shows how many hours were worked
	*/
	public void setHoursWorked(int hours) {
		this.hoursWorked = hours;
	}
	
	/**
	According to the type of employee, parttime in this case, calculates the 
	earnings for the pay period.
	*/
	@Override
	public void calculatePayment() { 
		int parttimeTotalHrs = 80; //The 2 week pay period
		int hoursLimit = 100; //hours worked cannot exceed 100 hours per week
		if(hoursWorked >= 0) {
			double thePay = super.getPaid();
			if(hoursWorked <= parttimeTotalHrs) {
				thePay = hourlyRate * hoursWorked;
			}else if(hoursWorked > parttimeTotalHrs && hoursWorked <= hoursLimit) {
				int overtimeDiff = hoursWorked - parttimeTotalHrs; //calculates the extra overtime hours
				thePay = (hourlyRate * parttimeTotalHrs) + (OVERTIME * hourlyRate * overtimeDiff);
			}
			super.setPaid(thePay);
		}
	}
	
	/**
	Gives the specified employee object, in this case employees that are parttime,
	an ouptut of their information and finances within the company.
	@return string value of their profile information, their hourly rate, and how many hours they worked
	*/
	@Override
	public String toString() {
		return super.toString() + "::PART TIME::Hourly Rate " + getHourlyRate() + "::Hours worked this period: " + Integer.toString(hoursWorked);
	}
	
	/**
	Compares another object to the current employee object of type parttime
	and checks if the object is also of the employee of type parttime.
	@param obj of type object that is to be compared to our parttime object
	@return true if they are the same object, false otherwise
	*/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Parttime) {
			return super.equals(obj) && hourlyRate == ((Parttime) obj).hourlyRate;
		}
		return false;
	}

}
