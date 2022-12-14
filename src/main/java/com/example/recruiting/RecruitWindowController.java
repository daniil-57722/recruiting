package com.example.recruiting;

import com.example.recruiting.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RecruitWindowController {
    @FXML
    private Label categoryLbl;

    @FXML
    private Label introLabel;

    @FXML
    private Label militaryUnitLbl;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveButton;

    User user;
    DBHandler dbHandler = new DBHandler();

    public void initData(User user){
        introLabel.setText(introLabel.getText()+" "+user.getMiddlename()+" "+user.getLastname());
        this.user = user;
        categoryLbl.setText(categoryLbl.getText()+" "+user.getCategory());
        militaryUnitLbl.setText(militaryUnitLbl.getText()+" ");
        nameField.setText(user.getFirstname()+" "+user.getMiddlename()+" "+user.getLastname());
    }

    @FXML
    private void initialize(){
        saveButton.setOnAction(e->{
            System.out.println(user.getUserId());
            String[] fullname = nameField.getText().trim().split(" ");
            dbHandler.updateQuerry("Update users set " +
                    "firstname='"+fullname[0]+"', " +
                    "middlename = '"+fullname[1]+"', " +
                    "lastname ='"+fullname[2]+"' where id_user = '"+user.getUserId()+"'" );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Процедура прошла успешно");
            alert.showAndWait();
        });
    }
}
