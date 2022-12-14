package com.example.recruiting;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewWorkerController {
    @FXML
    private Button addBtn;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    DBHandler dbHandler = new DBHandler();

    @FXML
    private void initialize(){
        addBtn.setOnAction(event->{
            String login = loginField.getText();
            String password = passwordField.getText();
            String[] fullname = nameField.getText().trim().split(" ");
            try {
                dbHandler.updateQuerry("Insert into authorization_data (login, password) values('" + login + "','" + password + "')");
                ResultSet res = dbHandler.querry("Select max(id_data) as id from authorization_data");
                int id;
                try {
                    res.next();
                    id = res.getInt("id");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                dbHandler.updateQuerry("Insert into workers " +
                        "(firstname, middlename, lastname, role_id, auth_data) " +
                        "values('"+fullname[0]+"', '"+fullname[1]+"', '"+fullname[2]+"','1','"+id+"')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Процедура прошла успешно");
                alert.showAndWait();
            }catch (Exception e){
                Swap.allert("Данные логин и пароль уже заняты");
            }
        });
    }
}
