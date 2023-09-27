package com.drcassessmenttask.drcassessmenttask.controllers;

import com.mongodb.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.random.RandomGenerator;

@Component
@EnableScheduling
public class MongoDataToPostgreSQL {

    @Scheduled(cron =" 0 */5 * * * ?")
    public  void insertDate() {
        String mongoHost = "192.168.100.25";
        String mongoDatabaseName = "bullseye_db";
        String mongoCollectionName = "ms_thousandeye_alert";
        String jdbcUrl = "jdbc:postgresql://192.168.100.25:5432/bullseye";
        String dbUsername = "postgres";
        String dbPassword = "password";

        try {
            MongoClient mongoClient = new MongoClient(mongoHost, 27017);
            DB db = mongoClient.getDB(mongoDatabaseName);
            DBCollection mongoCollection = db.getCollection(mongoCollectionName);
            Connection postgresConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, -5);
            Date beforeDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            String firstdate = dateFormat.format(new Date());
            String seconddate = dateFormat.format(beforeDate);
            BasicDBObject query = new BasicDBObject();

            List<BasicDBObject> andQuery=new ArrayList<>();
            andQuery.add(new BasicDBObject("alert.dateStartZoned", new BasicDBObject("$gte", seconddate).append("$lte", firstdate)));
            query.put("$and", andQuery);
            DBCursor cursor = mongoCollection.find(query);
            while(cursor.hasNext())
            {
                DBObject dbobj=cursor.next();
                    try {
                        String sql =  "INSERT INTO ms_thousandeye_alert (_id, alert, event_id, event_type, new_event_id) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = postgresConnection.prepareStatement(sql);
                        int j = RandomGenerator.getDefault().nextInt();
                            preparedStatement.setString(3, dbobj.get("eventId").toString()); // Replace with actual field names
                            preparedStatement.setString(4, dbobj.get("eventType").toString()); // Replace with actual field names
                            preparedStatement.setString(5, dbobj.get("newEventId").toString());
                            preparedStatement.setString(2, dbobj.get("alert").toString());
                            preparedStatement.setInt(1, j);

                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        System.out.println("Data inserted successfully!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

