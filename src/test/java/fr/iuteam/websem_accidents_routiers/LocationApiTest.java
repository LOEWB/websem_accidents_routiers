package fr.iuteam.websem_accidents_routiers;

import fr.iuteam.websem_accidents_routiers.entity.Location;
import fr.iuteam.websem_accidents_routiers.model_insertion.LocationFetching;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class LocationApiTest {

    @Test
    void testOneCorrectLocationFetch() {
        String lon = "2,4701200";
        String lat = "48,8962100";
        LocationFetching fetching = new LocationFetching();

        try {
            Location loc = fetching.fetchByLongLat(lon, lat);
            assert(loc.getCity().equals("Noisy-le-Sec"));

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
