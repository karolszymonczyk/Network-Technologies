import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph3 implements GraphType {

  private SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;

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
  }

  public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
    graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    createGraph();
    return graph;
  }
}
