package fr.iuteam.websem_accidents_routiers.sparql;


import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class SparqlConn implements Serializable {
        private SparqlConn()
        {
        }

        /** unique instance */
        private static SparqlConn INSTANCE = new SparqlConn();
        private String config = "src/main/resources/static/connexion.json";

        /** to get unique instance */
        public static SparqlConn getInstance()
        {   return INSTANCE;
        }

        /** security for deserialization */
        private Object readResolve() {
            return INSTANCE;
        }
        public RDFConnection getConn() throws IOException, ParseException, SparqlException {
            JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(new FileReader(this.config));
                JSONObject jsonObject =  (JSONObject) obj;

                String url = jsonObject.get("url")!=null ? (String) jsonObject.get("url") : "http://localhost" ;
                Long port  = jsonObject.get("port")!= null ?  (Long) jsonObject.get("port") : 3030;
                String endPoint =  jsonObject.get("endPoint")!= null ? (String) jsonObject.get("endPoint") : "sparql";

                if (jsonObject.get("dataset")==null ) {
                        throw new SparqlException("dataset unknown");
                }
                String dataset = (String) jsonObject.get("dataset");
                String update = jsonObject.get("update")!=null ? (String) jsonObject.get("update") : "update";
                String graph = jsonObject.get("graph")!=null ? (String) jsonObject.get("graph") : "data";

                String datasetURL =  url + ":" + port + "/" + dataset ;
                String sparqlEndpoint = datasetURL + "/" + endPoint;
                String sparqlUpdate = datasetURL + "/" + update;
                String graphStore = datasetURL + "/" + graph;
                RDFConnection conn = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
                return conn;
        }
}
