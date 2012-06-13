package edu.hm.lip.pizza.driver.objects.resource;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.hm.lip.pizza.driver.object.AbstractObject;

/**
 * Ressource für einen Fahrer.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Driver extends AbstractObject
{

	private static final long serialVersionUID = -6603727263288437346L;

	private Integer m_id;

	private String m_name;

	/**
	 * Konstruktor.
	 */
	public Driver()
	{

	}

	/**
	 * Konstruktor.
	 * 
	 * @param id
	 *            Identifikator
	 * @param name
	 *            Name des Fahrers.
	 */
	public Driver( Integer id, String name )
	{
		m_id = id;
		m_name = name;
	}

	/**
	 * Liefert das Attribut id.
	 * 
	 * @return id
	 */
	@JsonProperty( "id" )
	public Integer getId()
	{
		return m_id;
	}

	/**
	 * Setzt das Attribut id.
	 * 
	 * @param id
	 *            zu setzender Wert für das Attribut id
	 */
	@JsonProperty( "id" )
	public void setId( Integer id )
	{
		m_id = id;
	}

	/**
	 * Liefert das Attribut name.
	 * 
	 * @return name
	 */
	@JsonProperty( "name" )
	public String getName()
	{
		return m_name;
	}

	/**
	 * Setzt das Attribut name.
	 * 
	 * @param name
	 *            zu setzender Wert für das Attribut name
	 */
	@JsonProperty( "name" )
	public void setName( String name )
	{
		m_name = name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( this.getClass() );
		rsb.setExcludeFieldNames( null );
		return rsb.toString();
	}
}
