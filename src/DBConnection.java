import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Hospital",
                    "root",
                    "903612949072"
            );
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}




