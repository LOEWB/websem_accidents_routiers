package fr.iuteam.websem_accidents_routiers;


import fr.iuteam.websem_accidents_routiers.data.Parser;
import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApplication {

    public static void main(String[] args) throws IOException {

        /*ClassLoader classLoader = MainApplication.class.getClassLoader();
        URL url = classLoader.getResource("caracteristiques-2019.csv");
        assert url != null: "Lien du fichier introuvable";
        Parser p = new Parser(url.getPath());
        p.parseWithHeader(";");

        Model model = ModelFactory.createDefaultModel();
        CaracInsertor caracInsertor = new CaracInsertor(p, model);
        caracInsertor.insert();*/


    }

    public void test (){
        String datasetURL = "http://localhost:3030/test2";
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conn = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        QueryExecution qExec = conn.query("SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}");
        ResultSet rs = qExec.execSelect() ;
        while(rs.hasNext()) {
            QuerySolution qs = rs.next() ;
            Resource subject = qs.getResource("subject") ;
            System.out.println("Subject: "+qs) ;
        }
    }
}
