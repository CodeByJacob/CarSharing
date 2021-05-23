package carsharing.components.dao;

import carsharing.components.elements.Company;

import java.util.List;

@SuppressWarnings("unused")
public interface CompanyDaoMethods {
    List<Company> getAllCompanies();
    void addCompany(Company company);
}
