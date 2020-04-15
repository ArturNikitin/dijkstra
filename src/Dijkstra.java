import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();

        HashMap<String, Integer> startNeighbours = new HashMap<>();
        startNeighbours.put("A", 5);
        startNeighbours.put("B", 4);
        startNeighbours.put("C", 2);
        graph.put("start", startNeighbours);

        HashMap<String, Integer> aNeighbours = new HashMap<>();
        aNeighbours.put("F", 2);
        aNeighbours.put("E", 15);
        graph.put("A", aNeighbours);

        HashMap<String, Integer> bNeighbours = new HashMap<>();
        bNeighbours.put("E", 11);
        bNeighbours.put("D", 6);
        graph.put("B", bNeighbours);

        HashMap<String, Integer> cNeighbours = new HashMap<>();
        cNeighbours.put("B", 8);
        cNeighbours.put("D", 3);
        cNeighbours.put("F", 4);
        graph.put("C", cNeighbours);

        HashMap<String, Integer> eNeighbours = new HashMap<>();
        eNeighbours.put("W", 15);
        eNeighbours.put("Q", 7);
        graph.put("E", eNeighbours);

        HashMap<String, Integer> dNeighbours = new HashMap<>();
        dNeighbours.put("Q", 5);
        dNeighbours.put("P", 13);
        graph.put("D", dNeighbours);

        HashMap<String, Integer> fNeighbours = new HashMap<>();
        fNeighbours.put("K", 15);
        fNeighbours.put("P", 14);
        fNeighbours.put("W", 11);
        graph.put("F", fNeighbours);

        HashMap<String, Integer> wNeighbours = new HashMap<>();
        wNeighbours.put("S", 15);
        wNeighbours.put("fin", 4);
        graph.put("W", wNeighbours);

        HashMap<String, Integer> qNeighbours = new HashMap<>();
        qNeighbours.put("V", 13);
        qNeighbours.put("fin", 16);
        qNeighbours.put("W", 2);
        graph.put("Q", qNeighbours);

        HashMap<String, Integer> pNeighbours = new HashMap<>();
        pNeighbours.put("Q", 2);
        pNeighbours.put("fin", 17);
        graph.put("P", pNeighbours);

        HashMap<String, Integer> kNeighbours = new HashMap<>();
        kNeighbours.put("M", 2);
        kNeighbours.put("fin", 18);
        graph.put("K", kNeighbours);

        graph.put("S", new HashMap<>());

        graph.put("V", new HashMap<>());

        graph.put("M", new HashMap<>());

        graph.put("fin", new HashMap<>());

        System.out.println(dijkstra(graph, "start", "fin"));
    }

    public static int dijkstra(HashMap<String, HashMap<String, Integer>> graph, String start, String destination) {
        if (!graph.containsKey(start) || !graph.containsKey(destination)) {
            return -1;
        }
        Map<String, Integer> costs = graph.get(start);

        Map<String, String> parents = new HashMap<>();

        for (String child :
                graph.get(start).keySet()) {
            parents.put(child, start);
        }

        Set<String> processed = new HashSet<>();
        String node = findLowestNode(costs, processed);

        while (node != null) {
            int cost = costs.get(node);
            if (!graph.get(node).keySet().isEmpty()) {
                Set<String> neighbours = graph.get(node).keySet();
                for (String neighbour :
                        neighbours) {
                    int newCost = cost + graph.get(node).get(neighbour);

                    if (!costs.containsKey(neighbour)) {
                        costs.put(neighbour, newCost);
                        parents.put(neighbour, node);
                    } else {
                        if (newCost < costs.get(neighbour)) {
                            costs.put(neighbour, newCost);
                            parents.put(neighbour, node);
                        }
                    }
                }
            }
            processed.add(node);
            node = findLowestNode(costs, processed);
        }

        if (!costs.containsKey(destination)) {
            return -1;
        }

        List<String> criticalPath = new ArrayList<>();
        criticalPath.add(destination);
        while (true) {
            String child = criticalPath.get(criticalPath.size() - 1);
            if (parents.containsKey(child)) {
                criticalPath.add(parents.get(child));
            } else
                break;
        }

        Collections.reverse(criticalPath);
        criticalPath.forEach(System.out::println);
        return costs.get(destination);
    }

    public static String findLowestNode(Map<String, Integer> costs, Set<String> processed) {
        int lowest = Integer.MAX_VALUE;
        String node = null;
        for (Map.Entry<String, Integer> pair : costs.entrySet()) {
            if (pair.getValue() < lowest && !processed.contains(pair.getKey())) {
                node = pair.getKey();
                lowest = pair.getValue();
            }
        }
        return node;
    }
}
