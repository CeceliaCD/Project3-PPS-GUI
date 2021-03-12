package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

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
    
    private Profile inputtedEmpProfile(ActionEvent profileEvent) {
    	try {
    		String newEmployee = name.getText();
        	if(newEmployee == null) {
        		messageArea.appendText("Please enter employee name!\n");
        		return null;
        	}
        	
        	Profile prof = new Profile();
        	prof.setName(newEmployee);
        	
        	try {
        		RadioButton selectedDeptButton = (RadioButton) dept.getSelectedToggle();
        		String newDept = selectedDeptButton.getText(); 
        		prof.setDept(newDept);
        	}
        	catch(Exception e) {
            		messageArea.appendText("Please select a department!\n");
            		return null;
        	}
        	
        	if(dateHired.getValue() == null) {
        		messageArea.appendText("Please enter a date!\n");
        		return null;
        	}
        	
        	String[] temp = dateHired.getValue().toString().split("-");
        	String newDate = temp[1] + "/" + temp[2] + "/" + temp[0];
        	Date date = new Date(newDate);
        	
        	boolean dateValid = date.isValid();
        	if (dateValid == false) {
        		messageArea.appendText(date + "is not a valid date!\n");
        	}
        	prof.setDateHired(date);
        	
        	return prof;
    	}
    	catch(Exception e) {
    		messageArea.appendText("Invalid input!\n");
    		return null;
    	} 	
    }
    
    @FXML
    void addEmployee(ActionEvent event) {
    	try {
        	
        	RadioButton selectedType = (RadioButton) empType.getSelectedToggle();
    		String groupValue = selectedType.getText();
    		if(groupValue == null) {
    			messageArea.appendText("Please select an employment type.\n");
    		}
    		
    		if (groupValue.equals("Parttime")) {
    			Parttime parttimer;
    			try {
    				parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    				parttimer.setHourlyRate(Double.parseDouble(hourlyRate.getText()));
    			}
    			catch(Exception ex) {
    				messageArea.appendText("Invalid input!\n");
    				return;
    			}
    			
        		if(company.add(parttimer) == true) {
        			numEmp++;
        			messageArea.appendText("Employee added.\n");
        		}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    		
    		if (groupValue.equals("Fulltime")) {
    			Fulltime fulltimer; 
    			try {
    				fulltimer = new Fulltime(inputtedEmpProfile(event), 0, 0); 
    				fulltimer.setAnnualSalary(Double.parseDouble(annualSalary.getText()));
    			}
    			catch(Exception ex) {
    				return;
    			}
    		
        		if(company.add(fulltimer) == true) {
        			numEmp++;
        			messageArea.appendText("Employee added.\n");
        		}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    		
    		if (groupValue.equals("Management")) {
    			RadioButton selectedRole = (RadioButton) managerialRole.getSelectedToggle();
    			String role = selectedRole.getText();
    			Management manager;
    			try {
    				manager = new Management(inputtedEmpProfile(event), 0, 0, role); // right now the constructor takes an int for the role, should we change that?
    				manager.setAnnualSalary(Double.parseDouble(annualSalary.getText()));
    			}
    			catch(Exception ex) {
    				return;
    			}	
    			
    	    	if(company.add(manager) == true) {
    	    		numEmp++;
    	    		messageArea.appendText("Employee added.\n");
    	    	}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    	}
    	catch(Exception e) {
    		return;
    	}
    }

    @FXML
    void calculatePayment(ActionEvent event) {
    	if(numEmp == 0) {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    	company.processPayments();
    	messageAreaDB.appendText("Calculation of employee payments is done.\n");
    }

    @FXML
    void checkAnnualSalary(ActionEvent event) {
	double salary = Double.parseDouble(annualSalary.getText());
    	if (salary < 0) {
    		messageArea.appendText("Salary cannot be negative.\n");
    	}
    }

    @FXML
    void checkHourlyRate(ActionEvent event) {
	double rate = Double.parseDouble(hourlyRate.getText());
        if (rate < 0) {
            messageArea.appendText("Pay rate cannot be negative.\n");
        }
    }

    @FXML
    void checkHoursWorked(ActionEvent event) {
	// note for nida: make sure all fields are in for sethours before setting the hours (didn't have date but it still worked)
	int hours = Integer.parseInt(hoursWorked.getText());
    	if (hours > 100) {
    		messageArea.appendText("Invalid Hours: over 100.\n");
    	}
    	if (hours < 0) {
    		messageArea.appendText("Working hours cannot be negative.\n");
    	}
    }

    @FXML
    void clearFields(ActionEvent event) {
	name.clear();
    	annualSalary.clear();
    	hourlyRate.clear();
    	hoursWorked.clear();
    	messageArea.clear();
    	dateHired.getEditor().clear();
	dept.getSelectedToggle().setSelected(false);
    	empType.getSelectedToggle().setSelected(false);
    }

    @FXML
    void clearMessageArea(ActionEvent event) {
	// is this needed if we can just clear it in clearfields?
    }

    @FXML
    void exportFile(ActionEvent event) {

    }

    @FXML
    void fulltime(ActionEvent event) {
    	hourlyRate.setDisable(true);
		hoursWorked.setDisable(true);
		setHoursButton.setDisable(true);
		annualSalary.setDisable(false);
		managerialRole.getToggles().forEach(toggle 
				->{
					Node node = (Node) toggle;
					node.setDisable(true);
				});
    }

    @FXML
    void importFile(ActionEvent event) {

    }

    @FXML
    void management(ActionEvent event) {
    	hourlyRate.setDisable(true);
		hoursWorked.setDisable(true);
		setHoursButton.setDisable(true);
		annualSalary.setDisable(false);
		managerialRole.getToggles().forEach(toggle 
				->{
					Node node = (Node) toggle;
					node.setDisable(false);
				});
    }

    @FXML
    void parttime(ActionEvent event) {
    	annualSalary.setDisable(true);
		managerialRole.getToggles().forEach(toggle 
				->{
					Node node = (Node) toggle;
					node.setDisable(true);
				});
		hourlyRate.setDisable(false);
		hoursWorked.setDisable(false);
		setHoursButton.setDisable(false);
    }

    @FXML
    void printAllEmployees(ActionEvent event) {
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements for all employees--\n");
        	messageAreaDB.appendText(company.print()); // nida: did this bc i couldn't see the ouput, print() would have to return a string
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }

    @FXML
    void printByDate(ActionEvent event) {
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements by date hired--");
        	company.printByDate(); // messageAreaDB.appendText(company.printByDate());
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }

    @FXML
    void printByDept(ActionEvent event) {
    	if(numEmp > 0) {
    		messageAreaDB.appendText("--Printing earning statements by department--");
        	company.printByDepartment(); // messageAreaDB.appendText(company.printByDepartment());
    	}else {
    		messageAreaDB.appendText("Employee database is empty.\n");
    	}
    }

    @FXML
    void removeEmployee(ActionEvent event) {
    	//R Doe,Jane CS 7/1/2020 find the employee with the same profile and remove it
    	try {
    		Employee emp = new Employee(inputtedEmpProfile(event), 0);
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

    @FXML
    void setHours(ActionEvent event) {
    	Parttime parttimer;
    	try {
    		parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    		parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
    	}
    	catch(Exception ex) {
    		return;
    	}
    	
    	if (company.setHours(parttimer) == true) {
    		messageArea.appendText("Working hours set.\n");
    	} else {
    		messageArea.appendText("Employee does not exist.\n");
    	}
    }

}


