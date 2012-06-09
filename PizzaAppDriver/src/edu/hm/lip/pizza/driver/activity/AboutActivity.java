package edu.hm.lip.pizza.driver.activity;

import android.app.Activity;
import android.os.Bundle;

import edu.hm.lip.pizza.driver.R;

/**
 * Diese Klasse repräsentiert die About-Activity der Applikation.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class AboutActivity extends Activity
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.about );
	}

}
