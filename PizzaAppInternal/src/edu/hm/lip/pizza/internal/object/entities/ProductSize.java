package edu.hm.lip.pizza.internal.object.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.hm.lip.pizza.internal.object.enums.Size;

@Entity
public class ProductSize
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;
	
	@Enumerated( value = EnumType.ORDINAL )
	private Size size;

	private Double price;

	@ManyToOne( fetch = FetchType.LAZY )
	private Product product;

	public Size getSize()
	{
		return size;
	}

	public void setSize( Size size )
	{
		this.size = size;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice( Double price )
	{
		this.price = price;
	}

	public Product getProducts()
	{
		return product;
	}

	public void setProduct( Product product )
	{
		this.product = product;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "ProductSize [id=" + id + ", size=" + size + ", price=" + price + ", product=" + product + "]";
	}
	
	

}
