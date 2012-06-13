package edu.hm.lip.pizza.tsp.domain;

/**
 * Repräsentiert einen Knoten des Graphen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class Edge
{

	private final int id;

	private final Address address;

	/**
	 * Erzeugt ein neues Edge-Objekt.
	 * 
	 * @param edgeId
	 *            Identifikator
	 * @param edgeAddress
	 *            Adress-Objekt
	 */
	public Edge( int edgeId, Address edgeAddress )
	{
		this.id = edgeId;
		this.address = edgeAddress;
	}

	/**
	 * Liefert das Attribut id.
	 * 
	 * @return id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Liefert das Attribut address.
	 * 
	 * @return address
	 */
	public Address getAddress()
	{
		return address;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Edge [id=" + id + ", address=" + address + "]";
	}

}
