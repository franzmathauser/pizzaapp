package edu.hm.lip.pizza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Driver
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	@OneToMany(mappedBy="driver")
	private List<GPSData> gpsData;
	
	@OneToMany(mappedBy="driver")
	private List<Order> orders;

	
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

	
	public List<GPSData> getGpsData()
	{
		return gpsData;
	}

	
	public void setGpsData( List<GPSData> gpsData )
	{
		this.gpsData = gpsData;
	}





	public List<Order> getOrders()
	{
		return orders;
	}


	public void setOrders( List<Order> orders )
	{
		this.orders = orders;
	}
	
	
	@Override
	public String toString()
	{
		return "Driver [id=" + id + ", name=" + name + ", gpsData=" + gpsData + ", orders=" + orders + "]";
	}

}
