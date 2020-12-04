import data.Parser;
import model_insertion.CaracInsertor;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApplication {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = MainApplication.class.getClassLoader();
        URL url = classLoader.getResource("caracteristiques-2019.csv");
        assert url != null: "Lien du fichier introuvable";
        Parser p = new Parser(url.getPath());
        p.parseWithHeader(";");

        Model model = ModelFactory.createDefaultModel();
        CaracInsertor caracInsertor = new CaracInsertor(p, model);
        caracInsertor.insert();
    }
}
