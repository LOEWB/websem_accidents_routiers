import data.Parser;

import java.io.IOException;

public class MainApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Parser p = new Parser("src/main/resources/caracteristiques-2019.csv");
        p.parseWithHeader(";");
        System.out.println(p.getDataset().getData().get(0));
        System.out.println(p.getDataset().getData().get(1));
        System.out.println(p.getDataset().getData().get(2));
        System.out.println(p.getDataset().getHeaders());

        String st = "656323.5";
        System.out.println(Double.parseDouble(st));
        //p.getDataset().getData().stream().map( m -> m.get(0)).forEach(System.out::println);
        //p.getDataset().getData().forEach( d -> System.out.println(d));
        p.getDataset().getColumn("long").filter( v -> Double.parseDouble(v.replaceAll(",",".")) < 0).forEach(System.out::println);


    }
}
