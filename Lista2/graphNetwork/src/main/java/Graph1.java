import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph1 implements GraphType {

  private SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;

  private void createGraph() {
    for (int i = 1; i <= 20; i++) {
      graph.addVertex(i);
      if (i > 1) {
        graph.setEdgeWeight(graph.addEdge(i - 1, i), 0.95);
      }
    }
  }

  public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
    graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    createGraph();
    return graph;
  }
}
