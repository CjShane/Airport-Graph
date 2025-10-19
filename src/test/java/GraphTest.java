import edu.uwp.cs.csci340.A05.GTFH.LocationGraph;
import org.junit.Test;

import java.util.List;

public class GraphTest {
    @Test
    public void testGraph() {
        LocationGraph graph = new LocationGraph();
        graph.addLocation("ATL", "Hartsfield-Jackson Atlanta International Airport");
        graph.addLocation("MCO", "Orlando International Airport");
        graph.addLocation("LGA", "LaGuardia Airport");
        graph.addLocation("CLT", "Charlotte Douglas International Airport");
        graph.addLocation("ORD", "O'Hare International Airport");
        graph.addPath("ATL", "MCO");
        graph.addPath("ATL", "ORD");
        graph.addPath("LGA", "ORD");
        graph.addPath("LGA", "CLT");
        graph.addPath("ATL", "CLT");

        List<String> path = graph.shortestPath("ORD", "MCO");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }
}
