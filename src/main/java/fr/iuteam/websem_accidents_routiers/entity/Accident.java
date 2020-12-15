package fr.iuteam.websem_accidents_routiers.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Accident {

    private List<Integer> luminosity;
    private Date date;
    private Integer agglo;
    private Integer intersection;
    private Integer nbCol;
    private Integer atm;
    private String dep;
    private String postCode;
    private String adrPost;
    private Long lat;
    private Long lon;

}
