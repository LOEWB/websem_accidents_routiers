package fr.iuteam.websem_accidents_routiers.entity;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Data;


@Data
@JsonldType("http://exemple.org/accident")
public class AccidentDraw {
    @JsonldId
    private String id;
    @JsonldProperty("http://exemple.org/luminosity")
    private String lum;
    @JsonldProperty("http://exemple.org/atm")
    private String atm  ;
    @JsonldProperty("http://exemple.org/agg")
    private String agg ;
    @JsonldProperty("http://exemple.org/col")
    private String col;
    @JsonldProperty("http://exemple.org/inter")
    private String inter ;
}
