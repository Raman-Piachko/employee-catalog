package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.SALARY;

public class EmployeeExtractor implements Extractor {
    @Autowired
    private Extractor fullNameExtractor;
    @Autowired
    private Extractor departmentExtractor;

    @Override
    public Object getEntity(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getLong(ID),
                (FullName) fullNameExtractor.getEntity(rs),
                Position.valueOf(rs.getString(POSITION)),
                rs.getDate(HIRE_DATE).toLocalDate(),
                rs.getBigDecimal(SALARY),
                (Employee) getEntity(rs),
                (Department) departmentExtractor.getEntity(rs));
    }
}
