package edu.hm.lip.pizza.bean.database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;
import edu.hm.lip.pizza.internal.object.query.DriverQueryConstants;
import edu.hm.lip.pizza.internal.object.query.GPSDataQueryConstants;

/**
 * Bean für den Datenbankzugriff auf die GPSData Entität.
 * 
 * @author Franz Mathauser
 */
@Stateless
public class GPSDataDAO extends AbstractBean implements IGPSDataDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#create(edu.hm.lip.pizza.internal.object.entity.EntityGPSData)
	 */
	@Override
	public EntityGPSData create( EntityGPSData gpsData )
	{
		em.persist( gpsData );
		em.flush();
		return gpsData;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#readAll()
	 */
	@Override
	public List<EntityGPSData> readAll()
	{
		TypedQuery<EntityGPSData> query = em.createNamedQuery( GPSDataQueryConstants.GET_ALL_GPSDATA, EntityGPSData.class );
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#read(int)
	 */
	@Override
	public EntityGPSData read( int id )
	{
		return em.find( EntityGPSData.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#update(edu.hm.lip.pizza.internal.object.entity.EntityGPSData)
	 */
	@Override
	public EntityGPSData update( EntityGPSData gpsData )
	{
		gpsData = em.merge( gpsData );
		em.flush();
		return gpsData;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#delete(edu.hm.lip.pizza.internal.object.entity.EntityGPSData)
	 */
	@Override
	public void delete( EntityGPSData gpsData )
	{
		em.remove( gpsData );
		em.flush();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#getDriversLastPositions()
	 */
	@Override
	public List<EntityGPSData> getDriversLastPositions()
	{
		TypedQuery<EntityDriver> queryDriver = em.createNamedQuery( DriverQueryConstants.GET_ALL_DRIVERS, EntityDriver.class );
		List<EntityDriver> drivers = queryDriver.getResultList();

		List<EntityGPSData> lastPositions = new ArrayList<EntityGPSData>();

		if (drivers != null)
		{
			for (EntityDriver driver : drivers)
			{
				TypedQuery<EntityGPSData> query = em.createNamedQuery( GPSDataQueryConstants.GET_DRIVERS_LAST_POSITIONS,
						EntityGPSData.class );
				query.setParameter( "driver", driver );
				query.setFirstResult( 0 );
				query.setMaxResults( 1 );
				List<EntityGPSData> gpsData = query.getResultList();

				if (gpsData != null && gpsData.size() > 0)
				{
					lastPositions.add( gpsData.get( 0 ) );
				}
			}
		}

		return lastPositions;
	}
}
