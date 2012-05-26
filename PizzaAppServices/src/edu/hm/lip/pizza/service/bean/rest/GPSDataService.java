package edu.hm.lip.pizza.service.bean.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IGPSDataService;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal;
import edu.hm.lip.pizza.internal.converter.GPSDataConverter;
import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;

/**
 * REST-Service für die GPS-Datendomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Stateless
public class GPSDataService extends AbstractBean implements IGPSDataService
{

	@EJB
	private IGPSDataDAOLocal gpsDataDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#findAll()
	 */
	@Override
	public List<GPSData> findAll()
	{
		return GPSDataConverter.convertEntityToServiceGPSDataList( gpsDataDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#create(edu.hm.lip.pizza.api.object.resource.GPSData)
	 */
	@Override
	public GPSData create( GPSData gpsData )
	{
		EntityGPSData eGPSData = gpsDataDAOBean.create( GPSDataConverter.convertServiceToEntityGPSData( gpsData ) );
		return GPSDataConverter.convertEntityToServiceGPSData( eGPSData );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#findDriverPositions()
	 */
	@Override
	public List<GPSData> findDriverPositions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#findDriverPositions(int)
	 */
	@Override
	public GPSData findDriverPositions( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#find(int)
	 */
	@Override
	public GPSData find( int id )
	{
		EntityGPSData eGPSData = gpsDataDAOBean.read( id );
		return GPSDataConverter.convertEntityToServiceGPSData( eGPSData );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		EntityGPSData eGPSData = gpsDataDAOBean.read( id );
		if (eGPSData != null)
		{
			gpsDataDAOBean.delete( eGPSData );
		}
	}

}
