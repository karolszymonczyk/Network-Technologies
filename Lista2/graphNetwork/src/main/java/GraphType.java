import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public interface GraphType {

  public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGraph();
}
