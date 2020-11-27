package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.rdf.model.Model;

public class CaracInsertor extends AbstractInsertor {
    public CaracInsertor(Dataset dataset, Parser parser, Model model) {
        super(dataset, parser, model);
    }

    void headerProcess() {
        model.createProperty("a");
    }

}
