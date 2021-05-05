package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        boolean verif = true;
    	
    	double cost_arc;
    	
    	int E;
    	
    	List<Arc> destination = new ArrayList<Arc>();
    	
    	BinaryHeap<Label> heap ;
    	
    	Node origine = data.getOrigin();
    	
    	heap.insert(origine);
    	
    	while (verif == true) {
    		
    		destination = getSuccessors(findMin());
    		for (Arc arc : destination) {
    			cost_arc = arc.getMinimumTravelTime();
    			arc.getDestination()
    		}
    		
    	}
    	
        return solution;
    }

}
