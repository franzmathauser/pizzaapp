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
		return ProductConverter.convertEntityToServiceProductList( productDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.request.IProductServiceLocal#create(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public Product create( Product product )
	{
		EntityProduct eProduct = productDAOBean.create( ProductConverter.convertServiceToEntityProduct( product ) );
		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.request.IProductServiceLocal#find(int)
	 */
	@Override
	public Product find( int id )
	{

		EntityProduct eProduct = productDAOBean.read( id );
		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.request.IProductServiceLocal#update(edu.hm.lip.pizza.api.object.ressources.Product)
	 */
	@Override
	public Product update( Product product )
	{
		EntityProduct eProduct = ProductConverter.convertServiceToEntityProduct( product );
		productDAOBean.update( eProduct );

		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.request.IProductServiceLocal#remove(int)
	 */
	@Override
	public void remove( int id )
	{
		EntityProduct eProduct = productDAOBean.read( id );
		if (eProduct != null)
		{
			productDAOBean.delete( eProduct );
		}

	}

}
