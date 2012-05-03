package edu.hm.lip.pizza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(value=EnumType.ORDINAL)
	private Gender gener;
	
	private String company;
	private String department;
	private String lastname;
	private String forename;
	private String street;
	private String zipcode;
	private String city;
	private String level;
	private String phone;
	private String email;
	
	@OneToMany(mappedBy="customer")
	private List<Order> orders;

	
	public Integer getId()
	{
		return id;
	}

	
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	public Gender getGener()
	{
		return gener;
	}

	
	public void setGener( Gender gener )
	{
		this.gener = gener;
	}

	
	public String getCompany()
	{
		return company;
	}

	
	public void setCompany( String company )
	{
		this.company = company;
	}

	
	public String getDepartment()
	{
		return department;
	}

	
	public void setDepartment( String department )
	{
		this.department = department;
	}

	
	public String getLastname()
	{
		return lastname;
	}

	
	public void setLastname( String lastname )
	{
		this.lastname = lastname;
	}

	
	public String getForename()
	{
		return forename;
	}

	
	public void setForename( String forename )
	{
		this.forename = forename;
	}

	
	public String getStreet()
	{
		return street;
	}

	
	public void setStreet( String street )
	{
		this.street = street;
	}

	
	public String getZipcode()
	{
		return zipcode;
	}

	
	public void setZipcode( String zipcode )
	{
		this.zipcode = zipcode;
	}

	
	public String getCity()
	{
		return city;
	}

	
	public void setCity( String city )
	{
		this.city = city;
	}

	
	public String getLevel()
	{
		return level;
	}

	
	public void setLevel( String level )
	{
		this.level = level;
	}

	
	public String getPhone()
	{
		return phone;
	}

	
	public void setPhone( String phone )
	{
		this.phone = phone;
	}

	
	public String getEmail()
	{
		return email;
	}

	
	public void setEmail( String email )
	{
		this.email = email;
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
		return "Customer [id=" + id + ", gener=" + gener + ", company=" + company + ", department=" + department + ", lastname="
				+ lastname + ", forename=" + forename + ", street=" + street + ", zipcode=" + zipcode + ", city=" + city
				+ ", level=" + level + ", phone=" + phone + ", email=" + email + ", orders=" + orders + "]";
	}
	
	
	
}
