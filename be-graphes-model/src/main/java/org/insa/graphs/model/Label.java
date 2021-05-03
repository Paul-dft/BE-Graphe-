package org.insa.graphs.model;

public class Label {
	
	int sommet_courant;
	
	boolean marque = false;
	
	int cout;
	
	Arc pere;
	
	public int GetCost(Label label) {
		return label.cout;
	}
	

}
