package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.api.object.enums.Stage;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityDriver;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;

/**
 * @author Franz Mathauser
 */
@Stateless
public class DriverDAO extends AbstractBean implements IDriverDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#create(edu.hm.lip.pizza.internal.object.entities.EntityDriver)
	 */
	@Override
	public EntityDriver create( EntityDriver entityDriver )
	{
		em.persist( entityDriver );
		em.flush();
		return entityDriver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#readAll()
	 */
	@Override
	public List<EntityDriver> readAll()
	{
		Query query = em.createQuery( "SELECT d FROM EntityDriver d", EntityDriver.class );
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
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#update(edu.hm.lip.pizza.internal.object.entities.EntityDriver)
	 */
	@Override
	public EntityDriver update( EntityDriver entityDriver )
	{
		entityDriver = em.merge( entityDriver );
		em.flush();
		return entityDriver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal#delete(edu.hm.lip.pizza.internal.object.entities.EntityDriver)
	 */
	@Override
	public void delete( EntityDriver entityDriver )
	{
		em.remove( entityDriver );
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
		Query query = em.createQuery(
				"SELECT o FROM EntityOrder o where o.driver.id = :driver_id and not exists (from EntityOrderStage as s where s.order = o "
						+ "AND s.stage = " + Stage.DELIVERED.ordinal() + ")", EntityOrder.class );
		query.setParameter( "driver_id", id );
		return query.getResultList();
	}

}
