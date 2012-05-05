package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * @author Franz Mathauser
 */
@Local
public interface ICustomerDAOLocal
{

	EntityCustomer create( EntityCustomer entityCustomer );

	List<EntityCustomer> readAll();

	EntityCustomer read( int id );

	EntityCustomer update( EntityCustomer entityCustomer );

	void delete( EntityCustomer entityCustomer );
}
