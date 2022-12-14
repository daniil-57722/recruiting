package com.example.recruiting;

import com.example.recruiting.entities.User;
import com.example.recruiting.entities.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationController {
    @FXML
    private Button enterBtn;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInBtn;

    @FXML
    private void initialize(){
        DBHandler dbHandler = new DBHandler();
        enterBtn.setOnAction(event ->{
            System.out.println(1);
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            //хеширование пароля MD5
            String hashpass = Swap.MD5Cript(password);
            //проверка наличия таких логина и хеша пароля в бд  создание объектов при успехе
            ResultSet result = dbHandler.login(login, password);
            ResultSet result2 = dbHandler.loginUser(login, password);
            int k = 0;
            try {
                if (result.next()) {
                    k=1;
                    System.out.println(1);
                    enterBtn.getScene().getWindow().hide();
                    //открытие соответствующих окон при успешной авторизации
                    if (result.getInt("role_id") == 1) {
                        Worker worker = new Worker(result.getInt("id_worker"), result.getString("firstname"),
                                result.getString("middlename"), result.getString("lastname"));
                        Swap.openWorkerWindow(worker);
                    } else if (result.getInt("role_id") == 3) {
                        Worker worker = new Worker(result.getInt("id_worker"), result.getString("firstname"),
                                result.getString("middlename"), result.getString("lastname"));
                        Swap.openAdminWindow(worker);
                    }
                }
                if(result2.next()){
                    k=1;
                    User user = new User(result2.getInt("id_user"), result2.getString("category"),
                            result2.getString("firstname"), result2.getString("middlename"),
                            result2.getString("lastname"), result2.getString("military_unit"));
                    Swap.openUserWindow(user);
                } if (k==0) Swap.allert("Ошибка входа: проверьте введенные логин и пароль");
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    }
