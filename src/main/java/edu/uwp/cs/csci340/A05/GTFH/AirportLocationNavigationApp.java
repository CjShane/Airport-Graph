package edu.uwp.cs.csci340.A05.GTFH;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AirportLocationNavigationApp} class represents an application that constructs
 * a location graph of airports and their direct routes based on data from two input files:
 * <ul>
 *   <li><b>airports.txt</b> – contains airport codes and their corresponding locations</li>
 *   <li><b>routes.txt</b> – contains direct routes between airports</li>
 * </ul>
 * The graph is built using a {@link LocationGraph} object and printed to standard output.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * java AirportLocationNavigationApp
 * }</pre>
 *
 * @author Cj Shane
 */
public class AirportLocationNavigationApp {
    /**
     * A static {@link LocationGraph} instance that holds the graph of airport nodes and routes.
     */
    public static LocationGraph lg = new LocationGraph();

    /**
     * The main entry point of the application. It initializes the graph by reading
     * from the airports and routes files, then prints the resulting graph structure.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an I/O error occurs during file reading
     */
    public static void main(String[] args) throws IOException {
        airportList();
        routeList();
        System.out.println(lg.toString());
    }

    /**
     * Reads airport data from {@code airports.txt}, adds each airport to the location graph,
     * and maps its airport code to its location.
     *
     * <p>Expected format per line in the file: {@code Name, City, Country, IATA_Code}</p>
     *
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void airportList() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("airports.txt"));
        try {
            String line = br.readLine();
            ArrayList<String[]> airports = new ArrayList<>();

            do {
                String[] ap = line.split(", ");
                airports.add(ap);
                lg.addLocation(ap[3], ap[2]);
                line = br.readLine();
            }while (line != null);

        } finally {
            br.close();
        }
    }

    /**
     * Reads route data from {@code routes.txt}, adds each route as an edge between airports
     * in the location graph.
     *
     * <p>Expected format per line in the file: {@code Source_IATA, Destination_IATA}</p>
     *
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void routeList() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("routes.txt"));
        try {
            String line = br.readLine();
            ArrayList<String[]> routes = new ArrayList<>();

            do {
                String[] route = line.split(", ");
                routes.add(route);
                lg.addPath(route[0], route[1]);
                line = br.readLine();
            }while (line != null);

        } finally {
            br.close();
        }
    }
}
