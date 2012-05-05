package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Product
{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String name;

	private String description;

	private String imageUrl;

	@ManyToMany( mappedBy = "products" )
	private List<Order> orders;

	@OneToMany(mappedBy="product")
	private List<ProductSize> sizes;

	
	public Integer getId()
	{
		return id;
	}

	
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	public String getName()
	{
		return name;
	}

	
	public void setName( String name )
	{
		this.name = name;
	}

	
	public String getDescription()
	{
		return description;
	}

	
	public void setDescription( String description )
	{
		this.description = description;
	}

	
	public String getImageUrl()
	{
		return imageUrl;
	}

	
	public void setImageUrl( String imageUrl )
	{
		this.imageUrl = imageUrl;
	}

	
	public List<Order> getOrders()
	{
		return orders;
	}

	
	public void setOrders( List<Order> orders )
	{
		this.orders = orders;
	}

	
	public List<ProductSize> getSizes()
	{
		return sizes;
	}

	
	public void setSizes( List<ProductSize> sizes )
	{
		this.sizes = sizes;
	}


	@Override
	public String toString()
	{
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + ", orders="
				+ orders + ", sizes=" + sizes + "]";
	}
	
	
}
