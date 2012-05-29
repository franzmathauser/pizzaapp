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
 * REST-Service für die GPS-Datendomäne. Verfügbare Aktionen: GET, DELETE
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
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataService#findDriversLastPositions()
	 */
	@Override
	public List<GPSData> findDriversLastPositions()
	{
		// TODO Auto-generated method stub -> Implementieren
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
		return GPSDataConverter.convertEntityToServiceGPSData( gpsDataDAOBean.read( id ) );
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
