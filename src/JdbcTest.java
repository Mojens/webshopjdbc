import java.sql.*;

public class JdbcTest {
  private final static String DB_URL = "jdbc:mysql://localhost:3306/webshop";
  private final static String UID = "root";
  private final static String PWD = "";


  public static void main(String[] args) {
    try {
      getConnection();
      System.out.println("Connected to DB");
      Statement statement = getConnection().createStatement();
      final String SQL_QUERY = "SELECT * FROM product";
      ResultSet resultSet = statement.executeQuery(SQL_QUERY);

      //Read from resultset
      while (resultSet.next()) {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        int price = resultSet.getInt(3);
        System.out.println("#" + id + ", " + name + ", " + price);

      }
      statement.close();
      getConnection().close();


    } catch (SQLException e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }
  public static Connection getConnection(){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(DB_URL, UID, PWD);
      return connection;
    }catch (SQLException e){
      System.out.println(e);
    }
    return connection;
  }
}
