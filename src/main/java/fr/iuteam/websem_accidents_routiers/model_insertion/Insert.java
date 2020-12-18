package fr.iuteam.websem_accidents_routiers.model_insertion;

import fr.iuteam.websem_accidents_routiers.entity.AccidentIn;
import fr.iuteam.websem_accidents_routiers.entity.Location;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlConn;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlException;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Insert {

    private Model model;
    public Insert(AccidentIn accident) throws ParseException, SparqlException, IOException {
        this.model = ModelFactory.createDefaultModel();
        String id = UUID.randomUUID().toString();
        Resource accidentRoutierEvent = model.createResource("http://www.example.org/" + accident.getId());
        Resource accidentRoutier = model.createResource("http://www.example.org/accident_de_la_route");

        Property aProp = model.createProperty("a");
        Property dayProp = model.createProperty("http://www.example.org/jour_du_mois");
        Property monthProp = model.createProperty("http://www.example.org/mois");
        Property timeProp = model.createProperty("http://www.example.org/heure");
        Property lightProp = model.createProperty("http://www.example.org/lumiere");
        Property depProp = model.createProperty("https://www.wikidata.org/wiki/Property:P131");
        Property commProp = model.createProperty("https://www.wikidata.org/wiki/Property:P131");
        Property aggProp = model.createProperty("http://www.example.org/en_agglo_ou_hors_agglo");
        Property intProp = model.createProperty("http://www.example.org/intersection");
        Property atmProp = model.createProperty("http://www.example.org/conditions_atmo");
        Property colProp = model.createProperty("http://www.example.org/type_collision");
        Property adrProp = model.createProperty("http://www.example.org/adr_postale");
        Property latProp = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
        Property longProp = model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
        Property locationIdProp = model.createProperty("http://www.example.org/location");


        String lat = accident.getLat();
        String lon = accident.getLon();
        String comCode = accident.getCom();

        accidentRoutierEvent.addProperty(aProp, accidentRoutier);
        accidentRoutierEvent.addProperty(dayProp,String.valueOf(accident.getDate().getDay() ), XSDDatatype.XSDdecimal);
        accidentRoutierEvent.addProperty(monthProp, String.valueOf(accident.getDate().getHours()), XSDDatatype.XSDdecimal);
        //accidentRoutierEvent.addProperty(timeProp, accident.get(headersDico.get("hrmn")), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(lightProp, accident.getLuminosity(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(depProp, accident.getDep(), XSDDatatype.XSDdecimal);
        accidentRoutierEvent.addProperty(commProp, comCode, XSDDatatype.XSDdecimal);
        accidentRoutierEvent.addProperty(aggProp, accident.getAgg(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(intProp,accident.getInter(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(atmProp, accident.getAtm(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(colProp, accident.getCol(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(adrProp, accident.getAdr(), XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(latProp, lat, XSDDatatype.XSDstring);
        accidentRoutierEvent.addProperty(longProp, lon, XSDDatatype.XSDstring);

        Resource accidentLocation = model.createResource("http://www.example.org/location/" + id);
        accidentRoutierEvent.addProperty(locationIdProp, accidentLocation);


        Resource locationInstance = model.createResource("http://www.example.org/location/" + id);
        Property commProp2 = model.createProperty("https://www.wikidata.org/wiki/Property:P131");
        Property cityProp = model.createProperty("http://www.example.org/commune/");
        Property addressProp = model.createProperty("http://www.example.org/adresse/");
        Property postCodeProp = model.createProperty("http://www.example.org/code_postal/");

        Resource location = model.createResource("http://www.example.org/location");



        locationInstance.addProperty(aProp, location);
        locationInstance.addProperty(commProp2, comCode, XSDDatatype.XSDstring);

        locationInstance.addProperty(cityProp, accident.getCity(), XSDDatatype.XSDstring);
        locationInstance.addProperty(addressProp, accident.getAdr(), XSDDatatype.XSDstring);
        locationInstance.addProperty(postCodeProp, accident.getCp(), XSDDatatype.XSDstring);
        model.write(System.out, "Turtle");
        RDFConnection conn = SparqlConn.getInstance().getConn();
        conn.load(model); // add the content of model to the triplestore
        conn.update("INSERT DATA { <test> a <TestClass> }");

    }
}
