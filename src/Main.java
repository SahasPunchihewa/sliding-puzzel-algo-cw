
public class Main {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PathFinder pathFinder = new PathFinder();
        pathFinder.findPath(stopWatch);
    }
}