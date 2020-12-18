package fr.iuteam.websem_accidents_routiers.entity;


import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.Data;

@Data
@JsonldType("http://www.example.org/location")
public class LocationDraw {
    @JsonldProperty("http://www.example.org/city")
    private String city ;
}
