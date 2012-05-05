package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entities.Product;

/**
 * @author Franz Mathauser
 */
@Local
public interface IProductDAOLocal
{

	Product create( Product product );
	
	List<Product> readAll();
	
	Product read(int id);
	
	Product update(Product product);
	
	void delete(Product product);
	
}
