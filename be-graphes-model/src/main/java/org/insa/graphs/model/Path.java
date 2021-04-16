package org.insa.graphs.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * deprecated Need to be implemented.
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();
        List<Arc> arcs3 = new ArrayList<Arc>(); //liste des successeurs valides
        Arc shortArc = null; //arc le plus court
        
        if (nodes.size() == 1) {
        	return new Path(graph,nodes.get(0));}       
        
        for (int i = 0; i<nodes.size() -1; i++) {
        	
        	List<Arc> arcs2 = new ArrayList<Arc>(); //liste des successeurs
        	arcs2 = nodes.get(i).getSuccessors();
        	
        	for (Arc arc : arcs2) {
        		if (arc.getDestination() == nodes.get(i+1)) {arcs3.add(arc);}
        	}
        	
        	if(arcs3.isEmpty()) {throw(new IllegalArgumentException("pas de chemin"));}
        	
        	for (Arc arc : arcs3) {
        		double min = 1.0/0.0;
        		if (arc.getMinimumTravelTime()<min) {
        			min = arc.getMinimumTravelTime();
        			shortArc = arc;
        		}
        	}
        	
        	arcs.add(shortArc);
        
        }
        return new Path(graph, arcs);
  
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * deprecated Need to be implemented.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        /*List<Arc> arcs = new ArrayList<Arc>(); //liste d'arc définitive
        List<Arc> arcs3 = new ArrayList<Arc>(); //liste des successeurs valides
        Arc shortArc = null; //arc le plus court
        
        if (nodes.size() == 1) {
        	return new Path(graph,nodes.get(0));}
        
        for (int i = 0; i<nodes.size() -1; i++) {
        	
        	List<Arc> arcs2 = new ArrayList<Arc>(); //liste des successeurs
        	arcs2 = nodes.get(i).getSuccessors();
        	
        	for (Arc arc : arcs2) {
        		if (arc.getDestination() == nodes.get(i+1)) {arcs3.add(arc);}
        	}
        	
        	if(arcs3.isEmpty()) {throw(new IllegalArgumentException("pas de chemin"));}
        	
        	for (Arc arc : arcs3) {
        		double min = 1.0/0.0;
        		if (arc.getLength()<min) {
        			min = arc.getLength();
        			shortArc = arc;
        		}
        	}
        	
        	arcs.add(shortArc);
        
        }
        return new Path(graph, arcs);*/
    	
    	if (nodes.size() == 0) {
        	return new Path(graph);
        }
        if (nodes.size() == 1) {
        	return new Path(graph, nodes.get(0));
        }
        
        List<Arc> arcs = new ArrayList<Arc>();
        
        //For each nodes in the given list, except the last
        for (int i = 0; i < nodes.size()-1; i++) {
        	
        	//Initialization
        	Node node1 = nodes.get(i); //Origin
        	Node node2 = nodes.get(i+1); //Destination
        	//shortest arc is the first by default, even if it doesn't go to node2
        	Arc shortest = node1.getSuccessors().get(0);

        	//Test for each arc between the nodes and look for a faster arc, or at least one that goes to node2
        	for (int k = 1; k < node1.getNumberOfSuccessors();k++) {
        		Arc candidat = node1.getSuccessors().get(k);
        		if (candidat.getDestination() == node2) { //Is the arc going where we want it to ?
        			
        			//if the first candidate wasn't going to node2 or if we found a faster arc
        			if (shortest.getDestination() != node2 || candidat.getLength() < shortest.getLength() ) { // Is it faster ?
        				shortest = candidat; // Update the shortest arc found
        			}
        		}
        	}
        	
    		if (node2 != shortest.getDestination()) { // if nodes aren't connected
    			throw (new IllegalArgumentException());
    		}
    		
    		//If everything is OK, we append the shortest arc to the final list
    		arcs.add(shortest);
        }

        return new Path(graph, arcs);
        
    	
    
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * deprecated Need to be implemented.
     */
    public boolean isValid() {
        if (this.isEmpty()) {return true;} //condition 1
        if (this.size() == 1) {return true;} // condition 2
        
        List<Arc> arc = getArcs();
        
        if(this.origin != arc.get(0).getOrigin()) {return false;} // on check que l'origine du path est celle du premier arc
        
        for (int i = 0; i<this.size() - 2; i++ ) { //pour chaque arc on check la condition 3
			if (arc.get(i).getDestination() != arc.get(i+1).getOrigin()) {
				return false;
			}
        }
        
    	return true;
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     * deprecated Need to be implemented.
     */
    public float getLength() {
    	float length = 0;
        for (Arc a : arcs) {
			length  +=  a.getLength();
        }
        return length;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     * deprecated Need to be implemented.
     */
    public double getTravelTime(double speed) {
    	return getLength() * 3600.0 / (speed * 1000.0);
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     * deprecated Need to be implemented.
     */
    public double getMinimumTravelTime() {
    	float speed = 0;
        for (Arc a : arcs) {
			speed  +=  a.getMinimumTravelTime();
        }
    	return speed;
    }


}
