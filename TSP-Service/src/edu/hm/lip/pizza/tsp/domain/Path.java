package edu.hm.lip.pizza.tsp.domain;

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
	 * Erzeugt einen neuen Pfad.
	 */
	public Path()
	{

	}

	/**
	 * Liefert das Attribut edges.
	 * 
	 * @return edges
	 */
	public List<Edge> getEdges()
	{
		return edges;
	}

	/**
	 * Liefert eine Liste mit Ecken.
	 * 
	 * @param list
	 *            Ecken-Liste
	 */
	public Path( ArrayList<Edge> list )
	{
		for (Edge edge : list)
		{
			edges.add( edge );
		}
	}

	/**
	 * Fügt eine neue Ecke hinzu.
	 * 
	 * @param edge
	 *            Ecke
	 */
	public void addEdge( Edge edge )
	{
		edges.add( edge );
	}

	/**
	 * Liefert eine Ecke anhand des Index.
	 * 
	 * @param index
	 *            Indexwert
	 * @return Ecke
	 */
	public Edge getEdge( int index )
	{
		return edges.get( index );
	}

	/**
	 * Liefert die Anzahl der Ecken.
	 * 
	 * @return Eckenanzahl
	 */
	public int size()
	{
		return edges.size();
	}

	/**
	 * Liefert die Kosten beim durchlauf des Pfades.
	 * 
	 * @return Pfadkosten (DURRATION [seconds], DISTANCE [meters])
	 */
	public Long getCost()
	{
		return cost;
	}

	/**
	 * Setzt die Pfadkosten.
	 * 
	 * @param cost
	 *            Pfadkosten (DURRATION [seconds], DISTANCE [meters])
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
