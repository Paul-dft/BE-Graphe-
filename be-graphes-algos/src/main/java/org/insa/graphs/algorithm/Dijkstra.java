package org.insa.graphs.algorithm;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Label_Nodes;
import org.insa.graphs.model.Node;

@SuppressWarnings("unused")

public class Dijkstra{
	
	Graph graphe;
	
	Node origin = graphe.get(0);
	
	boolean verif = true;
	
	double cost_arc;
	
	int E;
	
	List<Arc> destination = new ArrayList<Arc>();
	
	/*int compteur = 0;
	
	List<Label_Nodes> liste = new ArrayList<Label_Nodes>();
	
	Label_Nodes ln;
	
	List<Node> list_n = new ArrayList<Node>();
	
	Node sommet_courant;
	
	for (Node node : list_n) {
		
		ln.noeud = node;
		ln.label.sommet_courant = node;
		liste.add(ln);
		
	}*/
	
	BinaryHeap<Label> heap ;
	
	heap.insert()
	
	while (verif == true) {
		
		destination = getSuccessors(findMin());
		for (Arc arc : destination) {
			cost_arc = arc.getMinimumTravelTime();
			arc.getDestination()
		}
		
	}
	
	

}
