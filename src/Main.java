public class Main {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Puzzle p = new Puzzle();
        p.setFileLocation("/Users/sahaspunchihewa/Desktop/IIT/YR 2/Algo/examples/maze30_1.txt");
        p.initializePuzzleArray();
        p.fillPuzzleArray();
        UndirectedGraph g = new UndirectedGraph();
        p.createGraph(g);
        p.printPathDetails(g);
        stopWatch.stop();

        System.err.println("elapsed time: " + stopWatch.getElapsedTime());
    }

}
