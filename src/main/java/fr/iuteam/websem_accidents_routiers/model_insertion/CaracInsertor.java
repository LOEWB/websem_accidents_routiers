package fr.iuteam.websem_accidents_routiers.model_insertion;

import fr.iuteam.websem_accidents_routiers.data.Dataset;
import fr.iuteam.websem_accidents_routiers.data.Parser;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class inserting caracteristiques-2019.csv in the model
 */
public class CaracInsertor extends AbstractInsertor {

    private Map<String, Integer> headersDico = new HashMap<>();
    private Map<Integer, String> lumDico = new HashMap<>();
    private Map<Integer, String> aggDico = new HashMap<>();
    private Map<Integer, String> intDico = new HashMap<>();
    private Map<Integer, String> atmDico = new HashMap<>();
    private Map<Integer, String> colDico = new HashMap<>();

    public CaracInsertor(Parser parser, Model model) {
        super(parser, model);
        initDicos();
    }

    void initDicos() {
        this.headersDico = Stream.of(new Object[][] {
                {"jour",1}, {"mois",2}, {"hrmn", 4}, {"lum", 5}, {"dep",6},
                {"com",7},{"agg",8}, {"int",9},{ "atm",10},{"col",11},
                {"adr",12},{ "lat",13},{ "long",14}
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        this.lumDico = Stream.of(new Object[][] {
                {1, "Plein jour"}, {2, "Crépuscule ou aube"}, {3, "Nuit sans éclairage"},
                {4, "Nuit avec éclairage public non allumé"}, {5, "Nuit avec éclairage public allumé"}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

        this.aggDico.put(1, "Hors agglomération");
        this.aggDico.put(2, "En agglomération");

        this.intDico = Stream.of(new Object[][] {
                {1,  "Hors intersection"}, {2, "Intersection en X"}, {3,"Intersection en T"},
                {4, "Intersection en Y"}, {5, "Intersection à plus de 4 branches"},
                {6, "Giratoire"},{7, "Place"}, {8, "Passage à niveau"},{9, "Autre intersection"}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));


        this.colDico = Stream.of(new Object[][]{
            {-1, "Non renseigné"}, {1, "Deux véhicules - frontale"}, {2, "Deux véhicules - par l'arrière"},
            {3, "Deux véhicules - par le côté"}, {4, "Trois véhicules et plus - en chaîne"},
            {5, "Trois véhicules et plus - collisions multiples"}, {6, "Autre collision"}, {7, "Sans collision"}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

        this.atmDico =  Stream.of(new Object[][]{
                {-1, "Non renseigné"}, {1, "Normale"}, {2, "Pluie légère"}, {3, "Pluie forte"}, {4, "Neige - grêle"},
                {5, "Brouillard - fumée"}, {6, "Vent fort - tempête"}, {7, "Temps éblouissant"},
                {8, "Temps couvert"}, {9, "Autre"}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
    }

    public void insert() {
        Dataset dataset = parser.getDataset();

        dataset.getData().forEach(accident -> {
            Resource accidentRoutierEvent = model.createResource("http://example.org/" + accident.get(dataset.getHeaders().indexOf("Num_Acc")));
            Resource accidentRoutier = model.createResource("http://example.org/accident_de_la_route");

            Property aProp = model.createProperty("a");
//            Property dayProp = model.createProperty("https://www.wikidata.org/wiki/Property:P837");
            Property dayProp = model.createProperty("http://www.exemple.org/jour_du_mois");
            Property monthProp = model.createProperty("http://www.exemple.org/mois");
            Property timeProp = model.createProperty("http://www.exemple.org/heure");
            Property lightProp = model.createProperty("http://www.exemple.org/lumiere");
            Property depProp = model.createProperty("https://www.wikidata.org/wiki/Property:P131");
            Property commProp = model.createProperty("https://www.wikidata.org/wiki/Property:P131");
            Property aggProp = model.createProperty("http://www.exemple.org/en_agglo_ou_hors_agglo");
            Property intProp = model.createProperty("http://www.exemple.org/intersection");
            Property atmProp = model.createProperty("http://www.exemple.org/conditions_atmo");
            Property colProp = model.createProperty("http://www.exemple.org/type_collision");
            Property adrProp = model.createProperty("http://www.exemple.org/adr_postale");
            Property latProp = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
            Property longProp = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");

            accidentRoutierEvent.addProperty(aProp, accidentRoutier);
            accidentRoutierEvent.addProperty(dayProp,
                    accident.get(headersDico.get("jour")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(monthProp,
                    accident.get(headersDico.get("mois")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(timeProp,
                    accident.get(headersDico.get("hrmn")),
                    XSDDatatype.XSDtime);
            accidentRoutierEvent.addProperty(lightProp,
                    lumDico.get(Integer.valueOf(accident.get(headersDico.get("lum")))),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(depProp,
                    accident.get(headersDico.get("dep")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(commProp,
                    accident.get(headersDico.get("com")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(aggProp,
                    aggDico.get(Integer.valueOf(accident.get(headersDico.get("agg")))),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(intProp,
                    intDico.get(Integer.valueOf(accident.get(headersDico.get("int")))),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(atmProp,
                    atmDico.get(Integer.valueOf(accident.get(headersDico.get("atm")).trim())),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(colProp,
                    colDico.get(Integer.valueOf(accident.get(headersDico.get("col")).trim())),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(adrProp,
                    accident.get(headersDico.get("adr")),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(latProp,
                    accident.get(headersDico.get("lat")),
                    XSDDatatype.XSDstring);
            accidentRoutierEvent.addProperty(longProp,
                    accident.get(headersDico.get("long")),
                    XSDDatatype.XSDstring);


        });
        insertData(model);
        model.write(System.out, "Turtle");
    }

    public static void insertData(Model model) {
        String datasetURL = "http://localhost:3030/accidents_routiers";
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        conneg.load(model); // add the content of model to the triplestore
        conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore

        /*String datasetURL = "http://localhost:3030/test2";
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conn = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        conn.load(model);
        conn.update("INSERT DATA { <test> a <TestClass> }");*/

    }

}
