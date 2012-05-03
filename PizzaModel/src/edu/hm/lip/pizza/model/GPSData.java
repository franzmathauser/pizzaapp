package edu.hm.lip.pizza.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class GPSData
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Double lat;
	private Double lon;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Driver driver;

	
	public Integer getId()
	{
		return id;
	}

	
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	public Double getLat()
	{
		return lat;
	}

	
	public void setLat( Double lat )
	{
		this.lat = lat;
	}

	
	public Double getLon()
	{
		return lon;
	}

	
	public void setLon( Double lon )
	{
		this.lon = lon;
	}

	
	public Date getDate()
	{
		return date;
	}

	
	public void setDate( Date date )
	{
		this.date = date;
	}

	
	public Driver getDriver()
	{
		return driver;
	}

	
	public void setDriver( Driver driver )
	{
		this.driver = driver;
	}


	@Override
	public String toString()
	{
		return "GPSData [id=" + id + ", lat=" + lat + ", lon=" + lon + ", date=" + date + ", driver=" + driver + "]";
	}
	
	
}
