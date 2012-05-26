package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;

/**
 * @author Franz Mathauser
 */
@Local
public interface IDriverDAOLocal
{

	EntityDriver create( EntityDriver entityDriver );

	List<EntityDriver> readAll();

	EntityDriver read( int id );

	EntityDriver update( EntityDriver entityDriver );

	void delete( EntityDriver entityDriver );
	
	List<EntityOrder> getUndeliverdOrders(int id);
}
