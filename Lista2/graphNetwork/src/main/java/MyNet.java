import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class MyNet implements GraphType {
  private SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;

  private void createGraph() {
    for (int i = 1; i <= 10; i++) {
      graph.addVertex(i);
      if (i > 1) {
        graph.setEdgeWeight(graph.addEdge(i - 1, i), 0.95);
      }
    }
    graph.setEdgeWeight(graph.addEdge(1, 10), 0.95);
    graph.setEdgeWeight(graph.addEdge(1, 6), 0.95);
    graph.setEdgeWeight(graph.addEdge(2, 7), 0.95);
    graph.setEdgeWeight(graph.addEdge(3, 8), 0.95);
    graph.setEdgeWeight(graph.addEdge(4, 9), 0.95);
    graph.setEdgeWeight(graph.addEdge(5, 10), 0.95);
  }

  public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
    graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    createGraph();
    return graph;
  }
}
