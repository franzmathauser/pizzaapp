package edu.hm.lip.pizza.domain;

/**
 * Repräsentiert einen Knoten des Graphen
 * 
 * @author Franz Mathauser
 */
public class Edge
{

	private final int id;

	private final Address address;

	public Edge( int id, Address address )
	{
		this.id = id;
		this.address = address;
	}

	public int getId()
	{
		return id;
	}

	public Address getAddress()
	{
		return address;
	}

	@Override
	public String toString()
	{
		return "Edge [id=" + id + ", address=" + address + "]";
	}

}
