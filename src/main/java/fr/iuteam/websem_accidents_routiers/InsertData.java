package fr.iuteam.websem_accidents_routiers;


import fr.iuteam.websem_accidents_routiers.data.Parser;
import fr.iuteam.websem_accidents_routiers.model_insertion.CaracInsertor;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlException;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URL;

public class InsertData {

    public static void main(String[] args) throws IOException, SparqlException, ParseException {

        ClassLoader classLoader = InsertData.class.getClassLoader();
        URL url = classLoader.getResource("caracteristiques-2019.csv");
        assert url != null: "Lien du fichier introuvable";
        Parser p = new Parser(url.getPath());
        p.parseWithHeader(";");

        Model model = ModelFactory.createDefaultModel();
        CaracInsertor caracInsertor = new CaracInsertor(p, model);
        caracInsertor.insert(100);

    }

}
