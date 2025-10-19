import edu.uwp.cs.csci340.A05.GTFH.AirportDistanceCalculator;
import org.junit.Test;

public class DistanceCheck {
    @Test
    public void test() {
        AirportDistanceCalculator adc = new AirportDistanceCalculator();
        double[] cLatLong = adc.geocodeCity("Chicago");
        double[] gLatLong = adc.geocodeCity("Greensboro, North Carolina");
        System.out.println(adc.getDistance(cLatLong, gLatLong));
    }
}
