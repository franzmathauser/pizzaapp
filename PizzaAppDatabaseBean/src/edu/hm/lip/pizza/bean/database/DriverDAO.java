package edu.hm.lip.pizza.bean.database;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityDriver;

/**
 * @author Franz Mathauser
 */
@Stateless
public class DriverDAO implements IDriverDAOLocal
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

}
