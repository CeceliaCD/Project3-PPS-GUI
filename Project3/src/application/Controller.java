package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
The controller class is responsible for the changes that happen in the
graphical user interface. It takes in the information that is used to
execute the methods in the company class, and outputs necessary results 
and messages.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Controller {
	
    @FXML
    private TextArea messageArea;
    
    @FXML
    private TextArea messageAreaDB;

    @FXML
    private TextField name;

    @FXML
    private ToggleGroup dept;

    @FXML
    private DatePicker dateHired;

    @FXML
    private TextField annualSalary;

    @FXML
    private TextField hourlyRate;

    @FXML
    private ToggleGroup empType;

    @FXML
    private TextField hoursWorked;

    @FXML
    private Button setHoursButton;

    @FXML
    private ToggleGroup managerialRole;
    
    private Company company = new Company();
    
    private int numEmp = 0;
    
    /**
    This method creates a profile object which will be returned so an employee
    of any type can be made.
	@param profileEvent takes in the necessary information to create a profile.
    @return the profile to be used when creating an employee.
    */
    private Profile inputtedEmpProfile(ActionEvent profileEvent) {
    	String newEmployee = name.getText();
        if(newEmployee == null) {
        	messageArea.appendText("Please enter employee name!\n");
        	return null;
        }
        for(int i = 0; i < newEmployee.length(); i++) {
        	char ch = newEmployee.charAt(i);
        	if(!Character.isLetter(ch) && ch != ' ') {
        		messageArea.appendText("Please enter valid input.\n");
            	return null;
        	}
        }
        	
        Profile prof = new Profile();
        prof.setName(newEmployee);
        	
      
        RadioButton selectedDeptButton = (RadioButton) dept.getSelectedToggle();
        String newDept = "";
        if(selectedDeptButton != null) {
        	newDept = selectedDeptButton.getText(); 
        }else {
        	messageArea.appendText("Please select a department!\n");
            return null;
        }
        prof.setDept(newDept);
       
        	
        if(dateHired.getValue() == null) {
        	messageArea.appendText("Please enter a date!\n");
        	return null;
        }
        	
        String[] temp = dateHired.getValue().toString().split("-");
        String newDate = temp[1] + "/" + temp[2] + "/" + temp[0];
        Date date = new Date(newDate);
        	
        boolean dateValid = date.isValid();
        if (dateValid == false) {
        	messageArea.appendText(date + " is not a valid date!\n");
        	return null;
        }
        prof.setDateHired(date);
        	
        return prof;	
    }
    
    /**
    A helper method for importFile to help scan in the information to the 
    text area.
    @param employeeInfo contains the information of an employee.
    */
    private void importedEmployees(String[] employeeInfo) {
    	String employeeType = employeeInfo[0];
    	String name = employeeInfo[1];
    	String deptName = employeeInfo[2];
    	String hireDate = employeeInfo[3];
    	String empSalary = employeeInfo[4];
    	int longestEmpStrLen = 6;
    	int manager = 1;
    	int deptHead = 2;
    	int director = 3;
    	
    	int role = 0;
    	if(employeeInfo.length == longestEmpStrLen) {
    		role = Integer.parseInt(employeeInfo[5]);
    	}
    	
    	String roleConverted = "";
    	if(role == manager) {
    		roleConverted = "Manager";
    	}else if(role == deptHead) {
    		roleConverted = "Department Head";
    	}else if(role == director) {
    		roleConverted = "Director";	
    	}
    	
    	Profile prof = new Profile();
    	prof.setName(name);
    	prof.setDept(deptName);
    	Date hired = new Date(hireDate);
    	prof.setDateHired(hired);
    	
    	if(employeeType.equals("P")) {
    		Parttime parttimer = new Parttime(prof, 0, Double.parseDouble(empSalary), 0);
    		company.add(parttimer);
    	}
    	else if(employeeType.equals("F")) {
    		Fulltime fulltimer = new Fulltime(prof, 0, Double.parseDouble(empSalary));
    		company.add(fulltimer);
    		
    	}
    	else if(employeeType.equals("M")) {
    		Management managing = new Management(prof, 0, Double.parseDouble(empSalary), roleConverted);
    		company.add(managing);
    	}
    	return;
    }
    
    /**
    Method to obtain the information needed for the add method in the company class,
    and output any necessary messages.
    @param event is the Add Employees button in View.fxml
    */
    @FXML
    void addEmployee(ActionEvent event) {
    	messageArea.clear();
    	
        RadioButton selectedType = (RadioButton) empType.getSelectedToggle();
        String groupValue = "";
    	if(selectedType != null) {
        	groupValue = selectedType.getText();
    	}else {
    		messageArea.appendText("Please enter valid input.\n");
    		return;
    	}
    		
    	if (groupValue.equals("Parttime")) {
    		Parttime parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    		if(parttimer.getempProfile() == null) {
    			return;
    		}
    		try {
    			parttimer.setHourlyRate(Double.parseDouble(hourlyRate.getText()));
    		}
    		catch(Exception ex) {
    			return;
    		}
    		finally {	
    			if (checkHourlyRate(hourlyRate) == true) {
					if (company.add(parttimer) == true) { // ;
						numEmp++;
						messageArea.appendText("Employee added.\n");
					} else {
						messageArea.appendText("Employee is already on the list.\n");
					}
				}	
    		}
    	}
    		
    	if (groupValue.equals("Fulltime")) {
    		Fulltime fulltimer = new Fulltime(inputtedEmpProfile(event), 0, 0);
    		if(fulltimer.getempProfile() == null) {
    			return;
    		}
    		try { 
    			fulltimer.setAnnualSalary(Double.parseDouble(annualSalary.getText()));
    		}
    		catch(Exception ex) {
    			return;
    		}
    		finally {
    			if (checkAnnualSalary(annualSalary) == true) {
					if (company.add(fulltimer)) { // ;
						numEmp++;
						messageArea.appendText("Employee added.\n");
					} else {
						messageArea.appendText("Employee is already on the list.\n");
					}
    			}
    		}
    	}
    		
    	if (groupValue.equals("Management")) {
    		RadioButton selectedRole = (RadioButton) managerialRole.getSelectedToggle();
    		String role = selectedRole.getText();
    		Management manager = new Management(inputtedEmpProfile(event), 0, 0, role); // changed roles to type String in Management
    		if(manager.getempProfile() == null) {
    			return;
    		}
    		try {
    			manager.setAnnualSalary(Double.parseDouble(annualSalary.getText()));
    		}
    		catch(Exception ex) {
    			return;
    		}
    		finally {
				if (checkAnnualSalary(annualSalary) == true) {
					if (company.add(manager) == true) { // ;
						numEmp++;
						messageArea.appendText("Employee added.\n");
					} else {
					messageArea.appendText("Employee is already on the list.\n");
					}
				}
    		}
    	}
    }

    /**
    This method calculates the payment for all employees in the database.
    @param event is the Calculate Payment button in View.fxml
    */
    @FXML
    void calculatePayment(ActionEvent event) {
    	messageAreaDB.clear();
    	
    	if(numEmp == 0) {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}else {
    		company.processPayments();
        	messageAreaDB.appendText("Calculation of employee payments is done.\n");
    	}
    }

    /**
	Method to check if the annual salary of a fulltime or manager employee is valid.
	@param annualSalary contains a fulltime or manager employee's annual salary
    @return boolean that signals if the salary was valid or not
    */
    private boolean checkAnnualSalary(TextField annualSalary) {
    	try {
			double salary = Double.parseDouble(annualSalary.getText());
			if (salary < 0) {
				messageArea.appendText("Salary cannot be negative.\n");
				return false;
			}
		} catch (Exception e) {
			messageArea.appendText("Please enter valid input.\n");
			return false;
		}
		return true;
    }
    
    /**
	Method to check if the hourly rate of a part time employee is valid.
	@param hourlyRate contains the rate of a part time employee in textfield format.
    @return boolean that signals if the hourly rate is valid or not.
    */
    private boolean checkHourlyRate(TextField hourlyRate) {	
    	try {	
    		double rate = Double.parseDouble(hourlyRate.getText());
    		if (rate < 0) {
			messageArea.appendText("Pay rate cannot be negative.\n");
			return false;
			}
		} catch (Exception ex) {
			messageArea.appendText("Please enter valid input.\n");
			return false;
		}
		return true;
    }
    
    /**
	Clears out every input and output area and enables all disabled fields again.
	@param event is the Clear button in the graphical user interface.
    */
    @FXML
    void clearFields(ActionEvent event) {
    	name.clear();
    	annualSalary.clear();
    	hourlyRate.clear();
    	hoursWorked.clear();
    	messageArea.clear();
    	dateHired.getEditor().clear();
    	dept.getToggles().forEach(toggle
    			->{
    				RadioButton temp = (RadioButton) toggle;
    				temp.setSelected(false);
    			});
    	empType.getToggles().forEach(toggle
    			->{
    				RadioButton temp = (RadioButton) toggle;
    				temp.setSelected(false);
    			});
    	managerialRole.getToggles().forEach(toggle 
				->{
					RadioButton temp = (RadioButton) toggle;
    				temp.setSelected(false);
				});
    	hourlyRate.setDisable(false);
		hoursWorked.setDisable(false);
		setHoursButton.setDisable(false);
		annualSalary.setDisable(false);
    }

    /**
	Clears the textarea for outputs in the Employee Database tab.
	@param event is the Clear button in the Employee Database tab.
    */
    @FXML
    void clearMessageArea(ActionEvent event) {
    	messageAreaDB.clear(); // this is for the other clear button on the Database tab, it clears that message area
    }

    /**
	Executes the necessary functions needed to export the file, and outputs any 
	relevant messages related to the status of the export.
	@throws FileNotFoundException if file is not found
	@param event is the Export button in the graphical user interface.
    */
    @FXML
    void exportFile(ActionEvent event) throws FileNotFoundException {
    	messageAreaDB.clear();
    	
    	FileChooser ch = new FileChooser();
    	ch.setTitle("Open Target File for the Export");
    	ch.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
    	Stage stage = new Stage();
    	File sourceFile = ch.showSaveDialog(stage);
    	
    	String errorMessage1 = "File is not empty!\n";
    	String errorMessage2 = "Export failed. Invalid file selection.\n";
    	String errorMessage3 = "File Not Found.\n";
    	String exported = "Payroll processing exported!\n";
    	
    	if(company.exportDatabase(sourceFile).equals(errorMessage1)) {
    		messageAreaDB.appendText(errorMessage1);
    	}
    	else if(company.exportDatabase(sourceFile).equals(errorMessage2)) {
    		messageAreaDB.appendText(errorMessage2);
    	}
    	else if(company.exportDatabase(sourceFile).equals(errorMessage3)) {
    		messageAreaDB.appendText(errorMessage3);
    	}
    	else if(company.exportDatabase(sourceFile).equals(exported)) {
    		messageAreaDB.appendText(exported);
    	}
    	return;
    	
    }

    /**
	This disables the fields that are not required for a full time employee, such
	as hourly rate, hours worked, set hours, and managerial role.
	@param event is the full time button in the graphical user interface.
    */
    @FXML
    void fulltime(ActionEvent event) {
    	hourlyRate.setDisable(true);
		hoursWorked.setDisable(true);
		setHoursButton.setDisable(true);
		annualSalary.setDisable(false);
		managerialRole.getToggles().forEach(toggle 
				->{
					RadioButton temp = (RadioButton) toggle;
    				temp.setDisable(true);
				});
    }

    /**
	Executes the necessary functions needed to import the file, and outputs any 
	relevant messages related to the status of the import.
	@throws FileNotFoundException if file is not found
	@param event is the Import button in the graphical user interface.
    */
    @FXML
    void importFile(ActionEvent event) throws FileNotFoundException {
    	messageAreaDB.clear();
    	
    	FileChooser ch = new FileChooser();
    	ch.setTitle("Open Target File for the Import");
    	ch.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
    	Stage stage = new Stage();
    	File sourceFile = ch.showSaveDialog(stage);
    	
    	try {
    		Scanner scan = new Scanner(sourceFile);
    		
    		if(!scan.hasNext()) {
    			messageAreaDB.appendText("File is empty!\n");
    			scan.close();
    			return;
    		}
    		while(scan.hasNext()) {
    			String commandLine = scan.nextLine();
    			String[] empInfo = commandLine.split(",");
    			importedEmployees(empInfo);
    		}
    		messageAreaDB.appendText("Databse imported!\n");
    		scan.close();
    		return;
    	}
    	catch(FileNotFoundException e) {
    		messageAreaDB.appendText("File Not Found.\n");
    		return;
    	}
    	catch(Exception ex) {
    		messageAreaDB.appendText("Import failed. Invalid file selection.");
    		return;
    	}
    	
    	
    }

    /**
	This disables the fields that are not required for a management employee, such
	as hourly rate, hours worked, and set hours.
	@param event is the management button in the graphical user interface.
    */
    @FXML
    void management(ActionEvent event) {
    	hourlyRate.setDisable(true);
		hoursWorked.setDisable(true);
		setHoursButton.setDisable(true);
		annualSalary.setDisable(false);
		managerialRole.getToggles().forEach(toggle 
				->{
					RadioButton temp = (RadioButton) toggle;
    				temp.setDisable(false);
				});
    }

    /**
	This disables the fields that are not required for a part time employee, such
	as annual salary and managerial role.
	@param event is the parttime button in the grpahical user interface.
    */
    @FXML
    void parttime(ActionEvent event) {
    	annualSalary.setDisable(true);
		managerialRole.getToggles().forEach(toggle 
				->{
					RadioButton temp = (RadioButton) toggle;
    				temp.setDisable(true);
				});
		hourlyRate.setDisable(false);
		hoursWorked.setDisable(false);
		setHoursButton.setDisable(false);
    }

    /**
	Outputs a list of all the employees in the database and their relevant information.
	@param event is the Print All Employees selection from the print drop down menu.
    */
    @FXML
    void printAllEmployees(ActionEvent event) {
    	messageAreaDB.clear();
    	
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements for all employees--\n");
        	messageAreaDB.appendText(company.print()); // nida: did this bc i couldn't see the ouput, print() would have to return a string
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }

    /**
	Outputs a list of all the employees in the database and their relevant information
	sorted by date.
	@param event is the Print By Date selection from the print drop down menu.
    */
    @FXML
    void printByDate(ActionEvent event) {
    	messageAreaDB.clear();
    	
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements by date hired--\n");
        	messageAreaDB.appendText(company.printByDate()); 
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }
    
    /**
	Outputs a list of all the employees in the database and their relevant information
	sorted by department.
	@param event is the Print By Date selection from the print drop down menu.
    */
    @FXML
    void printByDept(ActionEvent event) {
    	messageAreaDB.clear();
    	
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements by department--\n");
    		messageAreaDB.appendText(company.printByDepartment());
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }

    /**
	Obtains the necessary information for the remove method in company to execute
	successfully, and then output any relevant messages.
	@param event is the Remove Employee button in the graphical user interface.
    */
    @FXML
    void removeEmployee(ActionEvent event) {
    	messageArea.clear();
    	//R Doe,Jane CS 7/1/2020 find the employee with the same profile and remove it
    	try {
    		Employee emp = new Employee(inputtedEmpProfile(event), 0);
    		if(emp.getempProfile() == null) {
    			messageArea.clear();
    			messageArea.appendText("Please enter valid input.\n");
    			return;
    		}
    		if (company.remove(emp) == true) {
    			messageArea.appendText("Employee removed.\n");
    		} else {
    			messageArea.appendText("Employee does not exist.\n");
    		}	    		
    	}
    	catch(Exception e) {
    		return;
    	}
    }

    /**
	Obtains the necessary information for the setHours method in company to execute
	successfully, and then output any relevant messages.
	@param event is the Set Hours button in the graphical user interface.
    */
    @FXML
    void setHours(ActionEvent event) {
    	messageArea.clear();
    	
    	Parttime parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    	if(parttimer.getempProfile() == null) {
    		messageArea.clear();
			messageArea.appendText("Please enter valid input.\n");
			return;
		}
    	
    	try {
    		if(hoursWorked.getText() != null) {
    			parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
    		}else {
    			messageArea.appendText("Please enter hours worked.\n");
    			return;
    		}
    	}
    	catch(Exception ex) {
    		messageArea.appendText("Please enter valid input.\n");
    		return;
    	}
    	finally {
    		if(hoursWorked.getText() != null) {
    			parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
    		}else {
    			messageArea.appendText("Please enter hours worked.\n");
    			return;
    		}
			messageArea.clear();
    		if(Integer.parseInt(hoursWorked.getText()) < 0) {
    			if(hoursWorked.getText() != null) {
        			parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
        		}else {
        			messageArea.appendText("Please enter hours worked.\n");
        			return;
        		}
    			messageArea.clear();
    			messageArea.appendText("Working hours cannot be negative.\n");
    		}else if(Integer.parseInt(hoursWorked.getText()) > 100) {
    			if(hoursWorked.getText() != null) {
        			parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
        		}else {
        			messageArea.appendText("Please enter hours worked.\n");
        			return;
        		}
    			messageArea.clear();
    			messageArea.appendText("Invalid Hours: over 100.\n");
    		}else if (company.setHours(parttimer) == true) {
        		messageArea.appendText("Working hours set.\n");
        	} else {
        		messageArea.appendText("Employee does not exist.\n");
        	}
    	}
    }

}
