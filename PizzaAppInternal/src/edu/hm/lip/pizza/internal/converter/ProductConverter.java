package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.enums.Size;
import edu.hm.lip.pizza.api.object.resources.XYZProduct;
import edu.hm.lip.pizza.api.object.resources.ProductConfiguration;
import edu.hm.lip.pizza.api.object.resources.Product;
import edu.hm.lip.pizza.internal.object.entities.EntityProduct;
import edu.hm.lip.pizza.internal.object.entities.EntityProductConfiguration;

/**
 * Konverter-Klasse fuer Product-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser
 */
public final class ProductConverter
{

	private ProductConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Product-Objekt in ein Product-Objekt im Service-Datenmodell.
	 * 
	 * @param eProduct
	 *            Entity-Product-Objekt
	 * @return Product-Objekt aus dem Service-Datenmodell
	 */
	public static Product convertEntityToServiceProduct( EntityProduct eProduct )
	{
		if (eProduct == null)
		{
			return null;
		}

		Product product = new Product();
		product.setId( eProduct.getId() );
		product.setName( eProduct.getName() );
		product.setImageUrl( eProduct.getImageUrl() );
		product.setDescription( eProduct.getDescription() );

		for (EntityProductConfiguration configuration : eProduct.getConfigurations())
		{
			switch (configuration.getSize())
			{
				case L:
					product.setPriceL( configuration.getPrice().toString() );
					break;
				case XL:
					product.setPriceXL( configuration.getPrice().toString() );
					break;
				case XXL:
					product.setPriceXXL( configuration.getPrice().toString() );
					break;
				default:
					break;
			}

		}

		return product;

	}

	/**
	 * Konvertiert ein Product-Objekt aus dem Service-Datenmodell in ein Entity-Product-Objekt.
	 * 
	 * @param product
	 *            Product-Objekt aus dem Service-Datenmodell
	 * @return Entity-Product-Objekt
	 */
	public static EntityProduct convertServiceToEntityProduct( Product product )
	{
		if (product == null)
		{
			return null;
		}

		EntityProduct eProduct = new EntityProduct();
		eProduct.setId( product.getId() );
		eProduct.setName( product.getName() );
		eProduct.setImageUrl( product.getImageUrl() );
		eProduct.setDescription( product.getDescription() );

		List<EntityProductConfiguration> productConfigurations = new ArrayList<EntityProductConfiguration>();

		if (product.getPriceL() != null && !product.getPriceL().isEmpty())
		{
			EntityProductConfiguration eProductConfiguration = new EntityProductConfiguration();
			eProductConfiguration.setProduct( eProduct );
			eProductConfiguration.setSize( Size.L );
			eProductConfiguration.setPrice( Double.parseDouble( product.getPriceL() ) );
			productConfigurations.add( eProductConfiguration );
		}
		if (product.getPriceL() != null && !product.getPriceXL().isEmpty())
		{
			EntityProductConfiguration eProductConfiguration = new EntityProductConfiguration();
			eProductConfiguration.setProduct( eProduct );
			eProductConfiguration.setSize( Size.XL );
			eProductConfiguration.setPrice( Double.parseDouble( product.getPriceXL() ) );
			productConfigurations.add( eProductConfiguration );
		}
		if (product.getPriceL() != null && !product.getPriceXXL().isEmpty())
		{
			EntityProductConfiguration eProductConfiguration = new EntityProductConfiguration();
			eProductConfiguration.setProduct( eProduct );
			eProductConfiguration.setSize( Size.XXL );
			eProductConfiguration.setPrice( Double.parseDouble( product.getPriceXXL() ) );
			productConfigurations.add( eProductConfiguration );
		}

		if (productConfigurations.size() > 0)
		{
			eProduct.setConfigurations( productConfigurations );
		}
		return eProduct;

	}

	/**
	 * Konvertiert eine Liste von Entity-Product-Objekten in eine Liste von Product-Objekten im Service-Datenmodell.
	 * 
	 * @param eProductList
	 *            Liste von Entity-Product-Objekten
	 * @return Liste von Product-Objekten aus dem Service-Datenmodell
	 */
	public static List<Product> convertEntityToServiceProductList( List<EntityProduct> eProductList )
	{
		if (eProductList == null || eProductList.isEmpty())
		{
			return null;
		}

		List<Product> productList = new ArrayList<Product>();

		for (EntityProduct eProduct : eProductList)
		{
			productList.add( convertEntityToServiceProduct( eProduct ) );
		}

		return productList;
	}

}
