package edu.hm.lip.pizza.driver.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Service-Klasse welche die Routeninformationen vom Backend holt.
 * 
 * @author Stefan Wörner
 */
public class RouteService extends IntentService
{

	/**
	 * Konstruktor.
	 */
	public RouteService()
	{
		super( RouteService.class.getName() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent( Intent arg0 )
	{
		// TODO Auto-generated method stub

	}

}
