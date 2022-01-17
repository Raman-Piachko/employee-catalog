package com.epam.rd.autotasks.springemployeecatalog.rowpappers;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import com.epam.rd.autotasks.springemployeecatalog.extractors.Extractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.SALARY;

public class EmployeeRowMapper implements RowMapper<Employee> {
    private Extractor departmentExtractor;
    private Extractor fullNameExtractor;

    public EmployeeRowMapper(Extractor departmentExtractor, Extractor fullNameExtractor) {
        this.departmentExtractor = departmentExtractor;
        this.fullNameExtractor = fullNameExtractor;
    }


    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        Employee employee = new Employee(
                rs.getLong(ID),
                (FullName) fullNameExtractor.getEntity(rs),
                Position.valueOf(rs.getString(POSITION)),
                rs.getDate(HIRE_DATE).toLocalDate(),
                rs.getBigDecimal(SALARY),
                (Employee) rs.getObject(MANAGER),
                (Department) departmentExtractor.getEntity(rs));
        return employee;
    }
}
