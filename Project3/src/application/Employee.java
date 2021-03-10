package application;

import java.text.DecimalFormat;

/**
The employee class is the superclass of every class
that describes a different kind of employee. When an
object of type employee is made and the specifications
are set, whatever commands are called will invoke the
inherited attributes of that method at which matches the
actions that the command expects.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Employee {
	
	private Profile empProfile = new Profile(); //profile that uniquely identifies each employee
	private double paid;
	
	/**
	This constructor helps intialize employee objects.
	@param eProfile the profile of an employee which has their name, department, and date hired
	@param paidSalary is the salary paid this period for the employee
	*/
	public Employee(Profile eProfile, double paidSalary) {
		// TODO Auto-generated constructor stub
		this.empProfile = eProfile;
		this.paid = paidSalary;
	}
	
	/**
	Getter method that gets the profile object that contains the 
	employees name, department, and date hired.
	@return the object of type profile
	*/
	public Profile getempProfile() {
		return empProfile;
	}
	
	/**
	Getter method that gets the final calculated payment of an 
	employee for this period.
	@return the double value of the final calculation of this period's payment
	*/
	public double getPaid() {
		return paid;
	}
	
	/**
	Getter method that gets the final calculated payment of an 
	employee in decimal format in dollar currency.
	@return the string of the final calculated payment in decimal format
	*/
	public String getDollarValue() {
		return new DecimalFormat("$###,##0.00").format(getPaid());
	}
	
	/**
	Setter method that sets the profile object that contains the 
	employees name, department, and date hired.
	@param emp of type profile
	*/
	public void setempProfile(Profile emp) {
		this.empProfile = emp;
	}
	
	/**
	Setter method that sets the payment that was received by the employee
	this pay period.
	@param paid of type double of the final calculation of this period's payment
	*/
	public void setPaid(double paid) {
		this.paid = paid;
	}
	
	/**
	According to the type of employee, calculates the earnings for the pay period. 
	*/
	public void calculatePayment() {
		
	}
	
	/**
	Gives the specified employee object an ouptut of 
	their information and finances within the company.
	@return string value of their profile information and the dollar value of their payment
	*/
	@Override
	public String toString() { 
		return empProfile.toString() + "::Payment " + getDollarValue();
	}
	
	/**
	Compares another object to the current employee object and
	checks if the object is also that employee.
	@param obj of type object that is to be compared to our employee object
	@return true if they are the same object, false otherwise
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee objEmp = (Employee) obj;
		if (this.empProfile.equals(objEmp.empProfile)) {
			return true;
		}
		return false;
	}

}

