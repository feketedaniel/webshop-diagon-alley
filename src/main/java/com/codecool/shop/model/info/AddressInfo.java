package com.codecool.shop.model.info;

public class AddressInfo {
    private String country;
    private String city;
    private int zip;
    private String streetHouseNum;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreetHouseNum() {
        return streetHouseNum;
    }

    public void setStreetHouseNum(String streetHouseNum) {
        this.streetHouseNum = streetHouseNum;
    }

    public AddressInfo(String country, String city, int zip, String streetHouseNum) {
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.streetHouseNum = streetHouseNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nCountry: ").append(this.country).append("\n");
        sb.append("\nCity: ").append(this.city).append("\n");
        sb.append("\nZip: ").append(this.zip).append("\n");
        sb.append("\nStreet HouseNr.: ").append(this.streetHouseNum).append("\n");
        return sb.toString();    }
}
