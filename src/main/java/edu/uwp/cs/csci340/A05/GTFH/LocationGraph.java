package edu.uwp.cs.csci340.A05.GTFH;

import java.util.*;

/**
 * The {@code LocationGraph} class represents a graph of airports and the direct paths between them,
 * using latitude and longitude to compute distances. It supports adding locations, connecting them,
 * and computing the shortest path using Dijkstra's algorithm.
 *
 * The graph is undirected and uses a map-based adjacency list.
 *
 * Dependencies:
 * - {@code AirportDistanceCalculator} must provide methods to geocode cities and calculate distances.
 *
 * Note: Airport codes (IATA) are used as unique node identifiers.
 *
 * Example:
 * <pre>{@code
 * LocationGraph graph = new LocationGraph();
 * graph.addLocation("JFK", "New York");
 * graph.addLocation("LAX", "Los Angeles");
 * graph.addPath("JFK", "LAX");
 * List<String> path = graph.shortestPath("JFK", "LAX");
 * }</pre>
 *
 */
public class LocationGraph {
    private Map<Node, List<Edge>> adjList = new HashMap<>();
    private Map<String, Node> airportMap = new HashMap<>();

    /**
     * Adds a new location (node) to the graph.
     *
     * @param airport  the airport code (e.g., "JFK")
     * @param location the city or region associated with the airport
     */
    public void addLocation(String airport, String location){
        Node node = new Node(airport, location);
        adjList.put(node, new ArrayList<Edge>());
        airportMap.put(airport, node);
    }

    /**
     * Retrieves the city or region for a given airport code.
     *
     * @param airport the airport code
     * @return the location name, or {@code null} if the airport is not found
     */
    public String getLocationByAirport(String airport) {
        Node node = airportMap.get(airport);
        return (node != null) ? node.getLocation() : null;
    }

    /**
     * Adds a bi-directional path between two airports based on their geocoded distance.
     *
     * @param from the source airport code
     * @param to   the destination airport code
     */
    public void addPath(String from, String to){
        String location1 = getLocationByAirport(from);
        String location2 = getLocationByAirport(to);
        AirportDistanceCalculator adc = new AirportDistanceCalculator();
        double[] aLatLong = adc.geocodeCity(location1);
        double[] bLatLong = adc.geocodeCity(location2);
        int distance = (int)adc.getDistance(aLatLong, bLatLong);

        adjList.get(airportMap.get(from)).add(new Edge(distance, to, from));
        adjList.get(airportMap.get(to)).add(new Edge(distance, from, to));
    }

    /**
     * Computes the shortest path between two airports using Dijkstra's algorithm.
     *
     * @param start the starting airport code
     * @param end   the destination airport code
     * @return a list of airport codes forming the shortest path, or {@code null} if no path exists
     */
    public List<String> shortestPath(String start, String end) {
        if (!airportMap.containsKey(start) || !airportMap.containsKey(end)) {
            return null; // Invalid airport codes
        }

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> predecessor = new HashMap<>();
        Set<String> visited = new HashSet<>();

        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt((Pair p) -> p.distance));        pq.add(new Pair(start, 0));
        distance.put(start, 0);

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            String currentAirport = current.node;

            if (!visited.add(currentAirport)) continue; // Already visited

            if (currentAirport.equals(end)) break;

            Node currentNode = airportMap.get(currentAirport);

            for (Edge edge : adjList.get(currentNode)) {
                String neighborAirport = edge.getPlace1();
                int newDist = distance.get(currentAirport) + edge.getDistance();

                if (!distance.containsKey(neighborAirport) || newDist < distance.get(neighborAirport)) {
                    distance.put(neighborAirport, newDist);
                    predecessor.put(neighborAirport, currentAirport);
                    pq.add(new Pair(neighborAirport, newDist));
                }
            }
        }

        // Reconstruct path
        List<String> path = new LinkedList<>();
        if (!predecessor.containsKey(end) && !start.equals(end)) return null;

        String at = end;
        while (at != null) {
            path.add(0, at);
            at = predecessor.get(at);
        }

        return path;
    }

    /**
     * Returns a string representation of the graph, listing each node and its edges.
     *
     * @return a string showing all airport connections and distances
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : adjList.keySet()) {
            sb.append(node.getAirport()).append(" (").append(node.getLocation()).append(") -> ");
            for (Edge edge : adjList.get(node)) {
                sb.append(edge.getPlace1())
                        .append(" (").append(edge.getDistance()).append(" miles), ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Represents a node (airport) in the graph.
     */
    private class Node{
        String airport;
        String location;
        Node(String key, String value){
            this.airport = key;
            this.location = value;
        }
        public void setAirport(String airport){
            this.airport = airport;
        }
        public String getAirport(){
            return airport;
        }
        public void setLocation(String val){
            this.location = val;
        }
        public String getLocation(){
            return location;
        }
    }

    /**
     * Represents a weighted edge between two nodes (airports).
     */
    private class Edge{
        int distance;
        String from;
        String to;
        Edge(int distance, String place1, String place2){
            this.distance = distance;
            this.from = place1;
            this.to = place2;
        }
        public void setDistance(int distance){
            this.distance = distance;
        }
        public void setPlace1(String place1){
            this.from = place1;
        }
        public void setPlace2(String place2){
            this.to = place2;
        }
        public int getDistance(){
            return distance;
        }
        public String getPlace1(){
            return from;
        }
        public String getPlace2(){
            return to;
        }

    }

    /**
     * Represents a pair of airport code and distance used in the priority queue for Dijkstra's algorithm.
     */
    private class Pair {
        String node;
        int distance;

        Pair(String node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
