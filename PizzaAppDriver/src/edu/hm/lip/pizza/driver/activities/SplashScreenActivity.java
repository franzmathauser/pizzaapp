package edu.hm.lip.pizza.driver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import edu.hm.lip.pizza.driver.R;

/**
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

		// View splashScreen = (View) findViewById( R.id.splash );

		final SplashScreenActivity splashScreen = this;
		
		View splash = (View) findViewById( R.id.splash_id );

		Animation anim1 = new AlphaAnimation( 0.0f, 1.0f );
		anim1.setDuration( 2000 );

		splash.startAnimation( anim1 );

		// The thread to wait for splash screen events
		m_splashThread = new Thread()
		{

			@Override
			public void run()
			{
				try
				{
					synchronized (this)
					{
						// Wait given period of time or exit on touch
						wait( 5000 );
					}
				}
				catch (InterruptedException ex)
				{
					
				}

				finish();

				// Run next activity
				Intent intent = new Intent();
				intent.setClass( splashScreen, MainActivity.class );
				startActivity( intent );
				stop();
			}
		};

		m_splashThread.start();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent( MotionEvent event )
	{
		// TODO Auto-generated method stub
		// return super.onTouchEvent( event );
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
