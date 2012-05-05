package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entities.Customer;

/**
 * @author Franz Mathauser
 */
@Local
public interface ICustomerDAOLocal
{

	Customer create( Customer customer );

	List<Customer> readAll();

	Customer read( int id );

	Customer update( Customer customer );

	void delete( Customer customer );
}
