package com.drcassessmenttask.drcassessmenttask;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ExternalApiListener {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:pgsql://localhost:5432/Learning";
        String username = "postgres";
        String password = "root";

        PGDataSource dataSource = new PGDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        try (Connection connection = dataSource.getConnection()) {
            PGConnection pgConnection = connection.unwrap(PGConnection.class);

            PGNotificationListener listener = new PGNotificationListener() {
                @Override
                public void notification(int processId, String channelName, String payload) {
                    System.out.println(payload);
                    System.out.println(channelName);
                    if ("external_api".equals(channelName)) {
                        makeApiCall(payload);
                    }
                }
            };

            pgConnection.addNotificationListener(listener);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("LISTEN external_api");
            }

            System.out.println("Listening for notifications...");

            while (true) {
                Thread.sleep(1000); // Sleep to avoid busy-waiting
            }

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void makeApiCall(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("API Response: " + response.toString());
            } else {
                System.out.println("API Call failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

