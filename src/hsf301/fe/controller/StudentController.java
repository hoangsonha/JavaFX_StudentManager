package hsf301.fe.controller;

import java.beans.Transient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsf301.fe.pojo.Student;
import hsf301.fe.service.IstudentService;
import hsf301.fe.service.StudentService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;


public class StudentController implements Initializable {
	
	@FXML private TableView<Student> tbData;
	
	@FXML public TableColumn<Student, Integer> studentId;
	@FXML public TableColumn<Student, String> firstName;
	@FXML public TableColumn<Student, String> lastName;
	@FXML public TableColumn<Student, Integer> totalMark;
	
	@FXML public TextField txtFirstName;
	@FXML public TextField txtLastName;
	@FXML public TextField txtTotalMark;
	
	private int idStudent;
	
	private IstudentService istudentService;
	
	private ObservableList<Student> studentsModels;
	
	public StudentController() {
		
		istudentService = new StudentService("hibernate.cfg.xml");
		
		studentsModels = FXCollections.observableArrayList(istudentService.findALl());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		studentId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		totalMark.setCellValueFactory(new PropertyValueFactory<>("Mark"));
		tbData.setItems(studentsModels);
		
		tbData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observalueValue, Object oldValue, Object index) {
				if(tbData.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = tbData.getSelectionModel();
					ObservableList selectedcells = selectionModel.getSelectedCells();
					TablePosition tablePosition = (TablePosition) selectedcells.get(0);
					
					Object studentID = tablePosition.getTableColumn().getCellData(index);
					try {
						Student student = istudentService.findById(Integer.valueOf(studentID.toString()));
						showStudent(student);
					} catch(Exception e) {
						showAlert("Information Board !", "Please choose the First Cell !");
					}
				}
				
			}
		});
	}
	
	public void showAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public void showStudent(Student student) {
		this.setIdStudent(student.getId());
		this.txtFirstName.setText(student.getFirstName());
		this.txtLastName.setText(student.getLastName());
		this.txtTotalMark.setText(String.valueOf(student.getMark()));
	}
	
	private void refreshDataTable() {
		this.setIdStudent(0);
		this.txtFirstName.setText("");
		this.txtLastName.setText("");
		this.txtTotalMark.setText("");
		studentsModels = FXCollections.observableArrayList(istudentService.findALl());
		tbData.setItems(studentsModels);
	}
	
	@FXML
	public void addStudent() {
		if(!this.txtFirstName.getText().equals("") || !this.txtLastName.getText().equals("") || !this.txtTotalMark.getText().equals("")) {
			Student student = new Student(this.txtFirstName.getText(), this.txtLastName.getText(), Integer.parseInt(txtTotalMark.getText()));
			istudentService.save(student);
			refreshDataTable();
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Please input full field");
			alert.show();
		}
	}
	
	@FXML
	public void deleteStudent() {
		istudentService.delete(this.getIdStudent());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Delete successfully");
		alert.show();
		refreshDataTable();
	}
	
	@FXML
	public void updateStudent() {
		Student student = istudentService.findById(this.idStudent);
		if(student != null) {
			student.setBooks(student.getBooks());
			student.setFirstName(this.txtFirstName.getText());
			student.setLastName(this.txtLastName.getText());
			student.setMark(Integer.parseInt(txtTotalMark.getText()));
			istudentService.update(student);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Update successfully");
			alert.show();
		}
		refreshDataTable();
	}
	
	public int getIdStudent() {
		return idStudent;
	}
	
	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

}
