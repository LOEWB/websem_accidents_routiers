package fr.iuteam.websem_accidents_routiers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import org.apache.jena.atlas.json.JsonValue;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.io.IOException;
import java.io.StringWriter;
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
            build.select("?subject", "?lum","?vlum", "?value","?atm", "?vatm", "?agg", "?vagg","?col", "?vcol", "?i","?vi")
                    .where("?subject ?lum ?vlum")
                    .where("?subject ?atm ?vatm")
                    .where("?subject ?agg ?vagg")
                    .where("?subject ?col ?vcol")
                    .where("?subject ?i ?vi");


            build.filter("?atm = <http://www.exemple.org/conditions_atmo>").and()
                .filter("?agg = <http://www.exemple.org/en_agglo_ou_hors_agglo>").and()
                .filter("?col = <http://www.exemple.org/type_collision>").and()
                .filter("?i = <http://www.exemple.org/intersection>").and()
                .filter("?lum = <http://www.exemple.org/lumiere>");
            if(!accident.getLuminosity().isEmpty()){
                build.and();
                accident.Qb2FilterList(build,accident.getLuminosity(),caracInsertor.getLumDico(),"?vlum");
            }
            if(!accident.getAtm().isEmpty()){
                build.and();
                accident.Qb2FilterList(build,accident.getAtm(),caracInsertor.getAtmDico(), "?vatm");
            }
            if(!accident.getAgglo().isEmpty()){
                build.and();
                accident.Qb2FilterList(build,accident.getAgglo(),caracInsertor.getAggDico(), "?vagg");
            }
            if(!accident.getNbCol().isEmpty()){
                build.and();
                accident.Qb2FilterList(build,accident.getNbCol(),caracInsertor.getColDico(), "?vcol");
            }
            if(!accident.getIntersection().isEmpty()){
                build.and();
                accident.Qb2FilterList(build,accident.getIntersection(),caracInsertor.getIntDico(), "?vi");
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
                acc.setId(qs.getResource("subject").toString());
                acc.setLum(qs.getLiteral("vlum").toString());
                acc.setAtm(qs.getLiteral("vatm").toString()) ;
                acc.setAgg(qs.getLiteral("vagg").toString()) ;
                acc.setCol(qs.getLiteral("vcol").toString()) ;
                acc.setInter(qs.getLiteral("vi").toString());
                //System.out.println(qs.getLiteral("vinter").getValue().toString());
                //acc.setInter() ;
                lacc.add(acc);
            }
            m.addAttribute("lacc",lacc);


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.registerModule(new JsonldModule(() -> objectMapper.createObjectNode()));
            String personJsonLd = objectMapper.writer().writeValueAsString(JsonldResource.Builder.create().build(lacc));
            m.addAttribute("json", personJsonLd);

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
