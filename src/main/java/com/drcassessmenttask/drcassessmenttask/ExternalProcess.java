package com.drcassessmenttask.drcassessmenttask;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExternalProcess {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:pgsql://localhost:5432/Learning";
        String user = "postgres";
        String password = "root";
        System.out.println("Kfdskmf");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PGConnection pgConnection = connection.unwrap(PGConnection.class);

            System.out.println("xd");

            PGNotificationListener listener = new PGNotificationListener() {
                @Override
                public void notification(int processId, String channelName, String payload) {
                    if ("your_notification_channel".equals(channelName) && "new_row_inserted".equals(payload)) {
                        // Handle the API call here
                        makeApiCall();
                        System.out.println("API call triggered");
                    }
                }
            };

            System.out.println("dfvfdvdfvdsvsdv");

            pgConnection.addNotificationListener(listener);
            Thread.sleep(Long.MAX_VALUE); // Keep the listener alive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void makeApiCall() {
        try {
            URL apiUrl = new URL("http://localhost:8085/api/students/all");
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set up the connection, headers, request method, etc.

            // Send the request
            int responseCode = connection.getResponseCode();

            // Handle the response, error handling, etc.

            System.out.println(responseCode);

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


