package org.insa.graphs.algorithm.shortestpath;

import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @SuppressWarnings("null")
	@Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Label min;
        Label label_suivant;
        Node noeud_min;
        Node noeud_suivant;
        
        List<Arc> arc_final = null;
        
        
    	double cost_arc;
    	
    	BinaryHeap<Label> heap = null ;
    	
    	// initialisation du Label origine 
    	
    	Node origine = data.getOrigin();
    	Label Label_origin = null;
    	Label_origin.sommet_courant = origine;
    	Label_origin.cost = 0;
    	Label_origin.pere = null;
    	
    	// récupération du graphe 
    	
    	Graph graphe = data.getGraph();
    	
    	// création d'une liste de label pour permettre d'associer un noeud à un label
    	
    	Label[] LL;
    	
    	LL = new Label[graphe.size()];
    	
    	for(Node n : graphe.getNodes()) {
    		
    		Label l = null;
    		l.cost = 1/0;
    		l.pere = null;
    		l.sommet_courant = n;
    		LL[n.getId()] = l;
    		
    	}
    	
    	// on remplace le label origine dans la liste 
    	
    	int var = origine.getId();
    	LL[var] = Label_origin;
    	
    	// on insert chaque label dans le tas 
    	
    	
    	for(Label l : LL) {
    		
    		heap.insert(l);
    		
    	}
    	
    	// tant que le temps n'est pas vide on continue notre algo
    	
    	while (heap.isEmpty() == false) {
    		
    		// On prend le label avec le cout minimum
    		
    		min = heap.findMin();
    		noeud_min = min.sommet_courant;
    		
    		// On regarde tous ses sucesseur 
    		
    		for (Arc arc : noeud_min.getSuccessors()) {
    			
    			noeud_suivant = arc.getDestination();
    			//cost_arc = arc.getLength();
    			cost_arc = data.getCost(arc);
    			
    			label_suivant = LL[noeud_suivant.getId()];
    			
    			// Si on se rend compte que le trajet est plus court on remplace le cost 
    			
    			if (cost_arc < label_suivant.cost) {
    				
    				label_suivant.cost = cost_arc;
    				label_suivant.pere = arc;
    				
    			}
    		}
    		
    		// On enleve notre noeud minimum 
    		
    		arc_final.add(min.pere);
    		heap.remove(min);
    		
    	}
    	
    	
    	
    	
    	//path_sol = solution.getPath();
    	//solution.path.Path(graphe,arc_final);
  
    	//solution.ShortestPathSolution(data,,path_sol.Path(graphe,arc_final));
        
        //solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
    	
    	// Destination has no predecessor, the solution is infeasible...
        if (LL[data.getDestination().getId()].cost == 1/0) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
            //Path path_sol = new Path(graphe, arc_final);
            
            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graphe, arc_final));
        }

    	
    	
        return solution;
    }


}
