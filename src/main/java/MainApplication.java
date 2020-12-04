
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import data.Parser;
import data.Parser2;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.modify.request.UpdateDataInsert;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainApplication {

    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Hello World");
        Parser p = new Parser("src/main/resources/caracteristiques-2019.csv");
        p.parseWithHeader(";");

        Parser2 p2 = new Parser2("src/main/resources/caracteristiques-2019.csv");
        p2.parseWithHeader(";");
        //p2.getDataset().getData().get(0).getData()



        //p.getDataset().getData().stream().map( m -> m.get(0)).forEach(System.out::println);
        //p.getDataset().getData().forEach( d -> System.out.println(d));
       // p.getDataset().getColumn("long").filter( v -> Double.parseDouble(v.replaceAll(",",".")) < 0).forEach(System.out::println);


       // p.getDataset().getColumn("long").forEach(System.out::println);
        /*Model model = ModelFactory.createDefaultModel();

        p.getDataset().getData().forEach( d -> {
            Resource resource = model.createResource("http://example.org/" +d.get(p.getDataset().getHeaders().indexOf("Num_Acc")));

            Property p1 = model.createProperty("http://www.example.org/date");
            //Property p2 = model.createProperty();
            String date = d.get(p.getDataset().getHeaderColumn("jour")) + "/" +d.get(p.getDataset().getHeaderColumn("mois")) + "/" + d.get(p.getDataset().getHeaderColumn("an"))
                    + " " + d.get(p.getDataset().getHeaderColumn("hrmn"));

            resource.addProperty(p1,date,XSDDatatype.XSDdateTime);






            //resource.addProperty(p1,date);
            //System.out.println(resource);

        });
        model.write(System.out,"Turtle");
*/
    }
}
