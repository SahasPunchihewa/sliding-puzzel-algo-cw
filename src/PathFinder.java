/*
Name    : Pramuditha Sahas
IIT Id  : 20201214
UOW Id  : w1810601
*/

import java.io.File;
import java.util.*;

public class PathFinder {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private final static Character ROCK = '0';
    private Node start;
    private Node end;
    private List<List<Node>> nodeMatrix;
    private Integer height;
    private Integer width;
    private long initializingTime;
    private StopWatch localStopWatch;
    private Graph graph = new Graph();

    public PathFinder() {
    }

    public void findPath(StopWatch stopWatch) {

        localStopWatch = stopWatch;

        try {
            readFile();
        } catch (Exception ex) {
            System.out.println("An error Occurred while reading the file: " + ex.getMessage());
            return;
        }

        height = nodeMatrix.size();
        width = nodeMatrix.get(0).size();

        createGraph();

        if (graph == null) {
            System.out.println("No path available!");
            return;
        }

        List<Integer> shortestPath = graph.BreadthFirstSearch(start.getId(), end.getId());

        printShortestPath(shortestPath);

        stopWatch.stop();

        long totalTime = initializingTime + stopWatch.getElapsedTime();
        long minutes = (totalTime / 1000) / 60;
        long seconds = (totalTime / 1000) % 60;

        System.err.println("Elapsed time: " + totalTime + " milliseconds (" + minutes + " minutes and " + seconds + " seconds)");
    }

    private void readFile() throws Exception {

        localStopWatch.stop();
        initializingTime = localStopWatch.getElapsedTime();

        FileUtil fileUtil = new FileUtil();
        File txtFile = fileUtil.getFile();

        localStopWatch.start();

        Scanner scanner = new Scanner(txtFile);
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
                nodeMap.put(node.getId(), node);

                id++;
            }
            nodeMatrix.add(lineList);
            lineNo++;
        }
        scanner.close();
    }


    private void createGraph() {

        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        Node currentPosition = start;
        boolean pathPresent = false;

        stack.push(currentPosition.getId());
        graph.addVertex(currentPosition.getId());

        while (!stack.isEmpty()) {
            Integer vertexId = stack.pop();
            visited.add(vertexId);

            currentPosition = nodeMap.get(vertexId);

            if (findEndOfPath(currentPosition)) {
                graph.addVertex(end.getId());
                graph.addEdge(vertexId, end.getId());
                stack.clear();
                pathPresent = true;
                break;
            } else {
                if (canGoUp(currentPosition)) {
                    Node newNode = goUp(currentPosition);
                    checkVisitedAndUpdateGraph(visited, newNode, stack, vertexId);
                }
                if (canGoDown(currentPosition)) {
                    Node newNode = goDown(currentPosition);
                    checkVisitedAndUpdateGraph(visited, newNode, stack, vertexId);
                }
                if (canGoLeft(currentPosition)) {
                    Node newNode = goLeft(currentPosition);
                    checkVisitedAndUpdateGraph(visited, newNode, stack, vertexId);
                }
                if (canGoRight(currentPosition)) {
                    Node newNode = goRight(currentPosition);
                    checkVisitedAndUpdateGraph(visited, newNode, stack, vertexId);
                }
            }
        }
        if (!pathPresent) {
            graph = null;
        }
    }

    private void checkVisitedAndUpdateGraph(List<Integer> visited, Node newNode, Stack<Integer> stack, Integer vertexId) {
        if (!visited.contains(newNode.getId())) {
            stack.push(newNode.getId());
            graph.addVertex(newNode.getId());
            graph.addEdge(vertexId, newNode.getId());
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
            return !upNode.getLetter().equals(ROCK);
        }
    }

    private boolean canGoDown(Node node) {
        if (node.getY() == height - 1) {
            return false;
        } else {
            Node downNode = nodeMatrix.get(node.getY() + 1).get(node.getX());
            return !downNode.getLetter().equals(ROCK);
        }
    }

    private boolean canGoLeft(Node node) {
        if (node.getX() == 0) {
            return false;
        } else {
            Node leftNode = nodeMatrix.get(node.getY()).get(node.getX() - 1);
            return !leftNode.getLetter().equals(ROCK);
        }
    }

    private boolean canGoRight(Node node) {
        if (node.getX() == width - 1) {
            return false;
        } else {
            Node rightNode = nodeMatrix.get(node.getY()).get(node.getX() + 1);
            return !rightNode.getLetter().equals(ROCK);
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
                if (verticalNode.getLetter().equals(ROCK)) {
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
                if (horizontalNode.getLetter().equals(ROCK)) {
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
                Node currentNode = nodeMap.get(vertexId);

                if (currentNode == start) {
                    System.out.println(currentIndex + 1 + ". Start at (" + currentNode.getX() + "," + currentNode.getY() + ")");
                }

                if (!currentNode.equals(end)) {
                    Node nextNode = nodeMap.get(shortestPathList.get(currentIndex + 1));
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
                    System.out.println(currentIndex + 2 + ". Move " + direction + " to (" + nextNode.getX() + "," + nextNode.getY() + ")");
                } else {
                    System.out.println(currentIndex + 2 + ". Done!");
                }
            }
        }
    }
}
