package study02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import study01.User;

public class App {

    public static void main(String[] args) {
        App app = new App();
        Connection conn = app.getConn();
        if(conn != null) {
            List<User> list = app.getData(conn);
            System.out.println(list.size());
        }
    }
    
    private Connection getConn() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            System.out.println("JDBC Start!");

            String url = "jdbc:mariadb://prj1119.tplinkdns.com:8181/edu";
            String id = "user1";
            String pwd = "user1";
            return DriverManager.getConnection(url, id, pwd);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver NO");
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    private List<User> getData(Connection conn) {
        List<User> list = new ArrayList<User>();
        try {
            String sql = "SELECT * FROM users WHERE 삭제여부 = 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                User user = new User();
                user.set번호( rs.getInt(1) );
                user.set이메일( rs.getString(2) );
                user.set비밀번호( rs.getString(3) );
                user.set삭제여부( rs.getBoolean(4) );
                list.add(user);
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("JDBC SQL NO");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }

}