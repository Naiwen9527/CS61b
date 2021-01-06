package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome;
    private double solutionWeight;
    private int numStatesExplored;
    private List<Vertex> solution;
    private double timeSpent;

    private DoubleMapPQ<Vertex> fringe;

    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;


    public AStarSolver(AStarGraph<Vertex> G, Vertex start, Vertex goal, double timeout) {
        solutionWeight = 0;
        outcome = SolverOutcome.UNSOLVABLE;
        solution = new ArrayList<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        fringe = new DoubleMapPQ<>();

        double heuristics = G.estimatedDistanceToGoal(start, goal);
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        fringe.add(start, heuristics);

        Stopwatch sw = new Stopwatch();
        actualWork(G, goal, timeout, sw);

        // construct solution
        if (outcome.equals(SolverOutcome.SOLVED)) {
            solution.add(goal);
            solutionWeight = distTo.get(goal);
            Vertex current = edgeTo.get(goal);
            while (distTo.get(current) != null) {
                solution.add(0, current);
                current = edgeTo.get(current);
            }
        }
        // check time
        timeSpent = sw.elapsedTime();
        if (timeSpent > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        }
    }

    private void actualWork(AStarGraph<Vertex> G, Vertex goal, double timeout, Stopwatch sw) {
        while (fringe.size() > 0) {

            // hit the goal
            if (fringe.getSmallest().equals(goal)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }

            // get a new Vertex
            Vertex fresh = fringe.removeSmallest();
            numStatesExplored += 1;
            // get the neighbours of the new Vertex
            List<WeightedEdge<Vertex>> neighborEdges = G.neighbors(fresh);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                Vertex from = e.from();
                Vertex to = e.to();
                double weight = e.weight();
                double distance = distTo.get(from) + weight;
                double heuristics = G.estimatedDistanceToGoal(to, goal);
                double priority = distance + heuristics;

                if (!distTo.containsKey(to)) {
                    distTo.put(to, distance);
                    edgeTo.put(to, from);
                    fringe.add(to, priority);
                }

                if (distTo.containsKey(to)) {
                    if (distance < distTo.get(to)) {
                        distTo.replace(to, distance);
                        edgeTo.replace(to, from);
                        if (fringe.contains(to)) {
                            fringe.changePriority(to, priority);
                        }
                        if (!fringe.contains(to)) {
                            fringe.add(to, priority);
                        }
                    }
                }

                // check time
                timeSpent = sw.elapsedTime();
                if (timeSpent > timeout) {
                    outcome = SolverOutcome.TIMEOUT;
                    return;
                }

            }

        }

    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
