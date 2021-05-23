package carsharing.components.elements;

@SuppressWarnings("unused")
public class Company {
    int ID;
    private String name;

    public Company(int _ID, String _name){
        this.ID = _ID;
        this.name = _name;
    }

    public Company(String _name){
        this.name = _name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
