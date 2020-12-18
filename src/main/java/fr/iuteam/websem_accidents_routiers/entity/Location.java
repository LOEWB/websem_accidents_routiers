package fr.iuteam.websem_accidents_routiers.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Location {
    private String cityCode;
    private String city;
    private String address;
    private String postCode;

    public Location(String cityCode, String city, String address, String postCode) {
        this.cityCode = cityCode;
        this.city = city;
        this.address = address;
        this.postCode = postCode;

    }
}
