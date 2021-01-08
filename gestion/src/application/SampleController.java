package application;

import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SampleController implements Initializable {
	
	@FXML
	private Label message;
	
	@FXML
	private TextField inputID;
	
	@FXML
	private TextField inputFN;
	
	@FXML
	private TextField inputLN;
	
	@FXML
	private TextField inputSalary;
	
	@FXML
	private ToggleGroup gender;
	
	@FXML
	private RadioButton V;
	
	@FXML
	private RadioButton P;
	
	@FXML
	private RadioButton M;
	
	@FXML
	private RadioButton P1;
	
	@FXML
	private Button btnInsert;
	
	@FXML
	private Button btnUpdate;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private TableView<Employee> tvBox;
	
	@FXML
	private TableColumn<Employee, String> colFName;
	
	@FXML
	private TableColumn<Employee, String> colLName;
	
	@FXML
	private TableColumn<Employee, Double> colSalary;
	
	@FXML
	private TableColumn<Employee, String> colGender;
	
	private String radioSelected;
	
	private double vendeurChifreAffaire = 10000;
	private double representeurChifreAffaire = 20000;
	private int unitesProduitesMensuellemen = 10;
	private int nombreHeureTravailler = 100;
	
	private double empCalculatedSalary;
	
	@FXML
	private void handeleButtonAction(ActionEvent event) {
		if(event.getSource() == btnInsert) {
			insertData();
		}
		else if(event.getSource() == btnUpdate) {
			updateData();
		}
		else if(event.getSource() == btnDelete) {
			deleteData();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		showEmployee();
	}
	
	public Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javafx", "root", "");
			System.out.println("connected");
			return conn;
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public ObservableList<Employee> getEmployeeList() {
		ObservableList<Employee> employeeList = FXCollections.observableArrayList();
		Connection conn = getConnection();
		String query = "SELECT * FROM employee";
		Statement st;
		ResultSet rs;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			Employee employee;
			while(rs.next()) {
				employee = new Employee(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getDouble("salary"), rs.getString("gender"));
				employeeList.add(employee);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}

	public void showEmployee() {
		
		try {
			ObservableList<Employee> list = getEmployeeList();
			
			colFName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
			colLName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
			colSalary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
			colGender.setCellValueFactory(new PropertyValueFactory<Employee, String>("gender"));

			tvBox.setItems(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double calculerSalaire() {
		if(String.valueOf(P.isSelected()) == "true") {
			radioSelected = "productor";
		}
		else if(String.valueOf(V.isSelected()) == "true") {
			radioSelected = "vendeur";
		}
		else if(String.valueOf(P1.isSelected()) == "true") {
			radioSelected = "representeur";
		}
		else if(String.valueOf(M.isSelected()) == "true") {
			radioSelected = "mentionnaire";
		}
		
		if(radioSelected == "vendeur")
			return this.vendeurChifreAffaire*0.2 + 1500; //3500
		if(radioSelected == "representeur")
			return this.representeurChifreAffaire*0.2 + 2500; //6500
		if(radioSelected == "productor")
			return this.unitesProduitesMensuellemen*5 + 200; //250
		if(radioSelected == "Manutentionaire")
			return this.nombreHeureTravailler*50 + 200; //5200
		return 0;
	}
	
	private void insertData() {
		if(String.valueOf(P.isSelected()) == "true") {
			radioSelected = "productor";
		}
		else if(String.valueOf(V.isSelected()) == "true") {
			radioSelected = "vendeur";
		}
		else if(String.valueOf(P1.isSelected()) == "true") {
			radioSelected = "representeur";
		}
		else if(String.valueOf(M.isSelected()) == "true") {
			radioSelected = "mentionnaire";
		}
		
		empCalculatedSalary = Integer.parseInt(inputSalary.getText()) + calculerSalaire();
		
		String query = "INSERT INTO `employee`(`firstName`, `lastName`, `salary`, `gender`) VALUES ('"+inputFN.getText()+"','"+inputLN.getText()+"',"+empCalculatedSalary+",'"+radioSelected+"')";
		message.setText("inserted");
		
		executeQuery(query);
		showEmployee();
	}

	private void updateData() {
		if(String.valueOf(P.isSelected()) == "true") {
			radioSelected = "productor";
		}
		else if(String.valueOf(V.isSelected()) == "true") {
			radioSelected = "vendeur";
		}
		else if(String.valueOf(P1.isSelected()) == "true") {
			radioSelected = "representeur";
		}
		else if(String.valueOf(M.isSelected()) == "true") {
			radioSelected = "mentionnaire";
		}
		
		empCalculatedSalary = Integer.parseInt(inputSalary.getText()) + calculerSalaire();
		
		String query = "UPDATE `employee` SET `firstName`='"+inputFN.getText()+"',`lastName`='"+inputLN.getText()+"',`salary`="+empCalculatedSalary+",`gender`='"+radioSelected+"' WHERE id ="+inputID.getText()+"";
		message.setText("updated");
		
		executeQuery(query);
		showEmployee();
	}
	
	private void deleteData() {
		String query = "DELETE FROM `employee` WHERE id ="+inputID.getText()+"";
		message.setText("deleted");

		executeQuery(query);
		showEmployee();
	}

	private void executeQuery(String query) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handeleMousseAction(MouseEvent event) {
		Employee employee = tvBox.getSelectionModel().getSelectedItem();
		//System.out.println("id: " + employee.getId());
		//System.out.println("salary: " + employee.getSalary());
		
		inputID.setText(""+employee.getId());
		inputFN.setText(employee.getFirstName());
		inputLN.setText(employee.getLastName());
		inputSalary.setText(""+employee.getSalary());
	}
}
