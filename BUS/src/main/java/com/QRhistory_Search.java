package com;

import java.sql.*;
import java.util.Scanner;

public class QRhistory_Search {
    public static void main(String[] argv) {
        /* Retrieve DB authentication information */
        DatabaseAuthInformation db_info = new DatabaseAuthInformation();
        db_info.parse_auth_info("auth/mysql.auth");

        /* Prepare the URL for DB connection */
        String db_connection_url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", db_info.getHost(),
                db_info.getPort(),
                db_info.getDatabase_name());


        //Insert Tuples to table
        // Prepare the query statement
        //String query_string = "insert into TEST_TB (, value) VALUES (?, ?)";
        String query_string = "1";
        // Preparation for db process
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             Statement db_statement = db_connection.createStatement()) {

            // Create Tables
            query_string = "CREATE TABLE `QRHistory` (" +
                    "`사업자번호`          VARCHAR(45)," +
                    "`QR정보`             VARCHAR(400)," +
                    "`시각`               DATETIME," +
                    "`기기`               VARCHAR(40)," +
                    "`발급기관`            VARCHAR(45)," +
                    "PRIMARY KEY(`QR정보`, `시각`))";
            int result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Insert tuples
        query_string = "INSERT INTO QRHistory (사업자번호, QR정보, 시각, 기기, 발급기관) VALUES (?, ?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)) {

            String[] StoreNum = {"130-19-94781", "130-19-94781"};
            String[] QRInfo = {"002|eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlMTgyOTQxNmQ3NzVjNDY4MzdiYjE3NTkyYjJlZTIyMyIsImlzcyI6IktUUEFTUyIsImV4cCI6MTYwNjUwNTkzMywidmVyc2lvbiI6IjAwMiJ9.gmuIIEe0u_ESX07U41sw0QA9xrjiWOA-4UFuB9D0cOM"
                    , "001|eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ2ZXJzaW9uIjoiMDAxIiwiZXhwIjoxNTkxNzcwMzg1LCJzdWIiOiI2ZGVkMThkNGVjYmE0MWNjYmRiN2I4MGY2M2UzNDZiZSIsImlzcyI6Ik5BVkVSIn0.TwzATgrdywgwr575Y8mwf5ub4hzN4L_IWKe583tTT9o"};
            String[] date = {"2020-04-03 16:30", "2020-04-03 16:31"};
            String[] deviceIMEI = {"861536030196001", "353321061651140"};
            String[] Org = {"Naver", "Pass"};


            for (int i = 0; i < StoreNum.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, StoreNum[i]);
                db_statement.setString(2, QRInfo[i]);
                db_statement.setString(3, date[i]);
                db_statement.setString(4, deviceIMEI[i]);
                db_statement.setString(5, Org[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Scanner scan = new Scanner(System.in);

        System.out.println("발급기관: ");
        String scanOrg = scan.nextLine();

        query_string = "select * from qrhistory where 발급기관 = ?";

        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)) {

            /* Set the query statement */
            db_statement.setString(1, scanOrg);

            ResultSet result_set = db_statement.executeQuery();

            while (result_set.next()) {
                System.out.println("#" + "사업자번호:" + result_set.getString(1) +
                        "\n 시각:" + result_set.getString(3) + "\n 기기: " +result_set.getString(4) +
                        " \n QR정보:" + result_set.getString(2) + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
