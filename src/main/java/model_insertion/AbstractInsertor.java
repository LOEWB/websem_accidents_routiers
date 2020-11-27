package model_insertion;

import dataProcess.Dataset;
import dataProcess.Parsing;
import org.apache.jena.rdf.model.Model;

public abstract class AbstractInsertor {

    protected Dataset dataset;
    protected Parsing parser;
    protected Model model;

    public AbstractInsertor(Dataset dataset, Parsing parser, Model model) {
        this.dataset = dataset;
        this.parser = parser;
        this.model = model;
    }

    abstract void headerProcess();


}
