package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderStage;

/**
 * Bean für den Datenbankzugriff auf die Order Entität.
 * 
 * @author Franz Mathauser
 */
@Stateless
public class OrderDAO extends AbstractBean implements IOrderDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#create(edu.hm.lip.pizza.internal.object.entity.EntityOrder)
	 */
	@Override
	public EntityOrder create( EntityOrder order )
	{
		em.persist( order );
		em.flush();
		return order;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#readAll()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<EntityOrder> readAll()
	{
		Query query = em.createQuery( "SELECT o FROM EntityOrder o", EntityOrder.class );
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#read(int)
	 */
	@Override
	public EntityOrder read( int id )
	{
		return em.find( EntityOrder.class, id );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#update(edu.hm.lip.pizza.internal.object.entity.EntityOrder)
	 */
	@Override
	public EntityOrder update( EntityOrder order )
	{
		order = em.merge( order );
		em.flush();
		return order;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#delete(edu.hm.lip.pizza.internal.object.entity.EntityOrder)
	 */
	@Override
	public void delete( EntityOrder order )
	{
		em.remove( order );
		em.flush();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#getCurrentStage(int)
	 */
	@Override
	public EntityOrderStage getCurrentStage( int id )
	{
		EntityOrder eOrder = read( id );
		return eOrder.getStages().get( eOrder.getStages().size() - 1 );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#getUndeliveredTasks()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<EntityOrder> getUndeliveredOrders()
	{
		Query query = em.createQuery(
				"SELECT o FROM EntityOrder o where not exists (from EntityOrderStage as s where s.order = o AND s.stage = "
						+ Stage.IN_DELIVERY.ordinal() + ")", EntityOrder.class );
		return query.getResultList();
	}

}
