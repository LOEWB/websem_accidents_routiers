package fr.iuteam.websem_accidents_routiers.model_insertion;


import fr.iuteam.websem_accidents_routiers.data.Parser;
import fr.iuteam.websem_accidents_routiers.sparql.SparqlException;
import org.apache.jena.rdf.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class AbstractInsertor {

    protected Parser parser;
    protected Model model;

    public AbstractInsertor(Parser parser, Model model) {
        this.parser = parser;
        this.model = model;
    }

    public AbstractInsertor() {

    }

    abstract void insert() throws ParseException, SparqlException, IOException;


}
