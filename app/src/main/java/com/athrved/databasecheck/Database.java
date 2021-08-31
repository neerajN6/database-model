package com.athrved.databasecheck;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private Connection connection;

    private final String host = "ec2-54-159-35-35.compute-1.amazonaws.com";  // For Amazon Postgresql
    //private final String host = "35.44.16.169";  // For Google Cloud Postgresql
    private final String database = "d6u0g837rl4ifa";
    private final int port = 5432;
    private final String user = "lskuagydaitslz";
    private final String pass = "bce27733223391878af78bcacc1682f642ca82ce5b72cb36b2a8937a3a8a41b6";
    private String url = "jdbc:postgresql://lskuagydaitslz:bce27733223391878af78bcacc1682f642ca82ce5b72cb36b2a8937a3a8a41b6@ec2-54-159-35-35.compute-1.amazonaws.com:5432/d6u0g837rl4ifa";
    private boolean status;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getExtraConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}