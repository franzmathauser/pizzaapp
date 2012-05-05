package edu.hm.lip.pizza.internal.object.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.hm.lip.pizza.internal.object.enums.Stage;


@Entity
public class OrderStage
{
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;
	
	@Enumerated(EnumType.ORDINAL)
	private Stage stage;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Order order;

	
	public Integer getId()
	{
		return id;
	}

	
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	public Stage getStage()
	{
		return stage;
	}

	
	public void setStage( Stage stage )
	{
		this.stage = stage;
	}

	
	public Date getCreateDate()
	{
		return createDate;
	}

	
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
	}

	public Order getOrder()
	{
		return order;
	}


	public void setOrder( Order order )
	{
		this.order = order;
	}


	@Override
	public String toString()
	{
		return "OrderStage [id=" + id + ", stage=" + stage + ", createDate=" + createDate + ", order=" + order + "]";
	}
	
	
	
}
