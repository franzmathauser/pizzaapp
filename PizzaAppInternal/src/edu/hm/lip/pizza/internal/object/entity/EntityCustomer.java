package edu.hm.lip.pizza.internal.object.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für die Kunden. Es werden alle benötigten Kunden-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, die Adresse und eMail-Adresse.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "customer" )
public class EntityCustomer extends AbstractEntityObject
{

	private static final long serialVersionUID = 3895442825911877353L;

	private static final String LASTNAME_EMPTY_ERROR_MSG = "Bitte geben sie einen Nachnamen an.";

	private static final String FORENAME_EMPTY_ERROR_MSG = "Bitte geben sie einen Vornamen an.";

	private static final String STREET_EMPTY_ERROR_MSG = "Bitte geben sie eine Straße an.";

	private static final String CITY_EMPTY_ERROR_MSG = "Bitte geben sie einen Ort an.";

	private static final String PHONE_VALIDATION_ERROR = "Bitte geben sie eine Telefonnummer an.";

	private static final String EMAIL_VALIDATION_ERROR = "Bitte geben sie eine Emailadresse an.";

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( value = EnumType.ORDINAL )
	private Gender gender;

	private String company;

	private String department;

	@NotEmpty( message = LASTNAME_EMPTY_ERROR_MSG )
	private String lastname;

	@NotEmpty( message = FORENAME_EMPTY_ERROR_MSG )
	private String forename;

	@NotEmpty( message = STREET_EMPTY_ERROR_MSG )
	private String street;

	@Range( min = 10000, max = 99999 )
	private String zipcode;

	@NotEmpty( message = CITY_EMPTY_ERROR_MSG )
	private String city;

	private String level;

	@NotEmpty( message = PHONE_VALIDATION_ERROR )
	private String phone;

	@NotEmpty( message = EMAIL_VALIDATION_ERROR )
	private String email;

	@OneToMany( mappedBy = "customer", fetch = FetchType.LAZY )
	private List<EntityOrder> orders;

	private Double lat;

	private Double lon;

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
	 * Setzt das Attribut gender.
	 * 
	 * @param gender
	 *            zu setzender Wert für das Attribut gender
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
		return orders;
	}

	/**
	 * Setzt das Attribut orders.
	 * 
	 * @param orders
	 *            zu setzender Wert für das Attribut orders
	 */
	public void setOrders( List<EntityOrder> orders )
	{
		this.orders = orders;
	}

	/**
	 * Liefert das Attribut lat.
	 * 
	 * @return lat
	 */
	public Double getLat()
	{
		return lat;
	}

	/**
	 * Setzt das Attribut lat.
	 * 
	 * @param lat
	 *            zu setzender Wert für das Attribut lat
	 */
	public void setLat( Double lat )
	{
		this.lat = lat;
	}

	/**
	 * Liefert das Attribut lon.
	 * 
	 * @return lon
	 */
	public Double getLon()
	{
		return lon;
	}

	/**
	 * Setzt das Attribut lon.
	 * 
	 * @param lon
	 *            zu setzender Wert für das Attribut lon
	 */
	public void setLon( Double lon )
	{
		this.lon = lon;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), new String[] { "orders" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), new String[] { "orders" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( this.getClass() );
		rsb.setExcludeFieldNames( new String[] { "orders" } );
		return rsb.toString();
	}

}
