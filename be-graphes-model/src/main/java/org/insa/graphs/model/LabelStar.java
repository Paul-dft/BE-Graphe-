package org.insa.graphs.model;

public class LabelStar extends Label{
	
	private double cost_dest;
	private Node dest; 
	
	public LabelStar(Node sommet_courant, boolean marque, double cost, Arc pere) {
		super();
		
		this.cost_dest = this.sommet_courant.getPoint().distanceTo(this.dest.getPoint());
	}
	
	public int compareTo(LabelStar label_b) {
		if (this.cost + this.cost_dest <= label_b.cost + label_b.cost_dest) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
