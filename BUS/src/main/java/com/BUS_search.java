package com;

import java.sql.*;
import java.util.Scanner;

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
        // Prepare the query statement
        //String query_string = "insert into TEST_TB (, value) VALUES (?, ?)";
        String query_string = "1";
        // Preparation for db process
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             Statement db_statement = db_connection.createStatement()){

            // Create Tables
            query_string = "CREATE TABLE `버스` (" +
                    "`차량번호`         INT," +
                    "`년식`            INT," +
                    "`유지보수예정일`    DATE," +
                    "`유형`            VARCHAR(45)," +
                    "PRIMARY KEY(`차량번호`))";
            int result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `버스유형` (" +
                    "`유형`             VARCHAR(45)," +
                    "`최대탑승인원`      INT," +
                    "PRIMARY KEY(`유형`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `직원` (" +
                    "`직원번호`         INT," +
                    "`이름`            VARCHAR(45)," +
                    "`주소`            VARCHAR(45)," +
                    "`전화번호`         VARCHAR(45)," +
                    "`급여`            INT," +
                    "`업무`            VARCHAR(45)," +
                    "PRIMARY KEY(`직원번호`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `운전기사` (" +
                    "`직원번호`         INT," +
                    "`교통위반티켓수`    INT," +
                    "`운전면허종료일`    DATE," +
                    "`총운행거리`       INT," +
                    "PRIMARY KEY(`직원번호`)," +
                    "FOREIGN KEY(`직원번호`) REFERENCES 직원(`직원번호`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `정류장` (" +
                    "`정류장명`         VARCHAR(45)," +
                    "`주소`            VARCHAR(45)," +
                    "PRIMARY KEY(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `경유` (" +
                    "`경유코드`         VARCHAR(45)," +
                    "`정류장명`         VARCHAR(45)," +
                    "`도착시간`         FLOAT," +
                    "`출발시간`         FLOAT," +
                    "PRIMARY KEY(`경유코드`, `도착시간`)," +
                    "FOREIGN KEY(`정류장명`) REFERENCES 정류장(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `노선` (" +
                    "`노선명`           VARCHAR(45)," +
                    "`출발정류장`        VARCHAR(45)," +
                    "`도착정류장`        VARCHAR(45)," +
                    "PRIMARY KEY(`노선명`)," +
                    "FOREIGN KEY(`출발정류장`) REFERENCES 정류장(`정류장명`)," +
                    "FOREIGN KEY(`도착정류장`) REFERENCES 정류장(`정류장명`))";
            result = db_statement.executeUpdate(query_string);
            System.out.println("Updated query: " + result);

            query_string = "CREATE TABLE `운행스케줄` (" +
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

            query_string = "CREATE TABLE `운행이력` (" +
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
            System.out.println("Updated query: " + result);

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

        query_string = "INSERT INTO 운전기사 (직원번호, 교통위반티켓수, 운전면허종료일, 총운행거리) VALUES (?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            int[] num = {12484, 95187, 13854, 74321, 68421, 68432, 71432};
            int[] ticket = {0, 1, 2, 0, 6, 7, 1};
            String[] dateend = {"2028-04-01", "2027-12-01", "2030-01-01",
                    "2024-06-01", "2020-12-01", "2027-01-01", "2026-08-01"};
            int[] kilo = {42481, 79501, 43113, 6004, 87651, 120187, 32154};


            for(int i = 0; i < num.length; i++) {
                /* Set the query statement */
                db_statement.setInt(1, num[i]);
                db_statement.setInt(2, ticket[i]);
                db_statement.setString(3, dateend[i]);
                db_statement.setInt(4, kilo[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 정류장 (정류장명, 주소) VALUES (?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] station = {"서울", "홍천", "인제", "대구", "대전", "부산", "포항", "울산",
                    "천안", "김포", "부천", "인천", "세종", "광주", "목포", "신안"};
            String[] address = {"123-4", "432-3", "33-34", "234-5", "123-9", "638-2", "123-555", "481-2",
                    "213-89", "7435-5", "123-87", "745-66", "834-55", "125-62", "784-21", "481-21"};

            for(int i = 0; i < station.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, station[i]);
                db_statement.setString(2, address[i]);

                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 노선 (노선명, 출발정류장, 도착정류장) VALUES (?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] name = {"서부", "인김", "광서", "포인", "서신", "부목", "세부",
                    "대목", "인신", "울인", "부서", "세광", "인천", "목광", "김울"};
            String[] start = {"서울", "인제", "광주", "포항", "서울", "부산", "세종",
                    "대구", "인천", "울산", "부천", "세종", "인제", "목포", "김포"};
            String[] end = {"부산", "김포", "서울", "인천", "신안", "목포", "부산",
                    "목포", "신안", "인천", "서울", "광주", "천안", "광주", "울산"};

            for(int i = 0; i < name.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, name[i]);
                db_statement.setString(2, start[i]);
                db_statement.setString(3, end[i]);
                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 경유 (경유코드, 정류장명, 도착시간, 출발시간) VALUES (?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] code = {"465", "465", "465", "231", "231", "412", "121", "613", "613", "956", "956",
                     "002", "002", "411", "411", "411", "112", "112", "521", "156", "251", "444", "444", "277", "888"};
            String[] stationname = {"천안", "세종", "대구", "홍천", "대구", "천안", "천안", "세종", "목포", "대구", "신안",
                    "대전", "포항", "신안", "광주", "대전", "김포", "부천", "세종", "김포", "대전", "홍천", "김포", "신안", "천안"};
            Float[] arrivaltime = {9.40f, 10.20f, 11.50f, 11.50f, 15.30f, 15.30f, 12.00f, 09.20f, 09.50f, 12.20f, 14.40f,
                    12.30f, 14.20f, 15.40f, 17.20f, 17.50f, 17.50f, 18.30f, 08.50f, 08.30f, 10.40f, 11.00f, 12.20f, 11.10f, 10.00f};
            Float[] leavetime = {9.50f, 10.40f, 12.00f, 12.10f, 15.45f, 16.00f, 13.00f, 09.30f, 10.10f, 12.30f, 14.50f,
                    12.40f, 14.50f, 15.55f, 17.40f, 17.55f, 18.00f, 18.45f, 09.30f, 09.00f, 20.00f, 11.10f, 12.30f, 11.20f, 10.30f};

            for(int i = 0; i < code.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, code[i]);
                db_statement.setString(2, stationname[i]);
                db_statement.setFloat(3, arrivaltime[i]);
                db_statement.setFloat(4, leavetime[i]);
                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 운행스케줄 (노선명, 요일, 출발시간, 도착시간, 경유코드) VALUES (?, ?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] wayname = {"서부", "서부", "광서", "포인", "서신", "부목", "세부",
                    "대목", "인신", "울인", "부서", "세광", "인천", "목광", "목광"};
            String[] day = {"월", "화", "목", "토", "일", "월", "수", "목", "토", "토", "토", "화", "수", "화", "목"};
            Float[] leave = {9.10f, 10.30f, 12.30f, 06.10f, 07.30f, 09.50f, 11.15f,
                    13.40f, 15.35f, 17.25f, 06.45f, 08.10f, 09.55f, 10.10f, 09.45f};
            Float[] arrive = {15.45f, 18.30f, 22.15f, 19.30f, 20.20f, 20.30f, 20.30f,
                    23.40f, 23.45f, 21.30f, 19.40f, 22.20f, 15.40f, 12.10f, 11.50f};
            String[] via = {"465", "231", "412", "121", "613", "956", "002",
                    "411", "112", "521", "156", "251", "444", "277", "888"};

            for(int i = 0; i < wayname.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, wayname[i]);
                db_statement.setString(2, day[i]);
                db_statement.setFloat(3, leave[i]);
                db_statement.setFloat(4, arrive[i]);
                db_statement.setString(5, via[i]);
                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        query_string = "INSERT INTO 운행이력 (노선명, 요일, 년도, 주, 기사, 운행버스) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            String[] waynames = {"서부", "서부", "서신", "부목", "부목", "세부",
                    "대목", "인신", "부서", "세광", "인천", "목광", "목광", "김울", "김울"};
            String[] days = {"월", "화", "목", "토", "일", "월", "수", "목", "토", "토", "토", "화", "수", "화", "목"};
            int[] year = {2019, 2020, 2019, 2019, 2019, 2018, 2020, 2019,
                    2019, 2019, 2020, 2020, 2020, 2018, 2020};
            int[] week = {5, 16, 15, 21, 04, 50, 42, 33, 15, 18, 19, 24, 42, 17, 18};
            int[] driver = {12484, 12484, 12484, 95187, 74321, 74321,
                    68421, 68421, 68421, 71432, 71432, 68421, 68421, 95187, 95187};
            int[] busnum = {1234, 2353, 8731, 1513, 1548, 7621, 7627, 7621,
                    7616, 2353, 2353, 2353, 2342, 1234, 1234};

            for(int i = 0; i < waynames.length; i++) {
                /* Set the query statement */
                db_statement.setString(1, waynames[i]);
                db_statement.setString(2, days[i]);
                db_statement.setInt(3, year[i]);
                db_statement.setInt(4, week[i]);
                db_statement.setInt(5, driver[i]);
                db_statement.setInt(6, busnum[i]);
                int result = db_statement.executeUpdate();
                System.out.println("Updated query: " + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }


        Scanner scan = new Scanner(System.in);

        System.out.println("승차정류장명: ");
        String onStation = scan.nextLine();
        System.out.println("하차정류장명: ");
        String offStation = scan.nextLine();
        System.out.println("시간: ");
        Float inputtime = scan.nextFloat();

        query_string = "select 노선명, 요일, 출발시간, t2 as 도착시간 from " +
                "(select 노선.노선명, 요일, 운행스케줄.출발시간, 운행스케줄.도착시간 as t2, 경유.도착시간 as t1 " +
                "from 노선, 운행스케줄, 경유 where 노선.노선명 = 운행스케줄.노선명 and 운행스케줄.경유코드 = 경유.경유코드 " +
                "and (? = 출발정류장 or ? = 정류장명) and 운행스케줄.출발시간 > ?) as T " +
                "where T.노선명 in ( " +
                "select 노선.노선명 " +
                "from 노선, 운행스케줄, 경유 " +
                "where 노선.노선명 = 운행스케줄.노선명 and 운행스케줄.경유코드 = 경유.경유코드 " +
                "and (? = 도착정류장 or ? = 정류장명) and 운행스케줄.출발시간 > ? and T.t1 <= 경유.도착시간) group by 노선명, 요일";

        try (Connection db_connection = DriverManager.getConnection(db_connection_url, db_info.getUsername(), db_info.getPassword());
             PreparedStatement db_statement = db_connection.prepareStatement(query_string)){

            /* Set the query statement */
            db_statement.setString(1, onStation);
            db_statement.setString(2, onStation);
            db_statement.setFloat(3, inputtime);
            db_statement.setString(4, offStation);
            db_statement.setString(5, offStation);
            db_statement.setFloat(6, inputtime);
            ResultSet result_set = db_statement.executeQuery();

            while(result_set.next()) {
                System.out.println("#" + "노선명:" + result_set.getString(1) +
                        " 요일:" + result_set.getString(2) + " 출발시간: " + result_set.getFloat(3) +
                        " 도착시간:" + result_set.getFloat(4));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
