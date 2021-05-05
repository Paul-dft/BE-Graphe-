package org.insa.graphs.model;

public class Label implements Comparable <Label>{
	
	public Node sommet_courant;
	
	public boolean marque = false;
	
	public int cost;
	
	public Arc pere;
	
	public int GetCost(Label label) {
		return label.cost;
	}


	public int compareTo(Label label_b) {
		if (this.cost <= label_b.cost) {
			return 1;
		}
		else {
			return 0;
		}
	}

	

}
