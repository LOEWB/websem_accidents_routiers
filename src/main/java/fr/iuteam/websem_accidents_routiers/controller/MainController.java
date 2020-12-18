package fr.iuteam.websem_accidents_routiers.controller;

import fr.iuteam.websem_accidents_routiers.entity.Accident;
import fr.iuteam.websem_accidents_routiers.entity.AccidentDraw;
import fr.iuteam.websem_accidents_routiers.entity.AccidentIn;
import fr.iuteam.websem_accidents_routiers.entity.AllResource;
import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import fr.iuteam.websem_accidents_routiers.model_insertion.Insert;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlConn;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlException;
import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import org.apache.jena.atlas.json.JsonValue;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {

    private CaracInsertor caracInsertor = new CaracInsertor();
    @RequestMapping("")
    public String index(Model m, @ModelAttribute("accident") Accident accident, HttpServletRequest request) throws QueryBuilderException, ParseException, SparqlException, IOException {

        m.addAttribute("lum", caracInsertor.getLumDico());
        m.addAttribute("agg", caracInsertor.getAggDico());
        m.addAttribute("atm", caracInsertor.getAtmDico());
        m.addAttribute("col", caracInsertor.getColDico());
        m.addAttribute("inter", caracInsertor.getIntDico());
        if(request.getMethod().equals("POST")){

            QueryBuild build = new QueryBuild();

            build.select("?subjectAccident", "?lum","?vlum","?atm", "?vatm", "?agg", "?vagg","?col", "?vcol", "?i","?int")
                    .where("?subjectAccident <http://example.org/location> ?subjectLocation")
                    .where("?subjectLocation <http://example.org/commune/> ?locationObject")

                    .where("?subjectAccident <http://www.example.org/lumiere> ?lum")
                    .where("?subjectAccident <http://www.exemple.org/en_agglo_ou_hors_agglo> ?atm")
                    .where("?subjectAccident <http://www.example.org/lumiere> ?agg")
                    .where("?subjectAccident <http://www.exemple.org/type_collision> ?col")
                    .where("?subjectAccident <http://www.exemple.org/intersection> ?int");

            if(!accident.getLuminosity().isEmpty()){
                if(!build.isFilterEmptyOrNull())
                    build.and();
                accident.Qb2FilterList(build,accident.getLuminosity(),caracInsertor.getLumDico(),"?lum");
            }
            if(!accident.getAtm().isEmpty()){
                if(!build.isFilterEmptyOrNull())
                    build.and();
                accident.Qb2FilterList(build,accident.getAtm(),caracInsertor.getAtmDico(), "?atm");
            }
            if(!accident.getAgglo().isEmpty()){
                if(!build.isFilterEmptyOrNull())
                    build.and();
                accident.Qb2FilterList(build,accident.getAgglo(),caracInsertor.getAggDico(), "?agg");
            }
            if(!accident.getNbCol().isEmpty()){
                if(!build.isFilterEmptyOrNull())
                    build.and();
                accident.Qb2FilterList(build,accident.getNbCol(),caracInsertor.getColDico(), "?col");
            }
            if(!accident.getIntersection().isEmpty()){
                if(!build.isFilterEmptyOrNull())
                    build.and();
                accident.Qb2FilterList(build,accident.getIntersection(),caracInsertor.getIntDico(), "?int");
            }

            build.limit(150);
            System.out.println(build);
            SparqlConn sparqlConn = SparqlConn.getInstance();
            RDFConnection conn = sparqlConn.getConn();
            QueryExecution query = conn.query(build.toString());
            ResultSet rs = query.execSelect() ;
            List<AccidentDraw> lacc = new ArrayList<>();

            while(rs.hasNext()) {
                QuerySolution qs = rs.next() ;
                AccidentDraw acc = new AccidentDraw();

                /*qs.getLiteral("vcol").getString();*/
                //qs.getLiteral("vi").getString();
               // qs.getLiteral("vcol").getValue();
                acc.setId(qs.getResource("subjectAccident").toString());
                acc.setLum(qs.getLiteral("lum").toString());
                acc.setAtm(qs.getLiteral("atm").toString()) ;
                acc.setAgg(qs.getLiteral("agg").toString()) ;
                acc.setCol(qs.getLiteral("col").toString()) ;
                acc.setInter(qs.getLiteral("int").toString());
                //System.out.println(qs.getLiteral("vinter").getValue().toString());
                //acc.setInter() ;
                lacc.add(acc);
            }
            m.addAttribute("lacc",lacc);


        }
        return "/index";
    }


    @GetMapping("/all")
    public String all(Model m) throws ParseException, SparqlException, IOException {
        QueryBuild build = new QueryBuild();
        build.select("?subject", "?predi","?val")
        .where("?subject ?predi ?val").limit(200);
        SparqlConn sparqlConn = SparqlConn.getInstance();
        RDFConnection conn = sparqlConn.getConn();
        QueryExecution query = conn.query(build.toString());
        //m.addAttribute("jsonld", query.execJson());
        ResultSet rs = query.execSelect() ;
        List<AllResource> lacc = new ArrayList<>();
        while(rs.hasNext()) {
            QuerySolution qs = rs.next() ;
            AllResource acc = new AllResource();
            acc.setSubject(qs.getResource("subject").toString());
            //acc.setPredi(qs.getLiteral("predi").toString());
            //acc.setVal(qs.getLiteral("val").getString());
            lacc.add(acc);

        }
        m.addAttribute("all",lacc);
        return "/all";
    }

    @RequestMapping("insert")
    public String insert(Model m, @ModelAttribute("accident") AccidentIn accident, HttpServletRequest request) throws ParseException, SparqlException, IOException {
        m.addAttribute("lum", caracInsertor.getLumDico());
        m.addAttribute("agg", caracInsertor.getAggDico());
        m.addAttribute("atm", caracInsertor.getAtmDico());
        m.addAttribute("col", caracInsertor.getColDico());
        m.addAttribute("inter", caracInsertor.getIntDico());

        if(request.getMethod().equals("POST")){
            Insert insert = new Insert(accident);
            System.out.println(accident.getDate().getDay());
        }

        return "/insert";
    }


}
