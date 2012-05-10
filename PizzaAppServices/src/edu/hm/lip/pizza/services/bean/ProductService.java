package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IProductServiceLocal;
import edu.hm.lip.pizza.api.object.resources.ProductDTO;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.converter.ProductConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityProduct;

/**
 * @author Franz Mathauser
 */
@Stateless
public class ProductService extends AbstractBean implements IProductServiceLocal
{

	@EJB
	private IProductDAOLocal productDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductServiceLocal#findAll()
	 */
	@Override
	public List<ProductDTO> findAll()
	{
		return ProductConverter.convertEntityToServiceProductList( productDAOBean.readAll() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductServiceLocal#create(edu.hm.lip.pizza.api.object.resources.Product)
	 */
	@Override
	public ProductDTO create( ProductDTO product )
	{
		EntityProduct eProduct = productDAOBean.create( ProductConverter.convertServiceToEntityProduct( product ) );
		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductServiceLocal#find(int)
	 */
	@Override
	public ProductDTO find( int id )
	{

		EntityProduct eProduct = productDAOBean.read( id );
		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductServiceLocal#update(edu.hm.lip.pizza.api.object.resources.Product)
	 */
	@Override
	public ProductDTO update( ProductDTO product )
	{
		EntityProduct eProduct = ProductConverter.convertServiceToEntityProduct( product );
		productDAOBean.update( eProduct );

		return ProductConverter.convertEntityToServiceProduct( eProduct );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IProductServiceLocal#remove(int)
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
