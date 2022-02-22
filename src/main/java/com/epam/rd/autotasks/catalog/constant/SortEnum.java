package com.epam.rd.autotasks.catalog.constant;

public enum SortEnum {
    LASTNAME(" ORDER BY LASTNAME_e"),
    HIRED(" ORDER BY HIREDATE_e"),
    POSITION(" ORDER BY POSITION_e"),
    SALARY(" ORDER BY SALARY_e");

    private final String sortName;

    SortEnum(String sortName) {
        this.sortName = sortName;
    }

    public String getSortName() {
        return sortName;
    }
}