package edu.hm.lip.pizza.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Weg zwischen mehrerer Ecken über Kanten.
 */
public class Path
{

	/**
	 * Liste der zusammenhängenden Ecken.
	 */
	private final List<Edge> edges = new ArrayList<Edge>();

	/**
	 * Kosten um den ganzen Pfad zu durchlaufen.
	 */
	private Long cost;

	/**
	 * 
	 */
	public Path()
	{

	}

	/**
	 * @param list
	 */
	public Path( ArrayList<Edge> list )
	{
		for (Edge edge : list)
		{
			edges.add( edge );
		}
	}

	/**
	 * @param edge
	 */
	public void addEdge( Edge edge )
	{
		edges.add( edge );
	}

	/**
	 * @param index
	 * @return
	 */
	public Edge getEdge( int index )
	{
		return edges.get( index );
	}

	/**
	 * @return
	 */
	public int size()
	{
		return edges.size();
	}

	/**
	 * @return
	 */
	public Long getCost()
	{
		return cost;
	}

	/**
	 * @param cost
	 */
	public void setCost( Long cost )
	{
		this.cost = cost;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Edge edge : edges)
		{
			sb.append( edge.getAddress().getAddressString() + " -> " );
		}
		sb.append( "(" + cost + ")" );
		return sb.toString();
	}
}
