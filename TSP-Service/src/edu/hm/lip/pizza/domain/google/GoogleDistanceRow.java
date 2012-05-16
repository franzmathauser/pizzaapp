package edu.hm.lip.pizza.domain.google;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Repräsentiert eine Reihe der Distance-Matrix des Json-Response der GoogleMaps API.
 * 
 * @author Franz Mathauser
 */
public class GoogleDistanceRow
{

	@JsonProperty( "elements" )
	List<GoogleDistanceElement> elements;

	public List<GoogleDistanceElement> getElements()
	{
		return elements;
	}

	public void setElements( List<GoogleDistanceElement> elements )
	{
		this.elements = elements;
	}

	@Override
	public String toString()
	{
		return "GoogleDistanceRow [elements=" + elements + "]";
	}

}
