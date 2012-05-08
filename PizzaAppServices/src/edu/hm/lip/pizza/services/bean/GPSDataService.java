package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal;
import edu.hm.lip.pizza.api.object.resources.GPSData;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal;
import edu.hm.lip.pizza.internal.converter.GPSDataConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityGPSData;

/**
 * @author Franz Mathauser
 */
@Stateless
public class GPSDataService extends AbstractBean implements IGPSDataServiceLocal
{

	@EJB
	private IGPSDataDAOLocal gpsDataDAOBean;

	@EJB
	private IDriverDAOLocal driverDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#findAll()
	 */
	@Override
	public List<GPSData> findAll()
	{
		return GPSDataConverter.convertEntityToServiceGPSDataList( gpsDataDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#create(edu.hm.lip.pizza.api.object.resources.GPSData)
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
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#findDriverPositions()
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
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#findDriverPositions(int)
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
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#find(int)
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
	 * @see edu.hm.lip.pizza.api.communication.request.IGPSDataServiceLocal#remove(int)
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