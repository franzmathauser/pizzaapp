package edu.hm.lip.pizza.api.object.resource.heatmap;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Resource als Container für Heatmap - GPSDaten.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "Heatmap" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class Heatmap extends AbstractRessourceObject
{

	private static final long serialVersionUID = -9118515781116665793L;

	private List<GPSData> gpsDatas = new ArrayList<GPSData>();

	/**
	 * Speichert ein GPSData-Objekt in der Liste.
	 * 
	 * @param gpsData
	 *            GPS-Koordinate
	 */
	public void add( GPSData gpsData )
	{
		gpsDatas.add( gpsData );
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

	/**
	 * Liefert das Attribut gpsDatas.
	 * 
	 * @return gpsDatas
	 */
	public List<GPSData> getGpsDatas()
	{
		return gpsDatas;
	}

	/**
	 * Setzt das Attribut gpsDatas.
	 * 
	 * @param gpsDatas
	 *            zu setzender Wert für das Attribut gpsDatas
	 */
	public void setGpsDatas( List<GPSData> gpsDatas )
	{
		this.gpsDatas = gpsDatas;
	}

}
