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
		if (GetCost(this)< GetCost(label_b)) {
			return 1;
		}
		if (GetCost(this) == GetCost(label_b)) {
			if (this.cost <= label_b.cost) {
				return(1);
			}
			else {return(0);}
		}
		else {
			return 0;
		}
	}

	

}
