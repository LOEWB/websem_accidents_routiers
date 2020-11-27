package dataProcess;

import java.io.Serializable;
import java.util.List;

public class Dataset {

    private List<List<Object>> data;
    private List<Object> headers;

    public Dataset(List<List<Object>> data, List<Object> headers) {
        this.data = data;
        this.headers = headers;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public List<Object> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }
}
