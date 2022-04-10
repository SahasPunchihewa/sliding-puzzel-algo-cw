import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PathFinder {

    private final Map<Integer, Node> map = new HashMap<>();
    private Node start;
    private Node end;
    private List<List<Node>> nodeMatrix;
    private Integer height;
    private Integer width;

    public PathFinder() {
    }

    public void findPath() {

        try {
            readFile();
        } catch (Exception ex) {
            System.out.println("An error Occurred while reading the file: " + ex.getMessage());
            return;
        }

        height = nodeMatrix.size();
        width = nodeMatrix.get(0).size();

        Graph graph = createGraph();

        List<Integer> shortestPath = graph.BreadthFirstSearch(start.getId(), end.getId());

        printShortestPath(shortestPath);
    }

    private void readFile() throws Exception {

        Path path = Paths.get("D:\\IIT\\YR 2\\Algo\\examples\\maze30_5.txt");
        File txt = new File(String.valueOf(path));
        Scanner scanner = new Scanner(txt);
        nodeMatrix = new ArrayList<>();
        int lineNo = 0;
        int id = 0;

        while (scanner.hasNext()) {

            String line = scanner.nextLine();
            List<Node> lineList = new ArrayList<>();

            for (int i = 0; i < line.length(); i++) {

                char character = line.charAt(i);
                Node node = new Node(id, i, lineNo, character);

                if (character == 'S') {
                    start = node;
                }
                if (character == 'F') {
                    end = node;
                }
                lineList.add(node);
                map.put(node.getId(), node);

                id++;
            }
            nodeMatrix.add(lineList);
            lineNo++;
        }
        scanner.close();
    }


    private Graph createGraph() {

        Graph graph = new Graph();
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();

        Node currentPosition = start;
        stack.push(currentPosition.getId());
        graph.addVertex(currentPosition.getId());
        boolean pathPresent = false;

        while (!stack.isEmpty()) {
            Integer vertexId = stack.pop();
            visited.add(vertexId);

            currentPosition = map.get(vertexId);

            if (findEndOfPath(currentPosition)) {
                graph.addVertex(end.getId());
                graph.addEdge(vertexId, end.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                if (canGoUp(currentPosition)) {
                    Node newNode = goUp(currentPosition);
                    if (!visited.contains(newNode.getId())) {
                        stack.push(newNode.getId());
                        graph.addVertex(newNode.getId());
                        graph.addEdge(vertexId, newNode.getId());
                    }
                }
                if (canGoDown(currentPosition)) {
                    Node newNode = goDown(currentPosition);
                    if (!visited.contains(newNode.getId())) {
                        stack.push(newNode.getId());
                        graph.addVertex(newNode.getId());
                        graph.addEdge(vertexId, newNode.getId());
                    }
                }
                if (canGoLeft(currentPosition)) {
                    Node newNode = goLeft(currentPosition);
                    if (!visited.contains(newNode.getId())) {
                        stack.push(newNode.getId());
                        graph.addVertex(newNode.getId());
                        graph.addEdge(vertexId, newNode.getId());
                    }
                }
                if (canGoRight(currentPosition)) {
                    Node newNode = goRight(currentPosition);
                    if (!visited.contains(newNode.getId())) {
                        stack.push(newNode.getId());
                        graph.addVertex(newNode.getId());
                        graph.addEdge(vertexId, newNode.getId());
                    }
                }
            }
        }
        if (pathPresent) {
            return graph;
        } else {
            return null;
        }
    }

    private Node goUp(Node node) {
        while (canGoUp(node)) {
            node = nodeMatrix.get(node.getY() - 1).get(node.getX());
        }
        return node;
    }

    private Node goDown(Node node) {
        while (canGoDown(node)) {
            node = nodeMatrix.get(node.getY() + 1).get(node.getX());
        }
        return node;
    }

    private Node goLeft(Node node) {
        while (canGoLeft(node)) {
            node = nodeMatrix.get(node.getY()).get(node.getX() - 1);
        }
        return node;
    }

    private Node goRight(Node node) {
        while (canGoRight(node)) {
            node = nodeMatrix.get(node.getY()).get(node.getX() + 1);
        }
        return node;
    }

    private boolean canGoUp(Node node) {
        if (node.getY() == 0) {
            return false;
        } else {
            Node upNode = nodeMatrix.get(node.getY() - 1).get(node.getX());
            return !upNode.getLetter().equals('0');
        }
    }

    private boolean canGoDown(Node node) {
        if (node.getY() == height - 1) {
            return false;
        } else {
            Node downNode = nodeMatrix.get(node.getY() + 1).get(node.getX());
            return !downNode.getLetter().equals('0');
        }
    }

    private boolean canGoLeft(Node node) {
        if (node.getX() == 0) {
            return false;
        } else {
            Node leftNode = nodeMatrix.get(node.getY()).get(node.getX() - 1);
            return !leftNode.getLetter().equals('0');
        }
    }

    private boolean canGoRight(Node node) {
        if (node.getX() == width - 1) {
            return false;
        } else {
            Node rightNode = nodeMatrix.get(node.getY()).get(node.getX() + 1);
            return !rightNode.getLetter().equals('0');
        }
    }

    private boolean findEndOfPath(Node node) {

        if (Objects.equals(node.getId(), end.getId())) {
            return true;
        }
        if (!(Objects.equals(node.getX(), end.getX()) || Objects.equals(node.getY(), end.getY()))) {
            return false;
        }
        Integer startId;
        Integer endId;
        if (Objects.equals(node.getX(), end.getX())) {
            if (node.getY() < end.getY()) {
                startId = node.getY();
                endId = end.getY();
            } else {
                startId = end.getY();
                endId = node.getY();
            }
            List<Node> col = nodeMatrix.get(end.getX());
            for (int i = startId; i <= endId; i++) {
                Node verticalNode = col.get(i);
                if (verticalNode.getLetter().equals('0')) {
                    return false;
                }
            }
        } else {
            if (node.getX() < end.getX()) {
                startId = node.getX();
                endId = end.getX();
            } else {
                startId = end.getX();
                endId = node.getX();
            }
            List<Node> row = nodeMatrix.get(end.getY());
            for (int i = startId; i <= endId; i++) {
                Node horizontalNode = row.get(i);
                if (horizontalNode.getLetter().equals('0')) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printShortestPath(List<Integer> shortestPathList) {

        if (shortestPathList.isEmpty()) {
            System.out.println("No path available!");
        } else {
            for (Integer vertexId : shortestPathList) {

                int currentIndex = shortestPathList.indexOf(vertexId);
                Node currentNode = map.get(vertexId);

                if (currentNode == start) {
                    System.out.println(currentIndex + ". Start at (" + currentNode.getX() + "," + currentNode.getY() + ")");
                }

                if (!currentNode.equals(end)) {
                    Node nextNode = map.get(shortestPathList.get(currentIndex + 1));
                    String direction;

                    if (Objects.equals(currentNode.getX(), nextNode.getX())) {
                        if (currentNode.getY() > nextNode.getY()) {
                            direction = "up";
                        } else {
                            direction = "down";
                        }
                    } else {
                        if (currentNode.getX() > nextNode.getX()) {
                            direction = "left";
                        } else {
                            direction = "right";
                        }
                    }
                    System.out.println(currentIndex + 1 + ". Move " + direction + " to (" + nextNode.getX() + "," + nextNode.getY() + ")");
                } else {
                    System.out.println(currentIndex + 1 + ". Done!");
                }
            }
        }
    }
}