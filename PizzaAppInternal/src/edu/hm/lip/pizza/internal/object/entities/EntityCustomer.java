package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.hm.basic.object.AbstractBasicObject;
import edu.hm.lip.pizza.api.object.enums.Gender;

/**
 * Entität für die Kunden. Es werden alle benötigten Kunden-Information in dieser Klasse gehalten, wie beispielsweise der
 * Name, die Adresse, usw.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "customer" )
public class EntityCustomer extends AbstractBasicObject
{

	private static final long serialVersionUID = -5305461895246325685L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( value = EnumType.ORDINAL )
	private Gender gender;

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

	@OneToMany( mappedBy = "customer" )
	private List<EntityOrder> entityOrders;

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
	 * Liefert das Attribut gender.
	 * 
	 * @return gender
	 */
	public Gender getGender()
	{
		return gender;
	}

	/**
	 * Setzt das Attribut gener.
	 * 
	 * @param gender
	 *            zu setzender Wert für das Attribut gener
	 */
	public void setGender( Gender gender )
	{
		this.gender = gender;
	}

	/**
	 * Liefert das Attribut company.
	 * 
	 * @return company
	 */
	public String getCompany()
	{
		return company;
	}

	/**
	 * Setzt das Attribut company.
	 * 
	 * @param company
	 *            zu setzender Wert für das Attribut company
	 */
	public void setCompany( String company )
	{
		this.company = company;
	}

	/**
	 * Liefert das Attribut department.
	 * 
	 * @return department
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * Setzt das Attribut department.
	 * 
	 * @param department
	 *            zu setzender Wert für das Attribut department
	 */
	public void setDepartment( String department )
	{
		this.department = department;
	}

	/**
	 * Liefert das Attribut lastname.
	 * 
	 * @return lastname
	 */
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * Setzt das Attribut lastname.
	 * 
	 * @param lastname
	 *            zu setzender Wert für das Attribut lastname
	 */
	public void setLastname( String lastname )
	{
		this.lastname = lastname;
	}

	/**
	 * Liefert das Attribut forename.
	 * 
	 * @return forename
	 */
	public String getForename()
	{
		return forename;
	}

	/**
	 * Setzt das Attribut forename.
	 * 
	 * @param forename
	 *            zu setzender Wert für das Attribut forename
	 */
	public void setForename( String forename )
	{
		this.forename = forename;
	}

	/**
	 * Liefert das Attribut street.
	 * 
	 * @return street
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * Setzt das Attribut street.
	 * 
	 * @param street
	 *            zu setzender Wert für das Attribut street
	 */
	public void setStreet( String street )
	{
		this.street = street;
	}

	/**
	 * Liefert das Attribut zipcode.
	 * 
	 * @return zipcode
	 */
	public String getZipcode()
	{
		return zipcode;
	}

	/**
	 * Setzt das Attribut zipcode.
	 * 
	 * @param zipcode
	 *            zu setzender Wert für das Attribut zipcode
	 */
	public void setZipcode( String zipcode )
	{
		this.zipcode = zipcode;
	}

	/**
	 * Liefert das Attribut city.
	 * 
	 * @return city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * Setzt das Attribut city.
	 * 
	 * @param city
	 *            zu setzender Wert für das Attribut city
	 */
	public void setCity( String city )
	{
		this.city = city;
	}

	/**
	 * Liefert das Attribut level.
	 * 
	 * @return level
	 */
	public String getLevel()
	{
		return level;
	}

	/**
	 * Setzt das Attribut level.
	 * 
	 * @param level
	 *            zu setzender Wert für das Attribut level
	 */
	public void setLevel( String level )
	{
		this.level = level;
	}

	/**
	 * Liefert das Attribut phone.
	 * 
	 * @return phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * Setzt das Attribut phone.
	 * 
	 * @param phone
	 *            zu setzender Wert für das Attribut phone
	 */
	public void setPhone( String phone )
	{
		this.phone = phone;
	}

	/**
	 * Liefert das Attribut email.
	 * 
	 * @return email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Setzt das Attribut email.
	 * 
	 * @param email
	 *            zu setzender Wert für das Attribut email
	 */
	public void setEmail( String email )
	{
		this.email = email;
	}

	/**
	 * Liefert das Attribut orders.
	 * 
	 * @return orders
	 */
	public List<EntityOrder> getOrders()
	{
		return entityOrders;
	}

	/**
	 * Setzt das Attribut orders.
	 * 
	 * @param entityOrders
	 *            zu setzender Wert für das Attribut orders
	 */
	public void setOrders( List<EntityOrder> entityOrders )
	{
		this.entityOrders = entityOrders;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		return "Customer [id=" + id + ", gender=" + gender + ", company=" + company + ", department=" + department + ", lastname="
				+ lastname + ", forename=" + forename + ", street=" + street + ", zipcode=" + zipcode + ", city=" + city
				+ ", level=" + level + ", phone=" + phone + ", email=" + email + ", orders=" + entityOrders + "]";
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
}
