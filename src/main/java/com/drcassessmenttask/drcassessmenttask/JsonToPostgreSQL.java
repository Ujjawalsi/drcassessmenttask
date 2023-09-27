package com.drcassessmenttask.drcassessmenttask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JsonToPostgreSQL {
    public static void main(String[] args) {
        String jsonFilePath = "D:\\Json/bullseye_db.ms_thousandeye_alert.json";
        String jdbcUrl = "jdbc:postgresql://192.168.100.25:5432/bullseye";
        String username = "postgres";
        String password = "password";

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create a connection to the database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Read JSON file
            BufferedReader br = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            br.close();

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(jsonContent.toString());

            // Insert JSON data into PostgreSQL
            String insertQuery = "INSERT INTO ms_thousandeye_alert (_id, alert, event_id, event_type, new_event_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("alert"));
                String test = jsonObject.getString("alert");


                // Set values for each column in the prepared statement
                preparedStatement.setInt(1, (i));
                preparedStatement.setString(2, test);
                preparedStatement.setString(3, jsonObject.getString("eventId"));
                preparedStatement.setString(4, jsonObject.getString("eventType"));
                preparedStatement.setString(5, jsonObject.getString("newEventId"));

                // Set values for other columns similarly

                preparedStatement.executeUpdate();
            }

            // Close resources
            preparedStatement.close();
            connection.close();

            System.out.println("Data inserted successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
