package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * Class inserting caracteristiques-2019.csv in the model
 */
public class CaracInsertor extends AbstractInsertor {
    public CaracInsertor(Parser parser, Model model) {
        super(parser, model);
    }

    void headerProcess() {

    }

    public void insert() {
        Dataset dataset = parser.getDataset();
        dataset.getData().forEach(accident -> {
            Resource accidentRoutier = model.createResource("http://example.org/" + accident.get(dataset.getHeaders().indexOf("Num_Acc")));

//            Property dayProp = model.createProperty("https://www.wikidata.org/wiki/Property:P837");
            Property dayProp = model.createProperty("http://www.exemple.org/jour_du_mois");
            Property monthProp = model.createProperty("http://www.exemple.org/mois");
            Property timeProp = model.createProperty("http://www.exemple.org/heure");
            Property lightProp = model.createProperty("http://www.exemple.org/lumiere");
            Property depProp = model.createProperty("https://www.wikidata.org/wiki/Property:P131");

            accidentRoutier.addProperty(dayProp,
                    accident.get(dataset.getHeaders().indexOf("jour")),
                    XSDDatatype.XSDdecimal);
            accidentRoutier.addProperty(monthProp,
                    accident.get(dataset.getHeaders().indexOf("mois")),
                    XSDDatatype.XSDdecimal);
            accidentRoutier.addProperty(timeProp,
                    accident.get(dataset.getHeaders().indexOf("hrmn")),
                    XSDDatatype.XSDtime);
            accidentRoutier.addProperty(lightProp,
                    accident.get(dataset.getHeaders().indexOf("lum")),
                    XSDDatatype.XSDdecimal);
            accidentRoutier.addProperty(depProp,
                    accident.get(dataset.getHeaders().indexOf("dep")),
                    XSDDatatype.XSDdecimal);

            model.write(System.out, "Turtle");

        });




        model.createProperty("a");
    }

}