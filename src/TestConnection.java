import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");  // ✅ HERE

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/HOSPITAL",
                    "root",
                    "903612949072"
            );

            System.out.println("Connected Successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}