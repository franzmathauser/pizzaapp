package edu.hm.lip.pizza.test.services.rest.pure;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.heatmap.Heatmap;
import edu.hm.lip.pizza.test.JsonMapper;

import junit.framework.Assert;

/**
 * Testklasse für den Statistic REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class StatisticServiceTest extends AbstractRestServicePureTest
{

	/**
	 * Testet die FIND ALL HEATMAP DATA Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testFindAllHeatmapData() throws Exception
	{
		ClientResponse<String> response = getClient( "statistics/heatmap" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Heatmap heatmapFound = JsonMapper.fromJSON( response.getEntity(), Heatmap.class );
		Assert.assertNotNull( heatmapFound );

		log( this.getClass(), "Find_All_Heatmap_Data", heatmapFound.toString() );

		Assert.assertNotNull( heatmapFound.getGpsDatas() );
	}

}
