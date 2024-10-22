package com.example.room_booking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Load the H2 driver (optional with newer versions)
            Class.forName("org.h2.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            if (connection != null) {
                System.out.println("Connection to H2 successful!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
