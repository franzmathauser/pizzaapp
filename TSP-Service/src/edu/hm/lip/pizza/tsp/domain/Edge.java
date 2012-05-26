package edu.hm.lip.pizza.tsp.domain;

/**
 * Repräsentiert einen Knoten des Graphen.
 * 
 * @author Franz Mathauser
 */
public class Edge
{

	private final int id;

	private final Address address;

	/**
	 * @param id
	 * @param address
	 */
	public Edge( int id, Address address )
	{
		this.id = id;
		this.address = address;
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
