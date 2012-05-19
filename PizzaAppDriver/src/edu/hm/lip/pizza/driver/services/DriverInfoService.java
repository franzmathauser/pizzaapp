package edu.hm.lip.pizza.driver.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author Stefan Wörner
 */
public class DriverInfoService extends IntentService
{

	/**
	 * Konstruktor.
	 */
	public DriverInfoService()
	{
		super( "DriverInfoService" );
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
