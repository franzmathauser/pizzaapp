package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.hm.lip.pizza.api.object.enums.Size;

@Entity
public class ProductConfiguration
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( value = EnumType.ORDINAL )
	private Size size;

	private Double price;

	@ManyToOne( fetch = FetchType.LAZY )
	private Product product;
	
	@OneToMany( mappedBy = "productConfiguration" )
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
	 * 			zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<OrderLine> orderLines )
	{
		this.orderLines = orderLines;
	}



}
