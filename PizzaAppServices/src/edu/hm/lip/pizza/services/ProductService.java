package edu.hm.lip.pizza.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.object.ressources.Product;
import edu.hm.lip.pizza.api.request.IProductServiceLocal;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.converter.ProductConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityProduct;

/**
 * @author Franz Mathauser
 */
@Stateless
public class ProductService implements IProductServiceLocal
{

	@EJB
	private IProductDAOLocal productDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.request.IProductServiceLocal#findAll()
	 */
	@Override
	public List<Product> findAll()
	{
		List<EntityProduct> list = productDAOBean.readAll();
		return ProductConverter.convertEntityToServiceProductList( list );
	}

}
