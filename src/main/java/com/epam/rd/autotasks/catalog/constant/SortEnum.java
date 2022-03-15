package com.epam.rd.autotasks.catalog.constant;

public enum SortEnum {
    LASTNAME("LASTNAME"),
    HIRED("HIREDATE"),
    POSITION("POSITION"),
    SALARY("SALARY");

    private final String sortName;

    SortEnum(String sortName) {
        this.sortName = sortName;
    }

    public String getSortName() {
        return sortName;
    }
}