package com.example.recruiting;

import com.example.recruiting.entities.Worker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private Button deleteBtn;

    @FXML
    private Button addWorkerBtn;

    @FXML
    private Label introLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<ObservableList> workersTable;

    DBHandler dbHandler = new DBHandler();
    Worker worker;

    public void initData(Worker worker){
        try {
            Swap.fill("Select id_worker as id, firstname as Фамилия, middlename as Имя, lastname as Отчество from workers WHERE role_id = '1'", workersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.worker=worker;
        introLabel.setText(introLabel.getText()+" "+worker.getMiddlename()+" "+worker.getLastname());
    }
    @FXML
    private void initialize(){
        searchBtn.setOnAction(e->{
            String[] fullname = nameField.getText().split(" ");
            try {
                Swap.fill("Select id_worker as id, firstname as Фамилия, middlename as Имя, lastname as Отчество " +
                        "from workers where firstname ='" +fullname[0]+"' AND middlename ='"+fullname[1]+ "' AND lastname ='"+fullname[2]+"' AND role_id=1", workersTable);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteBtn.setOnAction(e->{
            ObservableList selected = workersTable.getSelectionModel().getSelectedItem();
            Object workerid = String.valueOf(selected.get(0));
            workersTable.getItems().remove(selected);
            dbHandler.updateQuerry("DELETE FROM workers WHERE id_worker = '"+workerid+"'");
        });
        addWorkerBtn.setOnAction(e->{
            try {
                Swap.openNewWorkerWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
