package com.coderman.worldgdp.model;



public class CountryGDP {
    private Short year;
    private Double value;

    public Short getYear() {
        return year;
    }
    public void setYear(Short year) {
        this.year = year;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CountryGDP{" +
                "year=" + year +
                ", value=" + value +
                '}';
    }
}
