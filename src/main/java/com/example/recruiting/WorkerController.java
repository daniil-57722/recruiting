package com.example.recruiting;

import com.example.recruiting.entities.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerController {


    @FXML
    private Button newRecruitBtn;

    @FXML
    private Button applyBtn;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private ComboBox<String> militaryBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;

    @FXML
    private TableView<ObservableList> recruitsTable;

    @FXML
    private Button searchBtn;

    @FXML
    private Label introLabel;

    @FXML
    private Button searchBtn1;
    Worker worker;
    DBHandler dbHandler = new DBHandler();

    public void initData(Worker worker) throws SQLException {
        ObservableList<String> militaryList = FXCollections.observableArrayList();
        ResultSet resultSet = dbHandler.querry("SELECT military_name FROM military_units");
        while (resultSet.next()){
            militaryList.add(resultSet.getString("military_name"));
        }
        militaryBox.setItems(militaryList);
        ObservableList<String> categoriesList = FXCollections.observableArrayList( "A1", "A2", "А3", "A4", "Б1", "Б2", "Б3", "Б4", "БB", "Г", "Д");
        categoryBox.setItems((ObservableList<String>) categoriesList);
        this.worker=worker;
        introLabel.setText(introLabel.getText()+" "+worker.getMiddlename()+" "+worker.getLastname());
    }

    @FXML
    private void initialize(){
        searchBtn.setOnAction(event ->{
            String[] fullname = nameField.getText().split(" ");
            try {
                Swap.fill("Select firstname as Фамилия, middlename as Имя, lastname as Отчество, category as Категория, " +
                        "(SELECT military_name from military_units where id_unit = military_unit) as 'Род войск' " +
                        "from users where firstname ='" +fullname[0]+"' AND middlename ='"+fullname[1]+ "' AND lastname ='"+fullname[2]+"'", recruitsTable);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        searchBtn1.setOnAction(event ->{
            String[] passData = passField.getText().split(" ");
            try {
                Swap.fill("Select firstname as Фамилия, middlename as Имя, lastname as Отчество, category as Категория, " +
                        "(SELECT military_name from military_units where id_unit = military_unit) as 'Род войск' " +
                        "from users where pass_series ='" +passData[0]+"' AND pass_number ='"+passData[1]+ "'", recruitsTable);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
        recruitsTable.setOnMouseClicked(event ->{
            if (event.getClickCount()==2){
                ObservableList<String> selectedRecruit = recruitsTable.getSelectionModel().getSelectedItem();
                categoryBox.setValue(selectedRecruit.get(3));
                militaryBox.setValue(selectedRecruit.get(4));
                passField.setEditable(false);
                nameField.setEditable(false);
            }
        });
        applyBtn.setOnAction(event ->{
            String militaryId = dbHandler.getMilitaryId(militaryBox.getValue());
            dbHandler.updateQuerry("UPDATE users set category = '" + categoryBox.getValue()+"', military_unit='"+militaryId+"'");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Операция прошла успешно");
            alert.showAndWait();
        });
        newRecruitBtn.setOnAction(event->{
            try {
                Swap.openNewRecruitWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
