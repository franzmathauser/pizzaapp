package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;

/**
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
	public EntityGPSData create( EntityGPSData entityGPSData )
	{
		em.persist( entityGPSData );
		em.flush();
		return entityGPSData;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#readAll()
	 */
	@Override
	public List<EntityGPSData> readAll()
	{
		Query query = em.createQuery( "SELECT g FROM EntityGPSData g", EntityGPSData.class );
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
	public EntityGPSData update( EntityGPSData entityGPSData )
	{
		entityGPSData = em.merge( entityGPSData );
		em.flush();
		return entityGPSData;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IGPSDataDAOLocal#delete(edu.hm.lip.pizza.internal.object.entity.EntityGPSData)
	 */
	@Override
	public void delete( EntityGPSData entityGPSData )
	{
		em.remove( entityGPSData );
		em.flush();

	}

}
