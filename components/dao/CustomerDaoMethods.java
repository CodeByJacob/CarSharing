package carsharing.components.dao;

import carsharing.components.elements.Car;
import carsharing.components.elements.Customer;

import java.util.List;

public interface CustomerDaoMethods {
    List<Customer> getAllCustomers();
    void rentCar(int customerID, int carID);
    void returnCar(int CustomerID);
    List<Car> getRentedCars(int customerID);
    void addCustomer(Customer customer);
}
