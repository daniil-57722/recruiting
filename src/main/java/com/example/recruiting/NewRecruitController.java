package com.example.recruiting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewRecruitController {

    @FXML
    private Button addBtn;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private TextField loginField;

    @FXML
    private ComboBox<String> militaryBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;

    @FXML
    private PasswordField passwordField;

    DBHandler dbHandler= new DBHandler();

    @FXML
    private void initialize() throws SQLException {
        ObservableList<String> militaryList = FXCollections.observableArrayList();
        ResultSet resultSet = dbHandler.querry("SELECT military_name FROM military_units");
        while (resultSet.next()){
            militaryList.add(resultSet.getString("military_name"));
        }
        militaryBox.setItems(militaryList);
        ObservableList<String> categoriesList = FXCollections.observableArrayList( "A1", "A2", "А3", "A4", "Б1", "Б2", "Б3", "Б4", "БB", "Г", "Д");
        categoryBox.setItems((ObservableList<String>) categoriesList);

        addBtn.setOnAction(event->{
            String[] fullname = nameField.getText().trim().split(" ");
            String[] passData = passField.getText().trim().split(" ");
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            String militaryUnit = dbHandler.getMilitaryId(militaryBox.getValue());
            String category = categoryBox.getValue();
            ResultSet res = dbHandler.querry("Select max(id_data) as id from authorization_data");
            int id;
            try {
                res.next();
                id = res.getInt("id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            id++;
            try {
                dbHandler.updateQuerry("Insert into authorization_data (login, password) values('" + login + "','" + password + "')");
                dbHandler.updateQuerry("Insert into users " +
                    "(firstname, middlename, lastname, category, pass_series, pass_number, military_unit, auth_data) " +
                    "values('"+fullname[0]+"', '"+fullname[1]+"', '"+fullname[2]+"', '"+category+"','"+passData[0]+"','"+passData[1]+"','"+militaryUnit+"','"+id+"')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Процедура прошла успешно");
                alert.showAndWait();
            }catch (Exception e){
                Swap.allert("Данные логин и пароль уже заняты");
            }
        });
    }
}
