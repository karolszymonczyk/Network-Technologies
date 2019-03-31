import org.jgrapht.GraphPath;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Random;

public class Main {

  private final static int TASK = 2;

  private static Random generator = new Random();
  private static int[][] A = new int[10][10];
  private static int[][] C = new int[10][10];
  private static int[][] N = {
          {0, 9, 8, 7, 6, 5, 4, 3, 2, 1},
          {9, 0, 9, 8, 7, 6, 5, 4, 3, 2},
          {8, 9, 0, 9, 8, 7, 6, 5, 4, 3},
          {7, 8, 9, 0, 9, 8, 7, 6, 5, 4},
          {6, 7, 8, 9, 0, 9, 8, 7, 6, 5},
          {5, 6, 7, 8, 9, 0, 9, 8, 7, 6},
          {4, 5, 6, 7, 8, 9, 0, 9, 8, 7},
          {3, 4, 5, 6, 7, 8, 9, 0, 9, 8},
          {2, 3, 4, 5, 6, 7, 8, 9, 0, 9},
          {1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
  };
  private static double T_max = 0.05;

  private static int sumN;

  public static void main(String[] args) {

    switch (TASK) {
      case 1: {
        Graph1 graph1 = new Graph1();
        Graph2 graph2 = new Graph2();
        Graph3 graph3 = new Graph3();
        Graph4 graph4 = new Graph4();

        checkGraph(graph1, -1);
        checkGraph(graph2, -1);
        checkGraph(graph3, -1);
        checkGraph(graph4, -1);
        break;
      }
      case 2: {
        MyNet net = new MyNet();

        generateA(net.getGraph());
        generateC();
        System.out.println("Matrix N : ");
        printMatrix(N);
        System.out.println("Matrix A : ");
        printMatrix(A);
        System.out.println("Matrix C : ");
        printMatrix(C);

        calculateSumN();
        checkGraph(net, 1);
        System.out.println();
        System.out.println();
        System.out.println();
        checkGraph(net, 2);
        break;
      }
    }
  }

  private static void checkGraph(GraphType graphType, int mode) {
    SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
    ArrayList<DefaultWeightedEdge> edges = new ArrayList<>();
    String name = graphType.getClass().getName();
    double weight;
    double random;
    int all = 0;
    int cohesive = 0;
    double sum = 0;
    int counter = 0;
    while (all != 100000) {
      graph = graphType.getGraph();
      edges.addAll(graph.edgeSet());
      for (DefaultWeightedEdge edge : edges) {
        weight = graph.getEdgeWeight(edge);
        random = generator.nextDouble();
        if (random > weight) {
          graph.removeEdge(edge);
        }
      }
      if (isCohesive(graph)) {
        switch (TASK) {
          case 1: {
            cohesive++;
            break;
          }
          case 2: {
            generateA(graph);
            double t = calculateDelay(graph);
            if (mode == 1) {
              if (t > 0) {
                sum += t;
                counter++;
              }
            } else {
              if (t > 0 && t < T_max) {
                counter++;
              }
            }
          }
        }
      }
      all++;

      switch (TASK) {
        case 1: {
          calculateReliability(name, cohesive, all);
          break;
        }
        case 2: {
          if (mode == 1) {
            calculateAverageDelay(name, sum, counter);
          } else {
            calculateTimeReliablility(name, counter, all);
          }
        }
      }
    }
    System.out.println();
  }

  private static boolean isCohesive(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
    return new ConnectivityInspector<>(graph).isGraphConnected();
  }

  private static void calculateReliability(String name, int cohesive, int all) {
    System.out.print("\r");
    System.out.print(name + ": " + cohesive * 100 / all + "%  " + cohesive + "/" + all);
  }

  private static void calculateAverageDelay(String name, double sum, int counter) {
    if (counter != 0) {
      System.out.print("\r");
      System.out.print(name + " average delay: " + sum / counter + "   " + sum + "/" + counter);
    }
  }

  private static void calculateTimeReliablility(String name, double sum, int counter) {
    if (counter != 0) {
      System.out.print("\r");
      System.out.print(name + " reliability: " + sum / counter + "   " + sum + "/" + counter);
    }
  }

  private static void generateA(SimpleWeightedGraph<Integer, DefaultWeightedEdge> net) {
    restartA();
    GraphPath<Integer, DefaultWeightedEdge> path;
    ArrayList<Integer> nodes;
    int val;
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        if (i == j) continue;
        path = DijkstraShortestPath.findPathBetween(net, i, j);
        nodes = (ArrayList<Integer>) path.getVertexList();
        val = N[i - 1][j - 1];
        for (int n = 0; n < nodes.size() - 1; n++) {
          A[nodes.get(n) - 1][nodes.get(n + 1) - 1] += val;
          A[nodes.get(n + 1) - 1][nodes.get(n) - 1] += val;
        }
      }
    }
  }

  private static void restartA() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        A[i][j] = 0;
      }
    }
  }

  private static void generateC() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (i == j) {

        } else if (A[i][j] == 0) {
          C[i][j] = 100;
        } else {
          C[i][j] = 2 * A[i][j];
        }
      }
    }
  }

  private static void printMatrix(int[][] matrix) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (matrix[i][j] / 10 == 0) {
          System.out.print("0");
        }
        if (matrix[i][j] / 100 == 0) {
          System.out.print("0");
        }
        System.out.print(matrix[i][j]);
        System.out.print(" ");
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  private static void calculateSumN() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        sumN += N[i][j];
      }
    }
  }

  private static double calculateDelay(SimpleWeightedGraph graph) {
    double sum = 0;
    double downSum;
    int es;
    int et;

    for (Object e : graph.edgeSet()) {
      es = (int) graph.getEdgeSource(e);
      et = (int) graph.getEdgeTarget(e);
      downSum = C[es - 1][et - 1] - A[es - 1][et - 1];
      if (downSum == 0) {
        return 0;
      }
      sum += A[es - 1][et - 1] / downSum;
    }
    return sum / sumN;
  }
}
