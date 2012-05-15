package edu.hm.lip.pizza.api.object.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;
import edu.hm.lip.pizza.api.object.enums.Size;

/**
 * Resource für die Produkt-Konfiguration. Es werden alle benötigten Produkt-Konfigurations-Information in dieser Klasse
 * gehalten, wie beispielsweise die Produktgröße und der zugehörige Produkt-Größen-Preis.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "ProductConfiguration" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class ProductConfiguration extends AbstractRessourceObject
{

	private static final long serialVersionUID = -7082424038218537628L;

	private Integer id;

	private Size size;

	private Double price;

	private Product product;

	private List<OrderLine> orderLines;

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
	 * Liefert das Attribut size.
	 * 
	 * @return size
	 */
	public Size getSize()
	{
		return size;
	}

	/**
	 * Setzt das Attribut size.
	 * 
	 * @param size
	 *            zu setzender Wert für das Attribut size
	 */
	public void setSize( Size size )
	{
		this.size = size;
	}

	/**
	 * Liefert das Attribut price.
	 * 
	 * @return price
	 */
	public Double getPrice()
	{
		return price;
	}

	/**
	 * Setzt das Attribut price.
	 * 
	 * @param price
	 *            zu setzender Wert für das Attribut price
	 */
	public void setPrice( Double price )
	{
		this.price = price;
	}

	/**
	 * Liefert das Attribut product.
	 * 
	 * @return product
	 */
	public Product getProduct()
	{
		return product;
	}

	/**
	 * Setzt das Attribut product.
	 * 
	 * @param product
	 *            zu setzender Wert für das Attribut product
	 */
	public void setProduct( Product product )
	{
		this.product = product;
	}

	/**
	 * Liefert das Attribut orderLines.
	 * 
	 * @return orderLines
	 */
	public List<OrderLine> getOrderLines()
	{
		return orderLines;
	}

	/**
	 * Setzt das Attribut orderLines.
	 * 
	 * @param orderLines
	 *            zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<OrderLine> orderLines )
	{
		this.orderLines = orderLines;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ProductConfiguration [id=" + id + ", size=" + size + ", price=" + price + ", product=" + product
				+ ", orderLines=" + orderLines + "]";
	}

}
