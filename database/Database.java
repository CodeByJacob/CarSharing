package carsharing.database;

import java.sql.*;

@SuppressWarnings("unused")
public class Database {
    private static final String DRIVER_JDBC = "org.h2.Driver";
    final private String DB_URL;
    Connection connection = null;
    Statement statement = null;

    public Database(String DB_URL){
        this.DB_URL = DB_URL;
        String createCompaniesQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                ", NAME VARCHAR(50) NOT NULL UNIQUE " +
                ");";
        String createCarsQuery = "CREATE TABLE IF NOT EXISTS CAR (" +
                "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                ", NAME VARCHAR(255) NOT NULL UNIQUE" +
                ", COMPANY_ID INTEGER NOT NULL" +
                ", FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ");";
        String createCustomerQuery = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                ", NAME VARCHAR(255) NOT NULL UNIQUE" +
                ", RENTED_CAR_ID INTEGER" +
                ", FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                ");";

        try{
            registerConnection();

            if(connection != null && !connection.isClosed()){
                statement = connection.createStatement();
                statement.executeUpdate(createCompaniesQuery);
                statement.executeUpdate(createCarsQuery);
                statement.executeUpdate(createCustomerQuery);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void registerConnection(){
        try {
            // Register driver for JDBC
            Class.forName(DRIVER_JDBC);
            // Create connection
            this.connection = DriverManager.getConnection(DB_URL);
            this.connection.setAutoCommit(true);
        }
        catch (ClassNotFoundException | SQLException e) {
            // Class.forName() - CLASS
            // Connection - SQL
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeQuery(String query){
        ResultSet result = null;
        try {
            this.statement = connection.createStatement();
            result =  statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
            result = null;
        }
        finally {
            return result;
        }
    }

    public void execute(String query){
        try {
            this.statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void unregisterConnection(){
        try {
            if (!this.statement.isClosed()){
                this.statement.close();
            }

            if(!this.connection.isClosed()){
                this.connection.close();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Database getDatabase(){
        return this;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public String getDB_URL() {
        return DB_URL;
    }
}
