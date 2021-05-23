package carsharing.components.elements;

@SuppressWarnings("unused")
public class Customer {
    private int ID;
    private String name;
    private int rentedCarID;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(int ID, String name, int rentedCarID) {
        this.ID = ID;
        this.name = name;
        this.rentedCarID = rentedCarID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getRentedCarID() {
        return rentedCarID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRentedCarID(int rentedCarID) {
        this.rentedCarID = rentedCarID;
    }
}
