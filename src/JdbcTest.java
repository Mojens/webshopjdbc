import java.sql.*;
import java.util.Scanner;

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
    insertRow();
    deleteRow();
    updateRow();
  }


  public static void insertRow(){
    Scanner input = new Scanner(System.in);
    final String INSERT_SQL = "INSERT INTO product(name, price) VALUES (?, ?)";
    getConnection();
    try {
      PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL);
      System.out.print("Enter Name: ");
      String nameInput = input.nextLine();
      System.out.print("Enter Price: ");
      int priceInput = input.nextInt();
      // Sætter name ind i tabellen
      preparedStatement.setString(1,nameInput);
      //Sætter tallet ind i tabellen
      preparedStatement.setInt(2,priceInput);

      preparedStatement.executeUpdate();

      System.out.println("Product inserted: " + "Name: " + nameInput+", " + "Price: " + priceInput);


    }catch (SQLException e){
      System.out.println(e);
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

  public static void deleteRow(){
    final String DELETE_QUERY = "DELETE FROM product WHERE id=?";
    Scanner input = new Scanner(System.in);
    getConnection();
    System.out.println("Enter ID to be deleted");
    int id = input.nextInt();

    try{
      PreparedStatement preparedStatementDelete = getConnection().prepareStatement(DELETE_QUERY);
      preparedStatementDelete.setInt(1,id);
      preparedStatementDelete.executeUpdate();
      System.out.println("Row with ID: " + "#"+ id + " " +"Is now deleted");

    }catch (SQLException e){
      System.out.println("Could not delete");
      e.printStackTrace();
    }

  }

  public static void updateRow(){
    Scanner input = new Scanner(System.in);
    final String UPDATE_QUERY = "UPDATE product SET name = ?, price = ? WHERE id = ?";
    System.out.println("Enter ID you want to update: ");
    int id = Integer.parseInt(input.nextLine());
    System.out.println("Enter new Name");
    String name = input.nextLine();
    System.out.println("Enter new Price");
    int price = Integer.parseInt(input.nextLine());
    getConnection();

    try{
      PreparedStatement preparedStatementUpdateRow = getConnection().prepareStatement(UPDATE_QUERY);
      preparedStatementUpdateRow.setString(1,name);
      preparedStatementUpdateRow.setInt(2,price);
      preparedStatementUpdateRow.setInt(3,id);
      preparedStatementUpdateRow.executeUpdate();
      System.out.println("Row with ID," + "#"+id+" Is updated");

    }catch (SQLException e){
      System.out.println(e);
    }

  }

}
