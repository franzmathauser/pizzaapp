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
import edu.hm.lip.pizza.api.object.enumeration.MessageType;

/**
 * Resource für die ActiveMQ. Es wird ein Messagetype angegeben und die auszuliefernde Nachricht.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "ActiveMQMessage" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class MessageContainer extends AbstractRessourceObject
{

	private static final long serialVersionUID = 5176999635823869143L;

	private MessageType messageType;

	private Object message;

	/**
	 * Liefert das Attribut messageType.
	 * 
	 * @return messageType
	 */
	public MessageType getMessageType()
	{
		return messageType;
	}

	/**
	 * Liefert das Attribut message.
	 * 
	 * @return message
	 */
	public Object getMessage()
	{
		return message;
	}

	/**
	 * Setzt das Attribut messageType.
	 * 
	 * @param messageType
	 *            zu setzender Wert für das Attribut messageType
	 */
	public void setMessageType( MessageType messageType )
	{
		this.messageType = messageType;
	}

	/**
	 * Setzt das Attribut message.
	 * 
	 * @param message
	 *            zu setzender Wert für das Attribut message
	 */
	public void setMessage( Object message )
	{
		this.message = message;
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
