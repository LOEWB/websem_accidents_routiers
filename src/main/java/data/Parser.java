package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private String path;
    private Dataset dataset;


    public Parser(){
    this.dataset = new Dataset();
    }

    public Parser(String path){
        this();
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void parseWithHeader(String delimiter) throws IOException {
        List<List<String>> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(this.path));
        String line;
        this.dataset.setHeaders(Arrays.asList(br.readLine().replaceAll("\"","").split(delimiter)));
        while((line = br.readLine()) !=null) {
            lines.add(Arrays.asList(line.replaceAll("\"","").split(delimiter)));
        }
        this.dataset.setData(lines);

    }
}
