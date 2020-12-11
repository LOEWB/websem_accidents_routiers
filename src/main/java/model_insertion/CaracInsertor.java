package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.util.HashMap;

/**
 * Class inserting caracteristiques-2019.csv in the model
 */
public class CaracInsertor extends AbstractInsertor {

    private HashMap<String, Integer> headersDico = new HashMap<>();
    private HashMap<Integer, String> lumDico = new HashMap<>();
    private HashMap<Integer, String> aggDico = new HashMap<>();
    private HashMap<Integer, String> intDico = new HashMap<>();
    private HashMap<Integer, String> atmDico = new HashMap<>();
    private HashMap<Integer, String> colDico = new HashMap<>();

    public CaracInsertor(Parser parser, Model model) {
        super(parser, model);
        initDicos();
    }

    void initDicos() {
        headersDico.put("jour", 1);
        headersDico.put("mois", 2);
        headersDico.put("hrmn", 4);
        headersDico.put("lum", 5);
        headersDico.put("dep", 6);
        headersDico.put("com", 7);
        headersDico.put("agg", 8);
        headersDico.put("int", 9);
        headersDico.put("atm", 10);
        headersDico.put("col", 11);
        headersDico.put("adr", 12);
        headersDico.put("lat", 13);
        headersDico.put("long", 14);

        lumDico.put(1, "Plein jour");
        lumDico.put(2, "Crépuscule ou aube");
        lumDico.put(3, "Nuit sans éclairage");
        lumDico.put(4, "Nuit avec éclairage public non allumé");
        lumDico.put(5, "Nuit avec éclairage public allumé");

        aggDico.put(1, "Hors agglomération");
        aggDico.put(2, "En agglomération");

        intDico.put(1, "Hors intersection");
        intDico.put(2, "Intersection en X");
        intDico.put(3, "Intersection en T");
        intDico.put(4, "Intersection en Y");
        intDico.put(5, "Intersection à plus de 4 branches");
        intDico.put(6, "Giratoire");
        intDico.put(7, "Place");
        intDico.put(8, "Passage à niveau");
        intDico.put(9, "Autre intersection");

        colDico.put(-1, "Non renseigné");
        colDico.put(1, "Deux véhicules - frontale");
        colDico.put(2, "Deux véhicules - par l'arrière");
        colDico.put(3, "Deux véhicules - par le côté");
        colDico.put(4, "Trois véhicules et plus - en chaîne");
        colDico.put(5, "Trois véhicules et plus - collisions multiples");
        colDico.put(6, "Autre collision");
        colDico.put(7, "Sans collision");

        atmDico.put(-1, "Non renseigné");
        atmDico.put(1, "Normale");
        atmDico.put(2, "Pluie légère");
        atmDico.put(3, "Pluie forte");
        atmDico.put(4, "Neige - grêle");
        atmDico.put(5, "Brouillard - fumée");
        atmDico.put(6, "Vent fort - tempête");
        atmDico.put(7, "Temps éblouissant");
        atmDico.put(8, "Temps couvert");
        atmDico.put(9, "Autre");
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

//        insertData(model);

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

    }

}
