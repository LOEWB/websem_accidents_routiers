package fr.iuteam.websem_accidents_routiers.entity;


import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Accident {

    private List<Integer> luminosity;
    private Date date;
    private List<Integer> agglo;
    private List<Integer> intersection;
    private List<Integer> nbCol;
    private List<Integer> atm;
    private String dep;
    private String postCode;
    private String adrPost;
    private Long lat;
    private Long lon;

    public QueryBuild QbFilterList(QueryBuild build, List<Integer> list, String predicate, Map<Integer,String> map, String val) throws QueryBuilderException {
        if(!list.isEmpty()){
            build.filter(predicate).and();
            build.filter(list.stream()
                    .map(n -> val+"= \""+map.get((n)) + "\"")
                    .collect(Collectors.joining(" || ","(",")")));
        }
        return build;
    }

}
