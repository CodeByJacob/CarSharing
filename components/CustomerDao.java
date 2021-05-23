package carsharing.components;

import carsharing.components.dao.CustomerDaoMethods;
import carsharing.components.elements.Car;
import carsharing.components.elements.Customer;
import carsharing.database.Database;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CustomerDao implements CustomerDaoMethods {
    Database database;

    public CustomerDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            List<Customer> customers = new LinkedList<>();

            ResultSet result = database
                    .executeQuery("SELECT * FROM CUSTOMER ORDER BY ID");

            while (result.next()) {
                customers.add(new Customer(result.getInt("ID"), result.getString("NAME"), result.getInt("RENTED_CAR_ID")));
            }

            return customers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        database.execute("INSERT INTO CUSTOMER(NAME) VALUES ('" + customer.getName() + "');");
    }

    @Override
    public void rentCar(int CustomerID, int CarID) {
        database.execute("UPDATE CUSTOMER SET RENTED_CAR_ID = " + CarID + "WHERE ID = " + CustomerID + ";");
    }

    @Override
    public void returnCar(int CustomerID) {
        database.execute("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE CUSTOMER.ID = " + CustomerID);
    }

    @Override
    public List<Car> getRentedCars(int CustomerID){
        try {
            List<Car> rented = new LinkedList<>();

            ResultSet result = database
                    .executeQuery(
                            "SELECT CAR.NAME AS NAME, COMPANY.NAME AS CNAME FROM CUSTOMER JOIN CAR ON " +
                                    "CUSTOMER.RENTED_CAR_ID = CAR.ID JOIN COMPANY ON CAR.COMPANY_ID = " +
                                    "COMPANY.ID WHERE CUSTOMER.ID = " + CustomerID);


            while (result.next()) {
                rented.add(new Car(result.getString("NAME"), result.getString("CNAME")));
            }

            return rented;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}