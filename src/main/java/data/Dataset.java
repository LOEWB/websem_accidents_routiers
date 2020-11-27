package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dataset {

    private List<List<String>> data = new ArrayList<>();
    private List<String> headers;

    public Dataset(List<List<String>> data, List<String> headers) {
        this.data = data;
        this.headers = headers;
    }
    public Dataset(){
    }

    public List<List<String>> getData() {
        return this.data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Stream<String> getColumn(int col) {
        return this.data.stream().map( m -> m.get(col));
    }
    public Stream<String> getColumn(String col) {
        return this.getColumn(this.getHeaderColumn(col));
    }

    private int getHeaderColumn(String col){
        return this.headers.indexOf(col);
    }


}
