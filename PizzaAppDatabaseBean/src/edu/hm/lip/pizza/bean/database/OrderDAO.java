package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderStage;

/**
 * @author Franz Mathauser
 */
@Stateless
public class OrderDAO implements IOrderDAOLocal
{

	@PersistenceContext( unitName = "PizzaAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#create(edu.hm.lip.pizza.internal.object.entities.EntityOrder)
	 */
	@Override
	public EntityOrder create( EntityOrder entityOrder )
	{
		em.persist( entityOrder );
		em.flush();
		return entityOrder;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#readAll()
	 */
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
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#update(edu.hm.lip.pizza.internal.object.entities.EntityOrder)
	 */
	@Override
	public EntityOrder update( EntityOrder entityOrder )
	{
		entityOrder = em.merge( entityOrder );
		em.flush();
		return entityOrder;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.database.IOrderDAOLocal#delete(edu.hm.lip.pizza.internal.object.entities.EntityOrder)
	 */
	@Override
	public void delete( EntityOrder entityOrder )
	{
		em.remove( entityOrder );
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

}
