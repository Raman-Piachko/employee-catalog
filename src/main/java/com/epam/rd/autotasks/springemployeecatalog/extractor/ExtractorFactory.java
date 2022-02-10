package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public interface ExtractorFactory {
    ResultSetExtractor<List<Employee>> getExtractor(boolean withChain);
}