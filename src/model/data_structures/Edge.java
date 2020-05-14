package model.data_structures;

public class Edge <T extends Comparable<T>,V extends Comparable<V>> implements Comparable<Edge<T,V>>{

	private double cost;
	private Vertex<T, V> initialVertex;
	private Vertex<T,V> finalVertex;
	
	
	public Edge (Vertex<T,V> pInitialVertex, Vertex<T,V> pFinalVertex, double pCost){
		initialVertex = pInitialVertex;
		finalVertex = pFinalVertex;
		cost = pCost;
	}
	
	public Vertex<T, V>getOrigin(){
		return initialVertex;
	}
	
	public Vertex<T, V> getFinalVertex(){
		return finalVertex;
	}
	
	public double getCost(){
		return cost;
	}
	
	public void changeCost(double pCost){
		cost = pCost;
	}
	
	
	@Override
	public int compareTo(Edge<T, V> arg0) {
		return (arg0.getOrigin().compareTo(this.initialVertex)+(arg0.getFinalVertex().compareTo(this.finalVertex)));
	}

}
