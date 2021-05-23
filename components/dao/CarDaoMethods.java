package carsharing.components.dao;

import carsharing.components.elements.Car;

import java.sql.ResultSet;
import java.util.List;

@SuppressWarnings("unused")
public interface CarDaoMethods {
    List<Car> getAllCars(int owner);
    List<Car> getAllNotRentedCars(int owner);
    List<Car> getAllCars();
    void addCar(Car car, int owner);
}
