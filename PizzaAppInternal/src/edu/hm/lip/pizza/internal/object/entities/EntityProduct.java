package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für die Produkte. Es werden alle benötigten Produkt-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, eine Beschreibung und das zugehörige Produktbild.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "product" )
public class EntityProduct extends AbstractEntityObject
{

	private static final long serialVersionUID = 2540347691582631624L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String name;

	private String description;

	private String imageUrl;

	@OneToMany( mappedBy = "product",fetch=FetchType.EAGER )
	@Cascade( value = CascadeType.ALL )
	private List<EntityProductConfiguration> configurations;

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
	 * Liefert das Attribut name.
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setzt das Attribut name.
	 * 
	 * @param name
	 *            zu setzender Wert für das Attribut name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

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
	 * Liefert das Attribut imageUrl.
	 * 
	 * @return imageUrl
	 */
	public String getImageUrl()
	{
		return imageUrl;
	}

	/**
	 * Setzt das Attribut imageUrl.
	 * 
	 * @param imageUrl
	 *            zu setzender Wert für das Attribut imageUrl
	 */
	public void setImageUrl( String imageUrl )
	{
		this.imageUrl = imageUrl;
	}

	/**
	 * Liefert das Attribut configurations.
	 * 
	 * @return configurations
	 */
	public List<EntityProductConfiguration> getConfigurations()
	{
		return configurations;
	}

	/**
	 * Setzt das Attribut configurations.
	 * 
	 * @param configurations
	 *            zu setzender Wert für das Attribut configurations
	 */
	public void setConfigurations( List<EntityProductConfiguration> configurations )
	{
		this.configurations = configurations;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
