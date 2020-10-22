package com;

import java.sql.*;

public class SelectQueryExample {
    public static void main(String[] argv) {
        /* Retrieve DB authentication information */
        DatabaseAuthInformation db_info = new DatabaseAuthInformation();
        db_info.parse_auth_info("auth/mysql.auth");

        /* Prepare the URL for DB connection */
        String db_connection_url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", db_info.getHost(),
                db_info.getPort(),
                db_info.getDatabase_name());

        /* Prepare the query statement */
        String query_string = "select id, value from TEST_TB where id > 10";

        /* Preparation for db process */
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             Statement db_statement = db_connection.createStatement()){

            /* Send query and get the result */
            ResultSet result_set = db_statement.executeQuery(query_string);
            while(result_set.next()) {
                System.out.println("#" + result_set.getInt(1) + ": " + result_set.getFloat(2));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
