package edu.hm.lip.pizza.api.object.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;
import edu.hm.lip.pizza.api.object.enums.Stage;

/**
 * Resource für den Bestellstatus. Es werden alle benötigten Status-Information in dieser Klasse gehalten, wie beispielsweise
 * der aktuelle Status und der zugehörige Zeitstempel.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "OrderStage" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class OrderStage extends AbstractRessourceObject
{

	private static final long serialVersionUID = -172518963268789237L;

	private Integer id;

	private Stage stage;

	private Date createDate;

	private Order order;

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
	 * Liefert das Attribut stage.
	 * 
	 * @return stage
	 */
	public Stage getStage()
	{
		return stage;
	}

	/**
	 * Setzt das Attribut stage.
	 * 
	 * @param stage
	 *            zu setzender Wert für das Attribut stage
	 */
	public void setStage( Stage stage )
	{
		this.stage = stage;
	}

	/**
	 * Liefert das Attribut createDate.
	 * 
	 * @return createDate
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Setzt das Attribut createDate.
	 * 
	 * @param createDate
	 *            zu setzender Wert für das Attribut createDate
	 */
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
	}

	/**
	 * Liefert das Attribut order.
	 * 
	 * @return order
	 */
	public Order getOrder()
	{
		return order;
	}

	/**
	 * Setzt das Attribut order.
	 * 
	 * @param order
	 *            zu setzender Wert für das Attribut order
	 */
	public void setOrder( Order order )
	{
		this.order = order;
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
