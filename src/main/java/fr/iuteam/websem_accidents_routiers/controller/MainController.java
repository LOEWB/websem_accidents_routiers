package fr.iuteam.websem_accidents_routiers.controller;



import fr.iuteam.websem_accidents_routiers.util.QueryBuild;
import fr.iuteam.websem_accidents_routiers.util.QueryBuilderException;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String index(Model m) throws QueryBuilderException {
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

        QueryBuild build = new QueryBuild();
        build.select("?subject","?predi","?x", "(count(*) as ?c)")
        .where("?subject ?predi ?x")
                .filter("?x > 5").and().filter("?x =10").or().filter("?x = 8").limit(5).orderBy("?x", "?r").limit(5, 2)
        .having("?x >5").where("?su ?x ?t").groupBy("?subject","?predi", "?x");
        System.out.println(build);

        return "/index";
    }

    @PostMapping("/accNo")
    public String noAcci(Model m){
        System.out.println("test");
        return "/index";
    }
    @PostMapping("/accDate")
    public String dateAcci(Model m){
        System.out.println("test2");
        return "/index";
    }
}
