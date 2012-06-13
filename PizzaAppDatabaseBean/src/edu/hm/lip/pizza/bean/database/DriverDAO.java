package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.query.DriverQueryConstants;
import edu.hm.lip.pizza.internal.object.query.OrderQueryConstants;

/**
 * Bean für den Datenbankzugriff auf die Driver Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class DriverDAO extends AbstractBean implements IDriverDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#create(edu.hm.lip.pizza.internal.object.entity.EntityDriver)
	 */
	@Override
	public EntityDriver create( EntityDriver driver )
	{
		em.persist( driver );
		em.flush();
		return driver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#readAll()
	 */
	@Override
	public List<EntityDriver> readAll()
	{
		TypedQuery<EntityDriver> query = em.createNamedQuery( DriverQueryConstants.GET_ALL_DRIVERS, EntityDriver.class );
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#read(int)
	 */
	@Override
	public EntityDriver read( int id )
	{
		return em.find( EntityDriver.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#update(edu.hm.lip.pizza.internal.object.entity.EntityDriver)
	 */
	@Override
	public EntityDriver update( EntityDriver driver )
	{
		driver = em.merge( driver );
		em.flush();
		return driver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#delete(edu.hm.lip.pizza.internal.object.entity.EntityDriver)
	 */
	@Override
	public void delete( EntityDriver driver )
	{
		em.remove( driver );
		em.flush();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#getUndeliverdOrders(int)
	 */
	@Override
	public List<EntityOrder> getUndeliverdOrders( int id )
	{
		TypedQuery<EntityOrder> query = em.createNamedQuery( OrderQueryConstants.ORDERS_BY_DRIVER_AND_STAGE, EntityOrder.class );
		query.setParameter( "driver_id", id );
		query.setParameter( "stage", Stage.DELIVERED );
		return query.getResultList();
	}

}
