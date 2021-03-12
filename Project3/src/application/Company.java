package application;

import java.io.File; // unused import ?
import java.util.Scanner; // unused import

/**
The company class is responsible for all the changes that
happens within the employee list. It contains helper methods
as well as methods used to be called when a user inputs a command.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Company {
	private Employee[] emplist; //company list of employees
	private int numEmployee; //total number of employees currently in company list
	private int CAPACITY = 4; //intial size of company list, as well as the rate at which it grows by
	
	/**
	The default constructor to make empty employee system.
	*/
	public Company() {
		this.emplist = new Employee[CAPACITY]; //initial length of 4
		this.numEmployee = 0; //no employees in company yet
	}
	
	/**
	A helper method used within the public methods in 
	this class to find the index of a requested employee.
	@param employee object is being searched for
	@return the integer value representing the index at which the employee is located
	*/
	private int find(Employee employee) {
		for (int i = 0; i < emplist.length; i++) 
		{
			if (this.emplist[i] == null) 
			{
				return -1;
			}
			if (emplist[i].getempProfile().equals(employee.getempProfile()))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	A helper method we use in the add() method to grow
	the array everytime it reaches maximum capacity. 
	*/
	private void grow() { 
		Employee[] temp = new Employee[emplist.length + CAPACITY];
		for (int i = 0; i < emplist.length; i++) {
			temp[i] = emplist[i];
		}
		emplist = temp;
	}
	
	/**
	This method adds employees to our employee array. It calls
	both the grow() and find() helper methods.
	@param employee object being added
	@return true if the employee was added to the array, false otherwise
	*/
	public boolean add(Employee employee) { // need to determine if employee is PT FT or M
		if (numEmployee % CAPACITY == 0 && numEmployee > 1) {
			grow();
		}
		if (find(employee) == -1) { //checking if this employee is already in the company, can only be added in if not found
			emplist[numEmployee] = employee;
			numEmployee++;
			return true;
		}
		else { //if the find() helper method does not result into -1, then the employee exists in the company
			return false;
		}
	}
	
	/**
	This method deletes a given employee from the array.
	It calls the find() helper method to make it easier 
	to find the index of the meployee that needs to be removed.
	@param employee object being removed
	@return true if the employee has been removed, false otherwise
	*/
	public boolean remove(Employee employee) { // might have to change
		int rNum = find(employee);
		if (rNum == -1) {
			return false;
		}
		for (int i = rNum; i < numEmployee - 1; i++) {
			emplist[i] = emplist[i + 1];
		}
		emplist[numEmployee - 1] = null;
		numEmployee--;
		return true; //maintain the original sequence 
	} 
	
	/**
	This method sets the hours that a parttime employee has worked
	during the pay period. Necessary to call before the periods 
	payments are calculated, otherwise some parttimers are going
	home with no money.
	@param employee object that is also of type parttime
	@return true if the employee's hours for this period has been set, false otherwise
	*/
	public boolean setHours(Employee employee) { // set working hours for a part time
		int limit = 100;
		int emp = find(employee);
		if (emp == -1) {
			return false;
		} else {
			if (emplist[emp] instanceof Parttime) {
				Parttime parttimeEmp = (Parttime) employee;
				int hoursWorked = parttimeEmp.getHoursWorked();
				if(hoursWorked < 0 || hoursWorked > limit) {
					return false;
				}
				Parttime parttimer = (Parttime) emplist[emp];
				parttimer.setHoursWorked(hoursWorked);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	This method is to be called in the PayrollProcessing class when a
	user commands the program to give the employees their bi-weekly
	payments. 
	*/
	public void processPayments() { //process payments for all employees
		if(numEmployee > 0) { //Checking to see that there are employees at the company
			for(int i = 0; i < emplist.length; i++) { //search through our employee list
				if(emplist[i] != null) { //assurance that there will be no NullPointerExceptions
						emplist[i].calculatePayment(); //Polymorphism should take care of calculating the payment of every type of employee	
				}
			}
		}
	} 
	
	/**
 	This method is invoked to print the current state of the employee list.
	*/
	public void print() { //print earning statements for all employees
		if(numEmployee != 0) {
			System.out.println("--Printing earning statements for all employees--");
			for(int i=0; i < emplist.length; i++) 
			{
				if (emplist[i] != null) //to ensure no null elements are printed
				{  
					emplist[i].toString();
				}
			}
		}else {
			System.out.println("Employee database is empty.");
		}
	} 
	
	/**
	This method is invoked to print the employee list by department in 
	alphabetical order.
	*/
	public void printByDepartment() { //print earning statements by department 
		if(numEmployee != 0) {
			Employee tempEmpDept;
			
			for(int i=0; i < emplist.length; i++)
			{
				for(int j=i+1; j < emplist.length; j++)
				{
					if(emplist[i] != null && emplist[j] != null) //helps with null elements
					{ 
						String dept1 = emplist[i].getempProfile().getDept();
						String dept2 = emplist[j].getempProfile().getDept();
						if(dept1.compareTo(dept2) > 0) //comparing the departments alphabetically to order the employees that way
						{
							tempEmpDept = emplist[i];
							emplist[i] = emplist[j];
							emplist[j] = tempEmpDept;	
						}else if(dept1.compareTo(dept2) == 0) {
							String tempEmpName1 = emplist[i].getempProfile().getName();
							String tempEmpName2 = emplist[j].getempProfile().getName();
							if(tempEmpName1.compareTo(tempEmpName2) > 0) //when employees belong to the same department, we then order them alphabetically by name
							{
								Employee temp =  emplist[i];
								emplist[i] = emplist[j];
								emplist[j] = temp;
							}
						}
					}else if(emplist[i] == null && emplist[j] != null) { 
						emplist[i] = emplist[j];
					}else if(emplist[i] != null && emplist[j] == null) {
						continue;
					}
				}
			}
			
			System.out.println("--Printing earning statements by department--");
			for(int k=0; k < emplist.length; k++) {
				if (emplist[k] != null) //print all the non-null elements of employee list
				{
					emplist[k].toString();
				}	
			}	
		}else {
			System.out.println("Employee database is empty.");
		}
	} 
	
	/**
	This method is invoked to print the employee list by the dates each 
	employee was hired in ascending order.
	*/
	public void printByDate() { //print earning statements by date hired
		if(numEmployee != 0) {
			Employee tempEmployee;
			
			for(int i=0; i < emplist.length; i++)
			{
				for(int j=i+1; j< emplist.length; j++)
				{
					if(emplist[i] != null && emplist[j] != null) //helps with null elements
					{ 
						Date dateHired1= emplist[i].getempProfile().getDateHired();
						Date dateHired2= emplist[j].getempProfile().getDateHired();
						if(dateHired1.compareTo(dateHired2) == 1) //order the employees by the dates they were hired in ascending order
						{
								tempEmployee = emplist[i];
								emplist[i] = emplist[j];
								emplist[j] = tempEmployee;
						}else if(dateHired1.compareTo(dateHired2) == 0) {
							String dept1 = emplist[i].getempProfile().getDept();
							String dept2 = emplist[j].getempProfile().getDept();
							if(dept1.compareTo(dept2) > 0) //if employees were hired on the same date, order them by department alphabetically
							{
									Employee tempEE = emplist[i];
									emplist[i] = emplist[j];
									emplist[j] = tempEE;
							}else if(dept1.compareTo(dept2) == 0) {
								String name1 = emplist[i].getempProfile().getName();
								String name2 = emplist[j].getempProfile().getName();
								if(name1.compareTo(name2) > 0) { //else if they were also hired by the same department, order them by name alphabetically
									Employee temp = emplist[i];
									emplist[i] = emplist[j];
									emplist[j] = temp;
								}
							}
						}
					}else if(emplist[i] == null && emplist[j] != null) {
						emplist[i] = emplist[j];
					}else if(emplist[i] != null && emplist[j] == null) {
						continue;
					}
				}
			}
			
			System.out.println("--Printing earning statements by date hired--");
			for(int j=0; j < emplist.length; j++) {
				if (emplist[j] != null) //print all the non-null elements of employee list
				{
					emplist[j].toString();
				}
			}
		}else {
			System.out.println("Employee database is empty.");
		}
	}
	
	/*
	 
	*/
	public void exportDatabase() {
		
	}

}

