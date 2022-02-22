package com.epam.rd.autotasks.catalog.extractor;

import com.epam.rd.autotasks.catalog.domain.Employee;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExtractorFactoryImpl implements ExtractorFactory {

    public ResultSetExtractor<List<Employee>> getExtractor(boolean withChain) {
        if (withChain) {
            return new EmployeeWithChainExtractor();
        } else {
            return new SimpleEmployeeExtractor();
        }
    }
}