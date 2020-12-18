package fr.iuteam.websem_accidents_routiers.controller;

import fr.iuteam.websem_accidents_routiers.entity.Accident;
import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


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
    @RequestMapping("")
    public String index(Model m, @ModelAttribute("accident") Accident accident, HttpServletRequest request) throws QueryBuilderException {

        m.addAttribute("lum", caracInsertor.getLumDico());
        m.addAttribute("agg", caracInsertor.getAggDico());
        m.addAttribute("atm", caracInsertor.getAtmDico());
        m.addAttribute("col", caracInsertor.getColDico());
        m.addAttribute("inter", caracInsertor.getIntDico());
        if(request.getMethod().equals("POST")){
            QueryBuild build = new QueryBuild();
            build.select("?subject", "?lum","?vlum", "?value","?atm", "?vatm", "?agg", "?vagg","?col", "?vcol","?inter", "?vinter")
                    .where("?subject ?lum ?vlum")
                    .where("?subject ?atm ?vatm")
                    .where("?subject ?agg ?vagg")
                    .where("?subject ?col ?vcol")
                    .where("?subject ?inter ?vinter");

            accident.QbFilterList(build,accident.getLuminosity(),"?lum = <http://www.exemple.org/lumiere>",caracInsertor.getLumDico(),"?vlum");
            if(!accident.getAtm().isEmpty()){
                build.and();
                accident.QbFilterList(build,accident.getAtm(),"?atm = <http://www.exemple.org/conditions_atmo>",caracInsertor.getAtmDico(), "?vatm");
            }
            if(!accident.getAgglo().isEmpty()){
                build.and();
                accident.QbFilterList(build,accident.getAgglo(),"?agg = <http://www.exemple.org/en_agglo_ou_hors_agglo>",caracInsertor.getAggDico(), "?vagg");
            }
            if(!accident.getNbCol().isEmpty()){
                build.and();
                accident.QbFilterList(build,accident.getNbCol(),"?col = <http://www.exemple.org/type_collision>",caracInsertor.getColDico(), "?vcol");
            }
            if(!accident.getIntersection().isEmpty()){
                build.and();
                accident.QbFilterList(build,accident.getIntersection(),"?inter = <http://www.exemple.org/intersection>",caracInsertor.getIntDico(), "?vinter");
            }

            build.limit(20);
            System.out.println(build);
        }
        return "/index";
    }

    @PostMapping("/t")
    public String noAcci(Model m, @ModelAttribute("accident") Accident accident) throws QueryBuilderException {

        return "/index";
    }

    @PostMapping("/accDate")
    public String dateAcci(Model m, @ModelAttribute("accident") Accident accident) throws QueryBuilderException {
        QueryBuild build = new QueryBuild();
        build.select("?subject", "?lum","?vlum", "?value","?atm", "?vatm", "?agg", "?vagg","?col", "?vcol","?inter", "?vinter")
                .where("?subject ?lum ?vlum")
                .where("?subject ?atm ?vatm")
                .where("?subject ?agg ?vagg")
                .where("?subject ?col ?vcol")
                .where("?subject ?inter ?vinter");

        accident.QbFilterList(build,accident.getLuminosity(),"?lum = <http://www.exemple.org/lumiere>",caracInsertor.getLumDico(),"?vlum");
        build.and();
        accident.QbFilterList(build,accident.getAtm(),"?atm = <http://www.exemple.org/conditions_atmo>",caracInsertor.getAtmDico(), "?vatm");
        build.and();
        accident.QbFilterList(build,accident.getAgglo(),"?agg = <http://www.exemple.org/en_agglo_ou_hors_agglo>",caracInsertor.getAggDico(), "?vagg");
        build.and();
        accident.QbFilterList(build,accident.getNbCol(),"?col = <http://www.exemple.org/type_collision>",caracInsertor.getColDico(), "?vcol");
        build.and();
        accident.QbFilterList(build,accident.getIntersection(),"?inter = <http://www.exemple.org/intersection>",caracInsertor.getIntDico(), "?vinter");




        build.limit(20);
        System.out.println(build);
        //System.out.println(accident.getLuminosity());
        //System.out.println(caracInsertor.getLumDico().get(accident.getLuminosity().get(0)));
        //System.out.println(caracInsertor.getAtmDico().get(accident.getAtm()));
        //System.out.println(caracInsertor.getAggDico().get(accident.getAgglo()));
        //System.out.println(caracInsertor.getColDico().get(accident.getNbCol()));
        System.out.println(caracInsertor.getIntDico().get(accident.getIntersection()));
        return "redirect:/";
    }
}
