package carsharing.components;

import carsharing.components.dao.CompanyDaoMethods;
import carsharing.components.elements.Company;
import carsharing.database.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class CompanyDao implements CompanyDaoMethods {
    Database database;

    public CompanyDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Company> getAllCompanies(){
        try {
            List<Company> companies = new LinkedList<>();

            ResultSet result = database
                    .executeQuery("SELECT * FROM COMPANY ORDER BY ID");

            while(result.next()){
                companies.add(new Company(result.getInt("ID"),result.getString("NAME")));
            }

            return companies;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public void addCompany(Company company){
        database.execute("INSERT INTO COMPANY(NAME) VALUES ('" + company.getName() +"');");
    }
}
