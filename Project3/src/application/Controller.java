package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class Controller {

    @FXML
    private TextArea messageArea;

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
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeBotton;

    @FXML
    private ToggleGroup managerialRole;

    @FXML
    private Button clearButton;
    
    private Company company = new Company();
    
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
    		throw e;
    	} 	
    }

    @FXML
    void addEmployee(ActionEvent event) {
    	
    	try {
        	
        	RadioButton selectedType = (RadioButton) empType.getSelectedToggle().getUserData();
    		String groupValue = selectedType.getText();
    		if(groupValue.equals(null) && selectedType == null) {
    			messageArea.appendText("Please select an employment type.\n");
    		}
    		
    		if (groupValue.equals("Parttime")) {
    			Parttime parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    			try {
    				parttimer.setHourlyRate(Integer.parseInt(hourlyRate.getText()));
    			}
    			catch(NumberFormatException ex) {
    				throw ex;
    			}
    			
    			annualSalary.setDisable(true);
    			managerialRole.getToggles().forEach(toggle 
    					->{
    						Node node = (Node) toggle;
    						node.setDisable(true);
    					});
    		
        		if(company.add(parttimer) == true) {
        			messageArea.appendText("Employee added.\n");
        		}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    		
    		if (groupValue.equals("Fulltime")) {
    			Fulltime fulltimer = new Fulltime(inputtedEmpProfile(event), 0, 0); // get salary!!!
    			try {
    				fulltimer.setAnnualSalary(Integer.parseInt(annualSalary.getText()));
    			}
    			catch(NumberFormatException ex) {
    				throw ex;
    			}
    			hourlyRate.setDisable(true);
    			hoursWorked.setDisable(true);
    			setHoursButton.setDisable(true);
    			managerialRole.getToggles().forEach(toggle 
    					->{
    						Node node = (Node) toggle;
    						node.setDisable(true);
    					});
    		
        		if(company.add(fulltimer) == true) {
        			messageArea.appendText("Employee added.\n");
        		}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    		
    		// need to add management + check if everything is valid
    		if (groupValue.equals("Management")) {
    			RadioButton selectedRole = (RadioButton) managerialRole.getSelectedToggle();
    			String role = selectedRole.getText();
    			Management manager = new Management(inputtedEmpProfile(event), 0, 0, role); // right now the constructor takes an int for the role, should we change that?
    			try {
    				manager.setAnnualSalary(Integer.parseInt(annualSalary.getText()));
    			}
    			catch(NumberFormatException ex) {
    				throw ex;
    			}	
    			hourlyRate.setDisable(true);
    			hoursWorked.setDisable(true);
    			setHoursButton.setDisable(true);
    			
    	    	if(company.add(manager) == true) {
    	    		messageArea.appendText("Employee added.\n");
    	    	}else {
    	    		messageArea.appendText("Employee is already on the list.\n");
    	    	}
    		}
    	}
    	catch(Exception e) {
    		throw e;
    	}
    
    }

    @FXML
    void checkAnnualSalary(ActionEvent event) {

    }

    @FXML
    void checkHourlyRate(ActionEvent event) {

    }

    @FXML
    void checkHoursWorked(ActionEvent event) {

    }

    @FXML
    void clearFields(ActionEvent event) {

    }

    @FXML
    void removeEmployee(ActionEvent event) {

    }

    @FXML
    void setHours(ActionEvent event) {
 
    	//RadioButton selectedDeptButton = (RadioButton) dept.getSelectedToggle();
    	//String newDept = selectedDeptButton.getText();
    	Parttime parttimer = new Parttime(inputtedEmpProfile(event), 0, 0, 0);
    	
    	try {
    		parttimer.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
    	}
    	catch(NumberFormatException ex) {
    		throw ex;
    	}
    	
    	if (company.setHours(parttimer) == true) {
    		messageArea.appendText("Working hours set.");
    	} else {
    		messageArea.appendText("Employee does not exist.");
    	}
    	
    }

}

