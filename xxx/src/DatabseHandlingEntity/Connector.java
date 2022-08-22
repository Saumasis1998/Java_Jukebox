package DatabseHandlingEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection connection=null;
    public static void setConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/TheJukeBox","root","Saumasis@1998");
    }
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) {
        try {
            setConnection();
//            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}