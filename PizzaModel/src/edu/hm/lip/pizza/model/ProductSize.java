package edu.hm.lip.pizza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ProductSize
{
	@Id
	@Enumerated(value=EnumType.ORDINAL)
	private Size size;
	
	private Double price;
	
	@ManyToMany( mappedBy = "sizes" )
	private List<Product> products;

	
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

	
	public List<Product> getProducts()
	{
		return products;
	}

	
	public void setProducts( List<Product> products )
	{
		this.products = products;
	}


	@Override
	public String toString()
	{
		return "ProductSize [size=" + size + ", price=" + price + ", products=" + products + "]";
	}
	
	
}
