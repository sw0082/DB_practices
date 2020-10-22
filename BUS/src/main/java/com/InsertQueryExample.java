package com;

import java.sql.*;

public class InsertQueryExample {
    public static void main(String[] argv) {
        /* Retrieve DB authentication information */
        DatabaseAuthInformation db_info = new DatabaseAuthInformation();
        db_info.parse_auth_info("auth/mysql.auth");

        /* Prepare the URL for DB connection */
        String db_connection_url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", db_info.getHost(),
                db_info.getPort(),
                db_info.getDatabase_name());

        /* Prepare the query statement */
        String query_string = "insert into TEST_TB (id, value) VALUES (?, ?)";

        /* Preparation for db process */
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            /* Set the query statement */
            db_statement.setInt(1, 20);
            db_statement.setFloat(2, 12.3f);

            /* Send query and get the result */
            int result = db_statement.executeUpdate();
            System.out.println("Updated query: " + result);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
