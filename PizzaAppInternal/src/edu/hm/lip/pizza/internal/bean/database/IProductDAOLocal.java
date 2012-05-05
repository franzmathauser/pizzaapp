package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entities.EntityProduct;

/**
 * @author Franz Mathauser
 */
@Local
public interface IProductDAOLocal
{

	EntityProduct create( EntityProduct entityProduct );
	
	List<EntityProduct> readAll();
	
	EntityProduct read(int id);
	
	EntityProduct update(EntityProduct entityProduct);
	
	void delete(EntityProduct entityProduct);
	
}
