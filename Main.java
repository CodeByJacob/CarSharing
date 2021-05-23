package carsharing;

import carsharing.components.CarDao;
import carsharing.components.CompanyDao;
import carsharing.components.CustomerDao;
import carsharing.components.dao.CarDaoMethods;
import carsharing.components.dao.CompanyDaoMethods;
import carsharing.components.dao.CustomerDaoMethods;
import carsharing.components.elements.Car;
import carsharing.components.elements.Company;
import carsharing.components.elements.Customer;
import carsharing.database.Database;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        String mainMenu = "1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit";
        String managerMenu = "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back";
        String carsMenu = "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back";
        String customerMenu = "1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back";

        String dbFileName;
        if (args.length < 2 || !args[0].equals("-databaseFileName")) {
            dbFileName = "cars";
        } else {
            dbFileName = args[1];
        }
        final String DB_URL = "jdbc:h2:./src/carsharing/db/" + dbFileName;

        Database database = new Database(DB_URL);
        Scanner scanner = new Scanner(System.in);

        int menuItem;

        boolean repeatMain = true;
        while (repeatMain) {
            System.out.println(mainMenu);
            menuItem = scanner.nextInt();
            scanner.nextLine();
            CustomerDaoMethods customerDao = new CustomerDao(database.getDatabase());

            switch (menuItem){
                case 1:
                    boolean repeat = true;
                    while (repeat) {
                        System.out.println(managerMenu);
                        menuItem = scanner.nextInt();
                        scanner.nextLine();
                        CompanyDaoMethods companyDao = new CompanyDao(database.getDatabase());
                        switch (menuItem) {
                            case 1:
                                List<Company> companyList = companyDao.getAllCompanies();
                                if (companyList == null || companyList.isEmpty()) {
                                    System.out.println("The company list is empty!");
                                    continue;
                                } else {
                                    int i = 0;
                                    System.out.println("Choose company: ");
                                    for (Company company : companyList) {
                                        System.out.println(++i + ". " + company.getName());
                                    }
                                    System.out.println("0. Back\n");

                                    menuItem = scanner.nextInt();
                                    scanner.nextLine();
                                    if (menuItem == 0) {
                                        continue;
                                    } else {
                                        int companyNum = menuItem;
                                        boolean repeatCars = true;
                                        while (repeatCars) {
                                            System.out.println(carsMenu);
                                            menuItem = scanner.nextInt();
                                            scanner.nextLine();
                                            CarDaoMethods carDao = new CarDao(database.getDatabase());
                                            switch (menuItem) {
                                                case 1:
                                                    List<Car> carList = carDao.getAllCars(companyList.get(companyNum - 1).getID());
                                                    if (carList == null || carList.isEmpty()) {
                                                        System.out.println("The car list is empty!");
                                                        continue;
                                                    } else {
                                                        int j = 0;
                                                        System.out.println("Cars list!");
                                                        for (Car car : carList) {
                                                            System.out.println(++j + ". " + car.getName());
                                                        }
                                                        System.out.println("0. Back\n");
                                                    }
                                                    continue;
                                                case 2:
                                                    System.out.print("Enter the car name: ");
                                                    String name = scanner.nextLine();
                                                    carDao.addCar(new Car(name), companyList.get(companyNum - 1).getID());
                                                    continue;
                                                default:
                                                    repeatCars = false;
                                                    break;
                                            }
                                        }
                                    }
                                    continue;
                                }
                            case 2:
                                System.out.print("Enter the company name: ");
                                String name = scanner.nextLine();
                                companyDao.addCompany(new Company(name));
                                continue;
                            default:
                                repeat = false;
                                break;
                        }
                    }
                    break;
                case 2:
                    int k = 0;
                    List<Customer> customerList = customerDao.getAllCustomers();
                    if (customerList == null || customerList.isEmpty()) {
                        System.out.println("The customer list is empty!");
                        continue;
                    }
                    else{
                        System.out.println("Choose a customer: ");
                        for (Customer customer : customerList) {
                            System.out.println(++k + ". " + customer.getName());
                        }
                        System.out.println("0. Back");
                        menuItem = scanner.nextInt();
                        scanner.nextLine();
                        if(menuItem == 0){
                            continue;
                        }
                        else{
                            int customer = menuItem;
                            boolean repeatCustomer = true;
                            while(repeatCustomer){
                                System.out.println(customerMenu);
                                menuItem = scanner.nextInt();
                                scanner.nextLine();
                                switch (menuItem) {
                                    case 1:
                                        List<Car> rentCars = customerDao.getRentedCars(customerList.get(customer -1).getID());
                                        if(rentCars == null || rentCars.isEmpty()) {
                                            CompanyDaoMethods companyDaoMethods = new CompanyDao(database.getDatabase());
                                            List<Company> companyList = companyDaoMethods.getAllCompanies();
                                            if (companyList == null || companyList.isEmpty()) {
                                                System.out.println("The company list is empty!");
                                                continue;
                                            } else {
                                                int i = 0;
                                                System.out.println("Choose company: ");
                                                for (Company company : companyList) {
                                                    System.out.println(++i + ". " + company.getName());
                                                }
                                                System.out.println("0. Back\n");

                                                menuItem = scanner.nextInt();
                                                scanner.nextLine();
                                                int companyNum = menuItem;

                                                CarDaoMethods carDaoCust = new CarDao(database.getDatabase());
                                                List<Car> cars = carDaoCust.getAllNotRentedCars(companyList.get(companyNum - 1).getID());
                                                if (cars == null || cars.isEmpty()) {
                                                    System.out.println("The car list is empty!");
                                                    continue;
                                                } else {
                                                    int j = 0;
                                                    System.out.println("Choose a car:");
                                                    for (Car car : cars) {
                                                        System.out.println(++j + ". " + car.getName());
                                                    }
                                                    System.out.println("0. Back\n");
                                                    menuItem = scanner.nextInt();
                                                    scanner.nextLine();
                                                    if (menuItem == 0) {
                                                        // repeatCustomer = false;
                                                        break;
                                                    } else {
                                                        customerDao.rentCar(customerList.get(customer - 1).getID(), cars.get(menuItem - 1).getID());
                                                        System.out.println("You rented '" + cars.get(menuItem - 1).getName() + "'");
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            System.out.println("You've already rented a car!");
                                            continue;
                                        }
                                    case 2:
                                        List<Car> rented = customerDao.getRentedCars(customerList.get(customer -1).getID());
                                        if (rented == null || rented.isEmpty()) {
                                            System.out.println("You didn't rent a car!");
                                            continue;
                                        }
                                        else{
                                            customerDao.returnCar(customerList.get(customer-1).getID());
                                            System.out.println("You've returned a rented car!");
                                            continue;
                                        }
                                    case 3:
                                        System.out.println("My rented car:");
                                        List<Car> rentedCars = customerDao.getRentedCars(customerList.get(customer -1).getID());
                                        if (rentedCars == null || rentedCars.isEmpty()) {
                                            System.out.println("You didn't rent a car!");
                                            continue;
                                        }
                                        else{
                                            for (Car car : rentedCars) {
                                                System.out.println(car.getName() +"\nCOMPANY:\n" + car.getCompanyName());
                                            }
                                        }
                                        break;
                                    default:
                                        repeatCustomer = false;
                                        break;
                                }
                            }
                        }
                        }
                    break;
                case 3:
                    System.out.print("Enter the customer name: ");
                    String name = scanner.nextLine();
                    customerDao.addCustomer(new Customer(name));
                    continue;
                default:
                    repeatMain = false;
                    break;
            }
        }
    }
}