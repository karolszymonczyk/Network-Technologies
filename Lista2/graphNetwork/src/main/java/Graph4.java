import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Random;

public class Graph4 implements GraphType {

  private SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
  private Random generator = new Random();

  private void createGraph() {
    for (int i = 1; i <= 20; i++) {
      graph.addVertex(i);
      if (i > 1) {
        graph.setEdgeWeight(graph.addEdge(i - 1, i), 0.95);
      }
    }
    graph.setEdgeWeight(graph.addEdge(1, 20), 0.95);
    graph.setEdgeWeight(graph.addEdge(1, 10), 0.8);
    graph.setEdgeWeight(graph.addEdge(5, 15), 0.7);
    int counter = 0;
    while (counter != 4) {
      int n1 = generator.nextInt(19) + 1;
      int n2 = generator.nextInt(19) + 1;
      if (n1 == n2 || graph.containsEdge(n1, n2) || graph.containsEdge(n2, n1)) {
        continue;
      }
      graph.setEdgeWeight(graph.addEdge(n1, n2), 0.4);
      counter++;
    }
  }

  public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
    graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    createGraph();
    return graph;
  }
}
