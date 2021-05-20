package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
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
        
        
        
        // récupération du graphe 
        
    	Graph graphe = data.getGraph();
    	
    	Label[] LL;
    	
    	LL = new Label[graphe.size()];
        
        Label min;
        Label label_suivant;
        Node noeud_min;
        Node noeud_suivant;
        
        
        List<Arc> arc_final = new ArrayList<Arc>();
        
        
    	double cost_arc;
    	
    	BinaryHeap<Label> heap = new BinaryHeap<Label>();
    	
    	// initialisation du Label origine 
    	
    	Node origine = data.getOrigin();
    	Label Label_origin = new Label();
    	Label_origin.sommet_courant = origine;
    	Label_origin.cost = 0;
    	//Label_origin.pere = null;
    	
    	
    	// création d'une liste de label pour permettre d'associer un noeud à un label
    	
    	
    	for(Node n : graphe.getNodes()) {
    		
    		Label l = new Label();
    		l.cost = Double.POSITIVE_INFINITY;
    		//l.pere = null;
    		l.sommet_courant = n;
    		LL[n.getId()] = l;
    		
    	}
    	
    	// on remplace le label origine dans la liste 
    	
    	int var = origine.getId();
    	LL[var] = Label_origin;
    	
    	//on insert le label origine dans le tas 
    	
    	heap.insert(Label_origin);
    	
    	// tant que le tas n'est pas vide on continue notre algo
    	
    	while ((LL[data.getDestination().getId()].marque == false)) {
    		
    		// On prend le label avec le cout minimum
    		
    		min = heap.findMin();

    		noeud_min = min.sommet_courant;
    		
    		// On regarde tous ses sucesseur 
    		
    		for (Arc arc : noeud_min.getSuccessors()) {
    			
    			noeud_suivant = arc.getDestination();
    			
    			cost_arc = data.getCost(arc);
    			
    			label_suivant = LL[noeud_suivant.getId()];
    			try {
    				heap.remove(label_suivant);
    			}catch(ElementNotFoundException ignored){
    				
    			}
    			
    			// Si on se rend compte que le trajet est plus court on remplace le cost 
    			
    			
    			if (label_suivant.marque == false) {
    				

        			if (min.cost + cost_arc < label_suivant.cost) {
        				
        				notifyNodeReached(arc.getDestination());
        				
        				label_suivant.cost = min.cost + cost_arc;
        				label_suivant.pere = arc;
        				
        			}
    			
    				heap.insert(label_suivant);
    			}
    		}
    		
    		// On enleve notre noeud minimum 
    		
    		arc_final.add(min.pere);
    		min.marque = true;
    		heap.remove(min);
    		
    		
    	}
    	
    	
    	
    	
    	//path_sol = solution.getPath();
    	//solution.path.Path(graphe,arc_final);
  
    	//solution.ShortestPathSolution(data,,path_sol.Path(graphe,arc_final));
        
        //solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
    	
    	ShortestPathSolution solution;
		// Destination has no predecessor, the solution is infeasible...
        if (LL[data.getDestination().getId()].cost == Double.POSITIVE_INFINITY) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        else {
            // mise en place de la liste de noeud 
            notifyDestinationReached(data.getDestination());

            ArrayList<Node> pathNodes = new ArrayList<>();
            pathNodes.add(data.getDestination());
            Node node = data.getDestination();
            while (!node.equals(data.getOrigin())) {
                Node fatherNode = (LL[node.getId()].pere).getOrigin();
                pathNodes.add(fatherNode);
                node = fatherNode;
            }
            Collections.reverse(pathNodes);

            // Creation du chemin final 
            
            Path solutionPath;
            if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
                solutionPath = Path.createShortestPathFromNodes(graphe, pathNodes);
            } else {
                solutionPath = Path.createFastestPathFromNodes(graphe, pathNodes);
            }

            solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
        }
    	
        return solution;
        
    }


}
