package edu.hm.lip.pizza.test.services.rest.proxy;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.heatmap.Heatmap;

import junit.framework.Assert;

/**
 * Testklasse für den Statistic REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class StatisticServiceTest extends AbstractRestServiceProxyTest
{

	/**
	 * Testet die FIND ALL HEATMAP DATA Funktion.
	 */
	@Test
	public void testFindAllHeatmapData()
	{
		Heatmap heatmapFound = getStatisticProxy().findAllHeatmapData();
		Assert.assertNotNull( heatmapFound );

		log( this.getClass(), "Find_All_Heatmap_Data", heatmapFound.toString() );

		Assert.assertNotNull( heatmapFound.getGpsDatas() );
	}

}
