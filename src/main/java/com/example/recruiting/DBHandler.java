package com.example.recruiting;

import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DBHandler {
    Connection dbConnection;
    Statement statement;
    ResultSet resultSet;

    //Соединение с БД
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/recruiting";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "root");
        return dbConnection;
    }
    //Авторизация
    public ResultSet login(String login, String pass) {
        String request = "SELECT * FROM recruiting.authorization_data join recruiting.workers on auth_data = id_data  where login='"
                 + login + "' AND password = '" + pass + "'";
        try {
            statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(request);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // выполнение заготовленных в методах запросов
    public ResultSet querry(String querry) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(querry,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = preparedStatement.executeQuery(querry);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String getMilitaryId(String militaryName) {
        String id = "1";
        String request = "select * from military_units where military_name = '"+militaryName+"'";
        try {
            statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(request);
            resultSet.next();
            id = resultSet.getString("id_unit");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    public void updateQuerry(String querry) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(querry);
            preparedStatement.executeUpdate(querry);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Swap.allert("Данная операция завершена с ошибкой, попробуйте другие данные");
        }
    }

    public ResultSet loginUser(String login, String pass) {
        String request = "SELECT * FROM recruiting.authorization_data join recruiting.users on auth_data = id_data  where login='"
                + login + "' AND password = '" + pass + "'";
        try {
            statement = getDbConnection().createStatement();
            resultSet = statement.executeQuery(request);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
}
