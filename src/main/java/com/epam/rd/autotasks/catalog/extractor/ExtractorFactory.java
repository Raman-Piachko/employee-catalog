package com.epam.rd.autotasks.catalog.extractor;

import com.epam.rd.autotasks.catalog.domain.Employee;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public interface ExtractorFactory {
    ResultSetExtractor<List<Employee>> getExtractor(boolean withChain);
}