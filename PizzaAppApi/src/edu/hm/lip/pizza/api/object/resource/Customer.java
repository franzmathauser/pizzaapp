package edu.hm.lip.pizza.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;
import edu.hm.lip.pizza.api.object.enumeration.Gender;

/**
 * Resource für die Kunden. Es werden alle benötigten Kunden-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, die Adresse und eMail-Adresse.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "Customer" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class Customer extends AbstractRessourceObject
{

	private static final long serialVersionUID = 2338873171248303915L;

	private Integer id;

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

	private String lat;

	private String lon;

	/**
	 * Liefert die Adresse der Kunden als Stringzeile.
	 * 
	 * @return Adresse als String
	 */
	public String getAddressAsString()
	{
		return getStreet() + ", " + getZipcode() + " " + getCity();
	}

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
	 * Liefert das Attribut lat.
	 * 
	 * @return lat
	 */
	public String getLat()
	{
		return lat;
	}

	/**
	 * Setzt das Attribut lat.
	 * 
	 * @param lat
	 *            zu setzender Wert für das Attribut lat
	 */
	public void setLat( String lat )
	{
		this.lat = lat;
	}

	/**
	 * Liefert das Attribut lon.
	 * 
	 * @return lon
	 */
	public String getLon()
	{
		return lon;
	}

	/**
	 * Setzt das Attribut lon.
	 * 
	 * @param lon
	 *            zu setzender Wert für das Attribut lon
	 */
	public void setLon( String lon )
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
				this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), null );
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
		rsb.setExcludeFieldNames( null );
		return rsb.toString();
	}

}
