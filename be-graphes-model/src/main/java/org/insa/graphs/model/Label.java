package org.insa.graphs.model;

public class Label implements Comparable <Label>{
	
	public Node sommet_courant;
	
	public boolean marque = false;
	
	public double cost;
	
	public Arc pere;
	
	public double GetCost(Label label) {
		return label.cost;
	}


	public int compareTo(Label label_b) {
		
		return(Double.compare(GetCost(this), label_b.GetCost(label_b)));
	}

	

}
