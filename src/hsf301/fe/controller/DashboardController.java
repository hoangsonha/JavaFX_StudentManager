package hsf301.fe.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class DashboardController {

	@FXML Button btnStudent;
	@FXML Button btnReport;
	@FXML Button btn_Timetable;
	@FXML Button btnSettings;
	@FXML Button btnUpdate;
	@FXML Button btnClasses;

	@FXML public void goStudents() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/StudentManager.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}


}
