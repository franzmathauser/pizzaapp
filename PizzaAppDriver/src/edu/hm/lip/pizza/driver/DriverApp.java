package edu.hm.lip.pizza.driver;

import android.app.Application;
import android.content.Context;

/**
 * @author Stefan Wörner
 */
public class DriverApp extends Application
{

	private static Context m_context;

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		DriverApp.m_context = getApplicationContext();
	}

	/**
	 * @return
	 */
	public static Context getAppContext()
	{
		return DriverApp.m_context;
	}
}
