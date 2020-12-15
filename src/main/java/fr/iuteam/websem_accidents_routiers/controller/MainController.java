package fr.iuteam.websem_accidents_routiers.controller;



import fr.iuteam.websem_accidents_routiers.entity.Accident;
import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {

    private CaracInsertor caracInsertor = new CaracInsertor();
    /*String datasetURL = "http://localhost:3030/test2";
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conn = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        QueryExecution qExec = conn.query("SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object;}");
        ResultSet rs = qExec.execSelect() ;
        int i = 0;
        while(rs.hasNext() && i<10) {
            QuerySolution qs = rs.next() ;
            Resource subject = qs.getResource("subject") ;
            System.out.println("Subject: "+qs) ;
            i++;
        }*/
        /*m.addAttribute("brands",brandService.findAllPage(p).getContent());
        m.addAttribute("pageable", p);*/
    @GetMapping("")
    public String index(Model m, @ModelAttribute("accident") Accident accident) throws QueryBuilderException {

        m.addAttribute("lum", caracInsertor.getLumDico());
        m.addAttribute("agg", caracInsertor.getAggDico());
        m.addAttribute("atm", caracInsertor.getAtmDico());
        m.addAttribute("col", caracInsertor.getColDico());
        m.addAttribute("inter", caracInsertor.getIntDico());
        return "/index";
    }

    @PostMapping("/accNo")
    public String noAcci(Model m){
        System.out.println("test");
        return "/index";
    }
    @PostMapping("/accDate")
    public String dateAcci(Model m, @ModelAttribute("accident") Accident accident) throws QueryBuilderException {
        QueryBuild build = new QueryBuild();
        build.select("?subject", "?predicate", "?value").where("?subject ?predicate ?value");
        build.filter("?predicate = <http://www.exemple.org/lumiere>").and();
        build.filter(accident.getLuminosity().stream()
                .map(n -> "?value = \""+caracInsertor.getLumDico().get((n)) + "\"")
                .collect(Collectors.joining(" || ","(",")")));


        build.limit(20);
        System.out.println(build);
        System.out.println(accident.getLuminosity());
        System.out.println(caracInsertor.getLumDico().get(accident.getLuminosity().get(0)));
        System.out.println(caracInsertor.getAtmDico().get(accident.getAtm()));
        System.out.println(caracInsertor.getAggDico().get(accident.getAgglo()));
        System.out.println(caracInsertor.getColDico().get(accident.getNbCol()));
        System.out.println(caracInsertor.getIntDico().get(accident.getIntersection()));
        return "redirect:/";
    }
}
