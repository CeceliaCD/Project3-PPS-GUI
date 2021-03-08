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
    		RadioButton selectedDeptButton = (RadioButton) dept.getSelectedToggle();
    		String newDept = selectedDeptButton.getText(); 
    		//Date date = new Date("2/25/2012");//new Date((String) datePicked); // can't do dateHired yet bc 
    		Date date = new Date(datePicked.getValue().toString());
    		
    		//messageArea.setText(newDept);
    		Profile prof = new Profile();
    		prof.setName(newEmployee);
    		prof.setDept(newDept);
    		prof.setDateHired(date);
    		
    		RadioButton selectedEmpType = (RadioButton) empType.getSelectedToggle();
    		String newEmpType = selectedEmpType.getText();
    		
    		if (newEmpType.equals("Parttime")) {
    			//int wage = Integer.parseInt(hourlyRate.getText());
    			Parttime parttimer = new Parttime(prof, 0, 0, 0); // get hourly rate!!!
    			parttimer.setHourlyRate(Integer.parseInt(hourlyRate.getText())); // causes an error if left blank
    			if (company.add(parttimer) == true) {
    				messageArea.setText("Employee added." + parttimer.toString()); // remove toString(), using it rn just to check
    			}
    			else {
    				messageArea.setText("Employee is already on the list.");
    			}
    		}
    		
    		if (newEmpType.equals("Fulltime")) {
    			Fulltime fulltimer = new Fulltime(prof, 0, 0); // get salary!!!
    			fulltimer.setAnnualSalary(Integer.parseInt(annualSalary.getText()));
    			if (company.add(fulltimer) == true) {
    				messageArea.setText("Employee added." + fulltimer.toString()); // remove toString(), using it rn just to check
    			}
    			else {
    				messageArea.setText("Employee is already on the list.");
    			}
    		}
    		// need to add management + check if everything is valid
   
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
        try { // doesn't work, need to fix
    		if (Integer.parseInt(hourlyRate.getText()) < 0) {
    			messageArea.setText("Pay rate cannot be negative.");
    		}
    	}
    	catch(NumberFormatException e) {
    		throw e;
    	}
    }

    @FXML
    void checkHoursWorked(ActionEvent event) {

    }

    @FXML
    void clearMessageArea(ActionEvent event) {

    }

    @FXML
    void removeEmployee(ActionEvent event) {
        // need to add something for empty employee database
    	String newEmployee = name.getText();
		RadioButton selectedDeptButton = (RadioButton) dept.getSelectedToggle();
		String newDept = selectedDeptButton.getText(); 
		Date date = new Date(datePicked.getValue().toString()); // figure out date
		
    	Profile prof = new Profile();
		prof.setName(newEmployee);
		prof.setDept(newDept);
		prof.setDateHired(date);
    }

    @FXML
    void setHours(ActionEvent event) {

    }

}
