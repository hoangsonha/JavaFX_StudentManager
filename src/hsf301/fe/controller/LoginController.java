package hsf301.fe.controller;


import java.io.IOException;

import hsf301.fe.service.AccountService;
import hsf301.fe.service.IAccountService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	
	private IAccountService iAccountService = null;
	
	public LoginController() {
		if(iAccountService == null) {
			iAccountService = new AccountService("hibernate.cfg.xml");
		}
	}
	
	@FXML public void login() throws IOException {
			
		boolean check = iAccountService.checkLogin(txtUserName.getText(), txtPassword.getText());
		if(check) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Your email or password is not correct!!!");
			alert.show();
		}	
	}
	
	@FXML public void cancel() {
		Platform.exit();
	}
	
	
}
