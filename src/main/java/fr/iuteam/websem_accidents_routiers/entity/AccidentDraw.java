package fr.iuteam.websem_accidents_routiers.entity;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Data;


@Data
@JsonldType("http://www.example.org/accident")
public class AccidentDraw {
    @JsonldId
    private String id;
    @JsonldProperty("http://www.example.org/luminosity")
    private String lum;
    @JsonldProperty("http://www.example.org/atm")
    private String atm  ;
    @JsonldProperty("http://www.example.org/agg")
    private String agg ;
    @JsonldProperty("http://www.example.org/col")
    private String col;
    @JsonldProperty("http://www.example.org/inter")
    private String inter ;
}
