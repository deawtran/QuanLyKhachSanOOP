package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/khachsan";
    private static final String USER = "root";
    private static final String PASS = "Deawtran.206";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Ket noi thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ket noi that bai!");
        }
        return conn;
    }
}

