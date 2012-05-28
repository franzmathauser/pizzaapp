package edu.hm.lip.pizza.service.bean.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IProductService;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.converter.ProductConverter;
import edu.hm.lip.pizza.internal.object.entity.EntityProduct;

/**
 * REST-Service für die Produktdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Stateless
public class ProductService extends AbstractBean implements IProductService
{

	@EJB
	private IProductDAOLocal productDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductService#findAll()
	 */
	@Override
	public List<Product> findAll()
	{
		return ProductConverter.convertEntityToServiceProductList( productDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductService#create(edu.hm.lip.pizza.api.object.resource.Product)
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
	 * @see edu.hm.lip.pizza.api.communication.request.IProductService#find(int)
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
	 * @see edu.hm.lip.pizza.api.communication.request.IProductService#update(int,
	 *      edu.hm.lip.pizza.api.object.resource.Product)
	 */
	@Override
	public Product update( int id, Product product )
	{
		if (id != product.getId())
		{
			return null;
		}

		EntityProduct eProduct = ProductConverter.convertServiceToEntityProduct( product );
		productDAOBean.update( eProduct );

		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductService#remove(int)
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
