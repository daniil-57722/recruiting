package com.example.recruiting;

import com.example.recruiting.entities.User;
import com.example.recruiting.entities.Worker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Swap {
    public static String MD5Cript(String pass){
        try {
            byte[] bytes = pass.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] MD5Digest = md.digest(bytes);
            BigInteger bigInt = new BigInteger(1, MD5Digest);
            String md5HexPass = bigInt.toString(16);
            return md5HexPass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void allert(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(s);
        alert.showAndWait();

    }

    public static void openWorkerWindow(Worker worker) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Recruiting.class.getResource("workerWindow.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        WorkerController workerController = loader.getController();
        workerController.initData(worker);
        stage.show();
    }
    public static void openNewRecruitWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(Recruiting.class.getResource("newRecruit.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    //Метод для заполнения таблиц результатами запроса
    public static void fill(String querry, TableView<ObservableList> table) throws SQLException {
        table.getColumns().clear();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.querry(querry);
        System.out.println(resultSet.getMetaData().getColumnCount());
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnLabel(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            table.getColumns().addAll(col);
        }
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }
        table.setItems(data);
    }

    public static void openAdminWindow(Worker worker) throws IOException {
        FXMLLoader loader = new FXMLLoader(Recruiting.class.getResource("adminWindow.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        AdminController adminController = loader.getController();
        adminController.initData(worker);
        stage.show();
    }

    public static void openUserWindow(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(Recruiting.class.getResource("recruitWindow.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        RecruitWindowController recruitWindowController = loader.getController();
        recruitWindowController.initData(user);
        stage.show();
    }

    public static void openNewWorkerWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(Recruiting.class.getResource("newWorkerWindow.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
