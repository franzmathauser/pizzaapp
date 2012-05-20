package edu.hm.lip.pizza.driver.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Service-Klasse welche die Trackingdaten an das Backend sendet.
 * 
 * @author Stefan Wörner
 */
public class DriverLocationService extends IntentService
{

	/**
	 * Konstruktor.
	 */
	public DriverLocationService()
	{
		super( "DriverLocationService" );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent( Intent intent )
	{
		// TODO Auto-generated method stub

	}

}
