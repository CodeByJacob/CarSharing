package carsharing.components;

import carsharing.components.elements.Car;
import carsharing.components.dao.CarDaoMethods;
import carsharing.database.Database;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class CarDao implements CarDaoMethods {
    Database database;

    public CarDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Car> getAllCars(int owner) {
        try {
            List<Car> cars = new LinkedList<>();

            ResultSet result = database
                    .executeQuery("SELECT * FROM CAR WHERE COMPANY_ID = " + owner + " ORDER BY ID");

            while(result.next()){
                cars.add(new Car(result.getInt("ID"),result.getString("NAME")));
            }

            return cars;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Car> getAllNotRentedCars(int owner) {
        try {
            List<Car> cars = new LinkedList<>();

            ResultSet result = database
                    .executeQuery("SELECT CAR.* FROM CAR WHERE CAR.COMPANY_ID = " + owner + "AND CAR.ID NOT IN " +
                            " (SELECT CUSTOMER.RENTED_CAR_ID FROM CUSTOMER WHERE CUSTOMER.RENTED_CAR_ID IS NOT NULL) ORDER BY CAR.ID");

            while(result.next()){
                cars.add(new Car(result.getInt("ID"),result.getString("NAME")));
            }

            return cars;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Car> getAllCars() {
        try {
            List<Car> cars = new LinkedList<>();

            ResultSet result = database
                    .executeQuery("SELECT NAME FROM CAR ORDER BY ID");

            while(result.next()){
                cars.add(new Car(result.getString("NAME")));
            }

            return cars;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void addCar(Car car, int owner) {
        database.execute("INSERT INTO CAR(NAME,COMPANY_ID) VALUES ('" + car.getName() +"','" + owner-- + "');");
    }
}
