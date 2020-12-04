package model_insertion;

import data.Dataset;
import data.Parser;
import org.apache.jena.rdf.model.Model;

public abstract class AbstractInsertor {

    protected Parser parser;
    protected Model model;

    public AbstractInsertor(Parser parser, Model model) {
        this.parser = parser;
        this.model = model;
    }

    abstract void insert();


}
