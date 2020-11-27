package model_insertion;

import dataProcess.Dataset;
import dataProcess.Parsing;
import model_insertion.AbstractInsertor;
import org.apache.jena.rdf.model.Model;

public class CaracInsertor extends AbstractInsertor {
    public CaracInsertor(Dataset dataset, Parsing parser, Model model) {
        super(dataset, parser, model);
    }

    void headerProcess() {
        model.createProperty("a");
    }

}
