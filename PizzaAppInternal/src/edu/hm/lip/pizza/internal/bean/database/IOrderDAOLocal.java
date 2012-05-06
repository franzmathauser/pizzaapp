package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.api.object.enums.Stage;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;
import edu.hm.lip.pizza.internal.object.entities.EntityOrderStage;

/**
 * @author Franz Mathauser
 */
@Local
public interface IOrderDAOLocal
{

	EntityOrder create( EntityOrder entityOrder );
	
	List<EntityOrder> readAll();
	
	EntityOrder read(int id);
	
	EntityOrder update(EntityOrder entityOrder);
	
	void delete(EntityOrder entityOrder);
	
	EntityOrderStage getCurrentStage(int id);
	
}