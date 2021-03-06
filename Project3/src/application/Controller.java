package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker; // added this
import javafx.scene.control.RadioButton; // added this 
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
    private DatePicker datePicked; // added this

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
    
    Company company = new Company(); // keep this here?

    @FXML
    void addEmployee(ActionEvent event) {
    	try {
    		String newEmployee = name.getText();
            RadioButton selectedButton = (RadioButton) dept.getSelectedToggle();
    		String newDept = selectedButton.getText();
            // need to figure out how to get date
            Profile prof = new Profile();
    		prof.setName(newEmployee);
    		prof.setDept(newDept);
    		//prof.setDateHired(date);
            
            Parttime parttimer = new Parttime(prof, 0, 45, 0); // making a parttime emp just to check if it was working, will add the other types soon
    		
    		if (company.add(parttimer) == true) {
    			messageArea.setText("Employee added.");
    		}
    		else {
    			messageArea.setText("Employee is already on the list.");
    		}   
    	}
    	catch(NullPointerException e) {
    		throw e;
    	}
    }

    @FXML
    void checkAnnualSalary(ActionEvent event) {
    	try {
    		Double annSal = Double.parseDouble(annualSalary.getText());
    		if(annSal >= 0) {
    			
    		}
    	}
    	catch(NumberFormatException e) {
    		throw e;
    	}
    }

    @FXML
    void checkDateHired(ActionEvent event) {

    }

    @FXML
    void checkHourlyRate(ActionEvent event) {

    }

    @FXML
    void checkHoursWorked(ActionEvent event) {

    }

    @FXML
    void clearMessageArea(ActionEvent event) {

    }

    @FXML
    void removeEmployee(ActionEvent event) {

    }

    @FXML
    void setHours(ActionEvent event) {

    }

}
