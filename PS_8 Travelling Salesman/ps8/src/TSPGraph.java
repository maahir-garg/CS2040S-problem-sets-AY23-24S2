import java.util.ArrayList;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        int numNodes = map.getCount();
        int[] parents = new int[numNodes];
        boolean[] visited = new boolean[numNodes];

        TreeMapPriorityQueue<Double, Integer> priorityQueue = new TreeMapPriorityQueue<>();
        for (int i = 1; i < numNodes; i++) {
            priorityQueue.add(i, map.pointDistance(0, i));
        }
        parents[0] = -1;
        visited[0] = true;
        while (!priorityQueue.isEmpty()) {
            int v = priorityQueue.extractMin();
            if (visited[v]) continue;
            visited[v] = true;

            for (int i = 0; i < numNodes; i++) {
                if (visited[i]) continue;
                if (map.pointDistance(v, i) < priorityQueue.lookup(i)) {
                    priorityQueue.decreasePriority(i, map.pointDistance(v, i));
                    parents[i] = v;
                }
            }
        }

        map.setLink(0, 0, false);

        for (int i = 1; i < numNodes; i++) {
            map.setLink(i, parents[i], false);
        }

        map.redraw();
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.

        int numNodes = map.getCount();
        boolean[] visited = new boolean[numNodes];
        ArrayList<Integer> nodeList = new ArrayList<>();
        nodeList.add(0);

        DFS(map, nodeList, visited, 0, numNodes);
        map.setLink(0, nodeList.get(nodeList.size() - 1), false);
        map.redraw();
    }

    private void DFS(TSPMap map, ArrayList<Integer> nodeList, boolean[] visited, int parent, int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            int node = map.getLink(i);

            if (!visited[i] && parent == node) {
                visited[i] = true;
                map.setLink(i, nodeList.get(nodeList.size() - 1), false);
                nodeList.add(i);
                DFS(map, nodeList, visited, i, numNodes);
            }
        }
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int numNodes = map.getCount();
        boolean[] visited = new boolean[numNodes];
        int count = -1;
        int node = 0;
        while (true) {
            count++;
            if (node == -1) return false;
            if (visited[node]) return count == numNodes && node == 0;
            visited[node] = true;
            node = map.getLink(node);
        }
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (!isValidTour(map)) return -1;
        double sum = 0;
        int curr = 0;
        int next = map.getLink(0);

        while (next != 0) {
            sum += map.pointDistance(curr, next);
            curr = next;
            next = map.getLink(next);
        }

        return sum + map.pointDistance(curr, next);
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

//        graph.MST(map);
         graph.TSP(map);
         System.out.println(graph.isValidTour(map));
         System.out.println(graph.tourDistance(map));
    }
}
