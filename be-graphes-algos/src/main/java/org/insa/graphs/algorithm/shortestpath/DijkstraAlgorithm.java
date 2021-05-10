package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Label_Nodes;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Label min;
        Label label_suivant;
        Node noeud_min;
        Node noeud_suivant;
        int cost_inter;
        
        
        boolean verif = true;
    	
    	double cost_arc;
    	
    	int E;
    	
    	List<Arc> destination = new ArrayList<Arc>();
    	
    	BinaryHeap<Label> heap ;
    	
    	Node origine = data.getOrigin();
    	Label Label_origin;
    	Label_origin.sommet_courant = origine;
    	Label_origin.cost = 0;
    	Label_origin.pere = null;
    	
    	Graph graphe = data.getGraph();
    	
    	Label[] LL;
    	
    	LL = new Label[graphe.size()];
    	
    	for(Node n : graphe.getNodes()) {
    		
    		Label l;
    		l.cost = 1/0;
    		l.pere = null;
    		l.sommet_courant = n;
    		LL[n.getId()] = l;
    		
    	}
    	
    	int var = origine.getId();
    	LL[var] = Label_origin;
    	
    	
    	for(Label l : LL) {
    		
    		heap.insert(l);
    		
    	}
    	
    	while (heap.isEmpty() == false) {
    		
    		min = heap.findMin();
    		noeud_min = min.sommet_courant;
    		
    		for (Arc arc : noeud_min.getSuccessors()) {
    			noeud_suivant = arc.getDestination();
    			cost_arc = arc.getMinimumTravelTime();
    			
    			label_suivant = LL[noeud_suivant.getId()];
    			
    			if (cost_arc < label_suivant.cost) {
    				
    				label_suivant.cost = cost_arc;
    				label_suivant.pere = arc;
    				
    			}
    		}
    		
    		heap.remove(min);
    		
    	}
    	
        return solution;
    }

}
