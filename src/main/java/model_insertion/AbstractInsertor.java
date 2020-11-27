package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.rdf.model.Model;

public abstract class AbstractInsertor {

    protected Dataset dataset;
    protected Parser parser;
    protected Model model;

    public AbstractInsertor(Dataset dataset, Parser parser, Model model) {
        this.dataset = dataset;
        this.parser = parser;
        this.model = model;
    }

    abstract void headerProcess();


}
