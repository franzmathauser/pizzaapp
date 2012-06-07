package edu.hm.lip.pizza.driver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import edu.hm.lip.pizza.driver.AppConstants;
import edu.hm.lip.pizza.driver.R;

/**
 * Diese Klasse repräsentiert die SplahScreen-Activity der Applikation.
 * 
 * @author Stefan Wörner
 */
public class SplashScreenActivity extends Activity
{

	private Thread m_splashThread;

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.splash );

		final SplashScreenActivity splashScreen = this;

		m_splashThread = new Thread()
		{

			@Override
			public void run()
			{
				try
				{
					synchronized (this)
					{
						wait( AppConstants.SPLASH_SCREEN_VISIBLE_INTERVAL );
					}
				}
				catch (InterruptedException e)
				{
					Log.e( this.getClass().getSimpleName(), e.getMessage() );
				}

				finish();

				// Main Avtivity starten
				Intent intent = new Intent();
				intent.setClass( splashScreen, MainActivity.class );
				startActivity( intent );
			}
		};

		m_splashThread.start();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			synchronized (m_splashThread)
			{
				m_splashThread.notifyAll();
			}
		}
		return true;
	}

}
