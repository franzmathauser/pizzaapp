package edu.hm.lip.pizza.domain;

/**
 * Repräsentiert ein Adress Objekt.
 * 
 * @author Franz Mathauser
 */
public class Address
{

	private Integer id;

	private String lat;

	private String lng;

	private final String addressString;

	/**
	 * Liefert die Adresszeile als String.
	 * 
	 * @param addressString
	 *            Stringrespräsentation der Adresse
	 */
	public Address( String addressString )
	{
		this.addressString = addressString;
	}

	/**
	 * Liefert das Attribut id.
	 * 
	 * @return id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Setzt das Attribut id.
	 * 
	 * @param id
	 *            zu setzender Wert für das Attribut id
	 */
	public void setId( Integer id )
	{
		this.id = id;
	}

	/**
	 * Liefert das Attribut lat.
	 * 
	 * @return lat
	 */
	public String getLat()
	{
		return lat;
	}

	/**
	 * Setzt das Attribut lat.
	 * 
	 * @param lat
	 *            zu setzender Wert für das Attribut lat
	 */
	public void setLat( String lat )
	{
		this.lat = lat;
	}

	/**
	 * Liefert das Attribut lng.
	 * 
	 * @return lng
	 */
	public String getLng()
	{
		return lng;
	}

	/**
	 * Setzt das Attribut lng.
	 * 
	 * @param lng
	 *            zu setzender Wert für das Attribut lng
	 */
	public void setLng( String lng )
	{
		this.lng = lng;
	}

	/**
	 * Liefert das Attribut addressString.
	 * 
	 * @return addressString
	 */
	public String getAddressString()
	{
		return addressString;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Address [lat=" + lat + ", lng=" + lng + ", addressString=" + addressString + "]";
	}

}
