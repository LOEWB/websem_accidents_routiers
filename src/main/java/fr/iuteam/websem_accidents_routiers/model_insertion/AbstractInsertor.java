package fr.iuteam.websem_accidents_routiers.model_insertion;


import fr.iuteam.websem_accidents_routiers.data.Parser;
import org.apache.jena.rdf.model.Model;

public abstract class AbstractInsertor {

    protected Parser parser;
    protected Model model;

    public AbstractInsertor(Parser parser, Model model) {
        this.parser = parser;
        this.model = model;
    }

    abstract void headerProcess();


}
