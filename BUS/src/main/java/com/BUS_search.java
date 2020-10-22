package com;

import java.sql.*;

public class BUS_search {
    public static void main(String[] argv) {
        /* Retrieve DB authentication information */
        DatabaseAuthInformation db_info = new DatabaseAuthInformation();
        db_info.parse_auth_info("auth/mysql.auth");

        /* Prepare the URL for DB connection */
        String db_connection_url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", db_info.getHost(),
                db_info.getPort(),
                db_info.getDatabase_name());

        //Insert Tuples to table
        //
        //
        //


        // Prepare the query statement
        //String query_string = "insert into TEST_TB (, value) VALUES (?, ?)";

        String query_string = "1";
        // Preparation for db process
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             Statement db_statement = db_connection.createStatement()){

            // Create Tables
            /*query_string = "CREATE TABLE `dbp_lecture_db`.`버스` (" +
                    "`차량번호`         INT," +
                    "`년식`            INT," +
                    "`유지보수예정일`    DATE," +
                    "`유형`            VARCHAR(45)," +
                    "PRIMARY KEY(`차량번호`))";
            int result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`버스유형` (" +
                    "`유형`             VARCHAR(45)," +
                    "`최대탑승인원`      INT," +
                    "PRIMARY KEY(`유형`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`직원` (" +
                    "`직원번호`         INT," +
                    "`이름`            VARCHAR(45)," +
                    "`주소`            VARCHAR(45)," +
                    "`전화번호`         VARCHAR(45)," +
                    "`급여`            INT," +
                    "`업무`            VARCHAR(45)," +
                    "PRIMARY KEY(`직원번호`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`운전기사` (" +
                    "`직원번호`         INT," +
                    "`교통위반티켓수`    INT," +
                    "`운전면허종료일`    DATE," +
                    "`총운행거리`       INT," +
                    "PRIMARY KEY(`직원번호`)," +
                    "FOREIGN KEY(`직원번호`) REFERENCES 직원(`직원번호`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`정류장` (" +
                    "`정류장명`         VARCHAR(45)," +
                    "`주소`            VARCHAR(45)," +
                    "PRIMARY KEY(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`경유` (" +
                    "`경유코드`         VARCHAR(45)," +
                    "`정류장명`         VARCHAR(45)," +
                    "`도착시간`         FLOAT," +
                    "`출발시간`         FLOAT," +
                    "PRIMARY KEY(`경유코드`, `도착시간`)," +
                    "FOREIGN KEY(`정류장명`) REFERENCES 정류장(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`노선` (" +
                    "`노선명`           VARCHAR(45)," +
                    "`출발정류장`        VARCHAR(45)," +
                    "`도착정류장`        VARCHAR(45)," +
                    "PRIMARY KEY(`노선명`)," +
                    "FOREIGN KEY(`출발정류장`) REFERENCES 정류장(`정류장명`)," +
                    "FOREIGN KEY(`도착정류장`) REFERENCES 정류장(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`운행스케줄` (" +
                    "`노선명`          VARCHAR(45)," +
                    "`요일`            VARCHAR(45)," +
                    "`출발시간`         FLOAT," +
                    "`도착시간`         FLOAT," +
                    "`경유코드`         VARCHAR(45)," +
                    "PRIMARY KEY(`노선명`, `요일`)," +
                    "FOREIGN KEY(`노선명`) REFERENCES 노선(`노선명`)," +
                    "FOREIGN KEY(`경유코드`) REFERENCES 경유(`경유코드`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `dbp_lecture_db`.`운행이력` (" +
                    "`노선명`         VARCHAR(45)," +
                    "`요일`           VARCHAR(45)," +
                    "`년도`           INT," +
                    "`주`             INT," +
                    "`기사`           INT," +
                    "`운행버스`        INT," +
                    "PRIMARY KEY(`노선명`, `요일`, `년도`, `주`, `기사`, `운행버스`)," +
                    "FOREIGN KEY(`노선명`) REFERENCES 노선(`노선명`)," +
                    "FOREIGN KEY(`기사`) REFERENCES 운전기사(`직원번호`)," +
                    "FOREIGN KEY(`운행버스`) REFERENCES 버스(`차량번호`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);*/

        }catch (SQLException e) {
            e.printStackTrace();
        }


        //Insert tuples
        query_string = "INSERT INTO 버스유형 (유형, 최대탑승인원) VALUES (?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] bussort = {"마을버스A", "마을버스B", "마을버스A개조", "버스A", "버스B", "저상A", "저상B", "저상C", "리무진A", "리무진B", "2층버스"};
            int[] max = {15, 15, 17, 40, 40, 35, 35, 40, 45, 45, 60};

            for(int i = 0; i < bussort.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, bussort[i]);
                db_statement.setInt(2, max[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 버스 (차량번호, 년식, 유지보수예정일, 유형) VALUES (?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            int[] num = {1234, 2342, 2353, 7231, 8731, 4461, 1513, 1845, 1548, 1735, 7621, 7627, 7616};
            int[] year = {2017, 2017, 2017, 2016, 2016, 2015, 2015, 2020, 2020, 2019, 2018, 2014, 2002};
            String[] date = {"2022-04-03", "2025-09-24", "2024-04-15", "2021-06-17", "2023-11-19", "2024-09-14",
                            "2023-11-28", "2020-12-18", "2020-12-24", "2021-11-18", "2020-11-17", "2021-10-25", "2023-06-14"};
            String[] bus = {"버스A", "버스B", "저상B", "2층버스", "리무진A", "리무진A", "리무진B", "마을버스A", "마을버스A",
                    "마을버스A", "마을버스B", "저상C", "저상A"};


            for(int i = 0; i < num.length; i++) {
                /* Set the query statement */
                db_statement.setInt(1, num[i]);
                db_statement.setInt(2, year[i]);
                db_statement.setString(3, date[i]);
                db_statement.setString(4, bus[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 직원 (직원번호, 이름, 주소, 전화번호, 급여, 업무) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            int[] num = {12484, 95187, 13854, 74321, 68421, 68432, 68431, 87462, 71641, 73512, 71432, 95421};
            String[] name = {"박성훈", "최성진", "박정호", "박민수", "방영진", "한경수",
                    "김광수", "박진우", "박상철", "김성수", "김영식", "한영철"};
            String[] address = {"성동구", "양천구", "송파구", "서초구", "서초구", "중구",
                    "도봉구", "노원구", "영등포구", "서대문구", "마포구", "중구"};
            String[] phone = {"010-4841-7215", "010-7843-4321", "010-7421-7215", "010-6215-4314",
                    "010-8435-7321", "010-1242-6215", "010-2482-4321", "010-7135-1242",
                    "010-1514-3215", "010-1542-4315", "010-4251-4215", "010-6284-4554"};
            int[] salary = {3200, 3500, 3200, 3600, 3700, 4500, 6200, 7800, 11300, 4800, 4500, 1200};
            String[] job = {"운전기사", "운전기사", "운전기사", "운전기사", "운전기사", "운전기사",
                    "이사", "사장아들", "사장", "경리", "운전기사", "인턴"};


            for(int i = 0; i < num.length; i++) {
                /* Set the query statement */
                db_statement.setInt(1, num[i]);
                db_statement.setString(2, name[i]);
                db_statement.setString(3, address[i]);
                db_statement.setString(4, phone[i]);
                db_statement.setInt(5, salary[i]);
                db_statement.setString(6, job[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
