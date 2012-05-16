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

	public String getDescription()
	{
		return description;
	}

	public String getValue()
	{
		return value;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "GoogleDistance [description=" + description + ", value=" + value + "]";
	}

}
