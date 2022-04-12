/*
Name    : Pramuditha Sahas
IIT Id  : 20201214
UOW Id  : w1810601
*/

import java.util.*;

public class Graph {

    private final Map<Vertex, List<Vertex>> adjVertices = new HashMap<>();

    public void addVertex(Integer id) {
        adjVertices.putIfAbsent(new Vertex(id), new ArrayList<>());
    }

    public void removeVertex(Integer id) {
        Vertex Vertex = new Vertex(id);

        adjVertices.values().forEach(e -> e.remove(Vertex));
        adjVertices.remove(new Vertex(id));
    }

    public void addEdge(Integer id1, Integer id2) {
        Vertex v1 = new Vertex(id1);
        Vertex v2 = new Vertex(id2);

        adjVertices.get(v1).add(v2);
        adjVertices.get(v1).add(v1);
    }

    public void removeEdge(Integer id1, Integer id2) {
        Vertex v1 = new Vertex(id1);
        Vertex v2 = new Vertex(id2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);

        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }


    public List<Integer> BreadthFirstSearch(Integer start, Integer end) {
        try {
            Queue<Integer> queue = new LinkedList<>();
            List<Integer> visited = new ArrayList<>();
            Map<Integer, Integer> vertexMap = new HashMap<>();

            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                Integer vertexId = queue.poll();
                List<Vertex> vertexList = getAdjVertices(vertexId);
                for (Vertex vertex : vertexList) {
                    if (!visited.contains(vertex.getId())) {
                        vertexMap.put(vertex.getId(), vertexId);
                        visited.add(vertex.getId());
                        queue.add(vertex.getId());
                    }
                }
            }

            List<Integer> shortestPath = new ArrayList<>();
            Integer tempEnd = end;

            while (tempEnd != null) {
                shortestPath.add(tempEnd);
                tempEnd = vertexMap.get(tempEnd);
            }
            Collections.reverse(shortestPath);
            return shortestPath;
        } catch (Exception ex) {
            System.out.println("An Error occurred while Breadth First Search: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Vertex> getAdjVertices(Integer id) {
        return adjVertices.get(new Vertex(id));
    }
}
