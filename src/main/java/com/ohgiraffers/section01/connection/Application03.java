package com.ohgiraffers.section01.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application03 {

    public static void main(String[] args) {

        /* 수업목표. Properties 파일로 주요 정보를 은닉할 수 있다. */

        /* 필기.
         *    Properties 파일을 사용하는 이유
         *    1. 수기로 작성 시 오타 발생 가능성 높음(한 곳에 모아둬서 유지보수 용이)
         *    2. 설정 속성에 수정 사항이 발생할 경우 파일마다 번거롭게 수정해야 하므로 유지보수 비용 증가
         *    3. Connection 을 사용하는 파일마다 동일한 코드의 중복을 막는다.
         * */

        Properties prop = new Properties();

        Connection con = null;     // 데이터베이스 연결을 나타내는 Connection 객체를 선언하고, 초기화

        try {
            prop.load(new FileReader("src/main/java/com/ohgiraffers/section01/connection/jdbc-config.properties"));

            System.out.println("prop = " + prop);         //순서랜덤으로, key & value값 불러오므로~

            /* 필기. 값 꺼내오기 getProperty() */

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(driver);              // JDBC 드라이버를 동적으로 로드함

            // JDBC 드라이버 매니저를 통해 데이터베이스에 연결한다.
            con = DriverManager.getConnection(url, user, password);       // * add catch
            System.out.println("con = " + con);

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } finally {

            if (con != null) {
                // 객체가 null이 아닌 경우에만 연결을 닫는다.
                // 이렇게 하면 예외가 발생하여 연결을 열지 못한 경우에는 연결을 닫지 않는다.

                try {
                    con.close();

                } catch (SQLException e) {
                    // 예외가 발생한 경우에 해당 예외를 처리한다.
                    // 각각의 예외 타입에 따라 다른 처리를 수행할 수 있다.
                    // 여기서는 각각의 예외를 런타임 예외로 감싸서 다시 던져준다.

                    // * 출력 문구 할땐, throw 로 나오면서 실행(프로그램 종료)되므로 throw 전에 입력해야한다.

                    throw new RuntimeException(e);

                }
                // => 데이터베이스 연결 설정을 외부 프로퍼티 파일에 저장하여 유지보수성을 높이고,
                // 예외 처리를 통해 안정적인 프로그램 동작을 보장합니다.

            }
        }

    }
}
