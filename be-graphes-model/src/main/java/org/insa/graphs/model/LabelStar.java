package org.insa.graphs.model;

public class LabelStar extends Label implements Comparable <Label>{
	
	public double cost_dest;
	public Node dest; 
	
	public LabelStar() {
		super();
		
	}
	
	public double GetCost(LabelStar labelS) {
		return(this.cost + this.cost_dest);
	}
	
	
}
