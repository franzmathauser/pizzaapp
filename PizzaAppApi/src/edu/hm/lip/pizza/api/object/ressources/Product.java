package edu.hm.lip.pizza.api.object.ressources;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement( name = "Product" )
//@XmlType( propOrder = { "name", "code", "examiner", "examDate", "room", "numSupervisors", "currentSupervisors" } )
//@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
//@XmlAccessorType( XmlAccessType.NONE )
//@JsonPropertyOrder( value = { "Name", "Code", "Examiner", "ExamineDate", "Room", "NecessarySupervisors", "Supervisors" },
//					alphabetic = true )
//@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class Product
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String name;

	private String description;

	private String imageUrl;

	@OneToMany( mappedBy = "product" )
	private List<ProductConfiguration> configurations;

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
	public List<ProductConfiguration> getConfigurations()
	{
		return configurations;
	}

	/**
	 * Setzt das Attribut configurations.
	 * 
	 * @param configurations
	 *            zu setzender Wert für das Attribut configurations
	 */
	public void setConfigurations( List<ProductConfiguration> configurations )
	{
		this.configurations = configurations;
	}

}
