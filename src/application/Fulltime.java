package application;

import java.text.DecimalFormat;

/**
The fulltime class is a subclass of the employee superclass.
It defines the specific attributes of a fulltime employee, such
as their annual salary, and also calculates their pay for the 
current period.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Fulltime extends Employee {
	
	private int fulltimepayperiods = 26; //there is 26 pay periods in a year
	private double annualSalary;
	
	/**
	The parameterized constructor that specifies what specific attributes a fulltime
	employee must have.
	@param eProfile is profile consisting of employee's name, department and date of hire
	@param thePay is what they're paid each period
	@param annSalary is employee's annual salary 
	*/
	public Fulltime(Profile eProfile, double thePay, double annSalary) {
		// TODO Auto-generated constructor stub
		super(eProfile, thePay);
		this.annualSalary = annSalary;
	}
	
	/**
	Getter method that receives the inputted annual salary of an employee.
	@return the double representation of an employees annual salary
	*/
	public double getAnnualSalary() {
		return annualSalary;
	}
	
	/**
	Getter method that receive the annual salary in decimal format.
	@return the string value of the annual salary
	*/
	public String getAnnSalary() {
		return new DecimalFormat("$###,###.00").format(annualSalary); 
	}
	
	/**
	Getter method that receive the integer value that represents all 26 pay periods.
	@return the integer value that represents all pay periods in a year
	*/
	public int getFTPayPeriods() {
		return fulltimepayperiods;
	}
	
	/**
	Setter method that assigns a fulltime employee an annual salary.
	@param annSal of double type is the value of the employee's annual salary
	*/
	public void setAnnualSalary(double annSal) {
		this.annualSalary = annSal;
	}
	
	/**
	According to the type of employee, fulltime in this case, calculates 
	the earnings for the pay period. 
	*/
	@Override
	public void calculatePayment() {
		double thePay = super.getPaid();
		thePay = annualSalary/fulltimepayperiods;
		super.setPaid(thePay);
	}
	
	/**
	Gives the specified employee object, in this case fulltime employees,
	an ouptut of their information and finances within the company.
	@return string value of their profile information, and their annual salary
	*/
	@Override
	public String toString() {
		return super.toString() + "::FULL TIME::Annual Salary " + getAnnSalary();
	}
	
	/**
	Compares another object to the current employee object of type fulltime
	and checks if the object is also of the employee of type fulltime.
	@param obj of type object that is to be compared to our fulltime object
	@return true if they are the same object, false otherwise
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fulltime) {
			return super.equals(obj) && annualSalary == ((Fulltime) obj).annualSalary;
		}
		return false;
	}

}

