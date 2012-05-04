package edu.hm.lip.pizza.domain;

/**
 * Repräsentiert ein Adress Objekt.
 * 
 * @author Franz Mathauser
 */
public class Address
{

	private String lat;

	private String lng;

	private final String addressString;

	public Address( String addressString )
	{
		this.addressString = addressString;
	}

	public String getLat()
	{
		return lat;
	}

	public void setLat( String lat )
	{
		this.lat = lat;
	}

	public String getLng()
	{
		return lng;
	}

	public void setLng( String lng )
	{
		this.lng = lng;
	}

	public String getAddressString()
	{
		return addressString;
	}

	@Override
	public String toString()
	{
		return "Address [lat=" + lat + ", lng=" + lng + ", addressString=" + addressString + "]";
	}

}
