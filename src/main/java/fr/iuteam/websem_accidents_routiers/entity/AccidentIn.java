package fr.iuteam.websem_accidents_routiers.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AccidentIn {
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String luminosity;
    private String dep;
    private String com;
    private String agg;
    private String inter;
    private String atm;
    private String col;
    private String adr;
    private String lat;
    private String lon;
    private String city;
    private String cp;


}
