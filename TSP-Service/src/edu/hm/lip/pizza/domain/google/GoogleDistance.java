package edu.hm.lip.pizza.domain.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Repräsentiert die Distance des Json-Response der GoogleMaps API.
 * 
 * @author Franz Mathauser
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GoogleDistance
{

	@JsonProperty( "text" )
	private String description;

	@JsonProperty( "value" )
	private String value;

	/**
	 * Liefert das Attribut description.
	 * 
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Setzt das Attribut description.
	 * 
	 * @param description
	 *            zu setzender Wert für das Attribut description
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}

	/**
	 * Liefert das Attribut value.
	 * 
	 * @return value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Setzt das Attribut value.
	 * 
	 * @param value
	 *            zu setzender Wert für das Attribut value
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GoogleDistance [description=" + description + ", value=" + value + "]";
	}

}
