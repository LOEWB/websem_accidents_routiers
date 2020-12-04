package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import java.util.HashMap;

/**
 * Class inserting caracteristiques-2019.csv in the model
 */
public class CaracInsertor extends AbstractInsertor {

    private HashMap<String, Integer> headersDico = new HashMap<>();
    private HashMap<Integer, String> lumDico = new HashMap<>();
    private HashMap<Integer, String> aggDico = new HashMap<>();
    private HashMap<Integer, String> intDico = new HashMap<>();
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
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(depProp,
                    accident.get(headersDico.get("dep")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(commProp,
                    accident.get(headersDico.get("com")),
                    XSDDatatype.XSDdecimal);
            accidentRoutierEvent.addProperty(aggProp,
                    aggDico.get(Integer.valueOf(accident.get(headersDico.get("agg")))),
                    XSDDatatype.XSDstring);

        });

        model.write(System.out, "Turtle");

    }
}
