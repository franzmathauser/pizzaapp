package edu.hm.lip.pizza.api.object.ressources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.enums.Stage;


@XmlRootElement( name = "OrderStage" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class OrderStage
{
	private Integer id;
	
	private Stage stage;
	
	private Date createDate;
	
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
