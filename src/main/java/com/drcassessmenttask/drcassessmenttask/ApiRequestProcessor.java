package com.drcassessmenttask.drcassessmenttask;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApiRequestProcessor {
    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://localhost:5432/Learning";
        String dbUser = "postgres";
        String dbPassword = "root";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            while (true) {
                processUnprocessedRequests(connection);
                sleepForSomeTime(5000); // Sleep for 5 seconds
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void processUnprocessedRequests(Connection connection) throws SQLException {
        String selectQuery = "SELECT id, request_data FROM api_request_queue WHERE processed = false";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int requestId = resultSet.getInt("id");
                String requestData = resultSet.getString("request_data");

                // Replace with your actual API call logic
                makeApiCall(requestData);

                markRequestAsProcessed(connection, requestId);
            }
        }
    }

    private static void makeApiCall(String requestData) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8085/api/students/all");
            httpPost.setEntity(new StringEntity(requestData));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                // Process API response if needed
                System.out.println("API call response: " + response.getStatusLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void markRequestAsProcessed(Connection connection, int requestId) throws SQLException {
        String updateQuery = "UPDATE api_request_queue SET processed = true WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();
        }
    }

    private static void sleepForSomeTime(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
