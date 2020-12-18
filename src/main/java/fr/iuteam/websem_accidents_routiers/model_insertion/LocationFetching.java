package fr.iuteam.websem_accidents_routiers.model_insertion;

import com.google.gson.*;
import fr.iuteam.websem_accidents_routiers.entity.Location;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class LocationFetching {

    private final static String api = "https://api-adresse.data.gouv.fr/";

    public LocationFetching() {
    }

    public Location fetchByLongLat(String lon, String lat) throws IOException, InterruptedException, ParseException {
        lon = lon.replaceAll(",", ".");
        lat = lat.replaceAll(",", ".");
        String path = api + "reverse/?lon=" + lon.trim() + "&lat=" + lat.trim();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(path)).GET().build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.headers().toString());
        //Parsing
//        System.out.println(response.body().toString());
        JsonElement jsonRoot = JsonParser.parseString(response.body().toString());
        JsonObject o = jsonRoot.getAsJsonObject();
        if(o.getAsJsonArray("features").size() != 0) {
            JsonObject features = o.getAsJsonArray("features").get(0).getAsJsonObject();
            JsonObject properties = features.get("properties").getAsJsonObject();
            String citycode = properties.get("citycode").getAsString();
            String postcode = properties.get("postcode").getAsString();
            String city = properties.get("city").getAsString();
            String address = properties.get("name").getAsString();

            return new Location(citycode, city, address, postcode);
        }

        return null;
    }


}
