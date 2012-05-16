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
	private List<GoogleDistanceElement> elements;

	/**
	 * Liefert das Attribut elements.
	 * 
	 * @return elements
	 */
	public List<GoogleDistanceElement> getElements()
	{
		return elements;
	}

	/**
	 * Setzt das Attribut elements.
	 * 
	 * @param elements
	 *            zu setzender Wert für das Attribut elements
	 */
	public void setElements( List<GoogleDistanceElement> elements )
	{
		this.elements = elements;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GoogleDistanceRow [elements=" + elements + "]";
	}

}
