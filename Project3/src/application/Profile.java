package application;

/**
The profile class defines and designs the personal
information of an employee such as getting and setting
up their name, department at which they work in, and 
when the company hired them.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Profile {

	private String name; //employees name in the form lastname,firstname
	private String department; //department code: CS, ECE, IT
	private Date dateHired;
	
	/**
	Getter method to obtain an employee's name.
	@return the string value of the employee's name
	*/
	public String getName() { 
		return name;
	}
	
	/**
	Getter method to obtain the department an employee works in.
	@return the string value of which department of the company the employee works in
	*/
	public String getDept() { 
		return department;
	}
	
	/**
	Getter method to obtain an employee's hire date.
	@return the Date object of the time the employee was hired
	*/
	public Date getDateHired() { 
		return dateHired;
	}
	
	/**
	Setter method to fill in the name of the employee in
	the profile object.
	@param empName of type string is the name to be given to employee's profile
	*/
	public void setName(String empName) {
		this.name = empName;
	}
	
	/**
	Setter method to fill in the department of the company
	that the employee works in for the profile object.
	@param dept of type string is the department to be given to employee's profile
	*/
	public void setDept(String dept) {
		this.department = dept;
	}
	
	/**
	Setter method to fill in the date when the 
	employee was hired.
	@param hired is the date to be given to employee's profile
	*/
	public void setDateHired(Date hired) {
		this.dateHired = hired;
	}
	
	/**
	Gives the specified profile an ouptut of name, department,
	and date hired for an employee that joined a company.
	@return string value of their profile information
	*/
	@Override
	public String toString() { 
		return getName() + "::" + getDept() + "::" + getDateHired().toString();
	}
	
	/**
	Compares another object to the current profile object checks 
	if the object is also the same profile.
	@param obj of type object that is to be compared to our profile object
	@return true if they are the same object, false otherwise
	*/
	@Override
	public boolean equals(Object obj) { //compare name, department and dateHired
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Profile)) {
			return false;
		}
		Profile objProfile = (Profile) obj;
		if (this.name.equals(objProfile.name) && 
				this.department.equals(objProfile.department)&& objProfile.dateHired.getYear()==this.dateHired.getYear()
	                    && objProfile.dateHired.getMonth()==this.dateHired.getMonth() && objProfile.dateHired.getDay()==this.dateHired.getDay()) {
			return true;
		}
		return false;
	} 
}

