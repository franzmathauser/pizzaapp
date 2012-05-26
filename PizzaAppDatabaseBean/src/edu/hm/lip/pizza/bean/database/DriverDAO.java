package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;

/**
 * Bean für den Datenbankzugriff auf die Driver Entität.
 * 
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
	@SuppressWarnings( "unchecked" )
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
	@SuppressWarnings( "unchecked" )
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
