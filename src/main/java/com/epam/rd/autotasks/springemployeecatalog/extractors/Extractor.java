package com.epam.rd.autotasks.springemployeecatalog.extractors;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Extractor {
    Object getEntity(ResultSet resultSet) throws SQLException;
}
