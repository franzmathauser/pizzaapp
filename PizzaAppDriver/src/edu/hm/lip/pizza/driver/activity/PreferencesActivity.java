package edu.hm.lip.pizza.driver.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import edu.hm.lip.pizza.driver.PreferencesConstants;
import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.objects.resource.Driver;
import edu.hm.lip.pizza.driver.service.DriverInfoService;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;

/**
 * Diese Klasse repräsentiert die Einstellungs-Activity der Applikation.
 * 
 * @author Stefan Wörner
 */
public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{

	private ProgressDialog m_driverInfoProgressDialog;

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		// Dateinamen der Preferences festlegen -> wird auch an anderen Stellen verwendet, daher muss der Default-Wert
		// überschrieben werden
		getPreferenceManager().setSharedPreferencesName( PreferencesConstants.FILENAME );

		// Preference aus XML-Datei hinzufügen
		addPreferencesFromResource( R.xml.preferences );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();

		// Dynamische Preferences (neu) laden
		loadDynamicPreferences();

		// Über alle Preferences iterieren ...
		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++)
		{
			// ... und updateSummary Methode aufrufen um aktuellen Wert anzuzeigen
			updatePreferenceSummary( getPreferenceScreen().getPreference( i ) );
		}

		// Für Änderungen an den Einstellungen registrieren, um ggf. die Summary anzupassen
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );

		// BroadcastReceiver für DriverInfo Service registrieren
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction( DriverInfoService.TRANSACTION_DONE );
		registerReceiver( m_driverInfoReceiver, intentFilter );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause()
	{
		super.onPause();

		// Deregistrieren des PreferenceChangeListeners
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );

		// BroadcastReceiver für DriverInfo Service deregistrieren
		unregisterReceiver( m_driverInfoReceiver );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.content.SharedPreferences.OnSharedPreferenceChangeListener#onSharedPreferenceChanged(android.content.SharedPreferences,
	 *      java.lang.String)
	 */
	@Override
	public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key )
	{
		// Prüfen ob die Fahrerliste aktuelisiert wurde, falls ja muss nichts unternommen werden
		if (getString( R.string.pref_category_driver_list_key ).equals( key ))
		{
			return;
		}

		// Falls Hostname oder Port geändert wurde muss nachgeschaut werden ob neue/andere Fahrer verfügbar sind
		if (getString( R.string.pref_category_server_hostname_key ).equals( key )
				|| getString( R.string.pref_category_server_port_key ).equals( key ))
		{
			loadDynamicPreferences();
		}

		// updateSummary Methode aufrufen um ggf. Werte zu aktualisieren
		updatePreferenceSummary( findPreference( key ) );
	}

	private void loadDynamicPreferences()
	{
		updateDriverListPreference();

		Intent intent = new Intent( this, DriverInfoService.class );
		startService( intent );

		m_driverInfoProgressDialog = ProgressDialog.show( this, getString( R.string.service_check_connection_title ),
				getString( R.string.service_check_connection_message ) );
	}

	private void updateDriverListPreference()
	{
		List<Driver> drivers = PreferencesStore.getDriverListPreference();

		// Driver ListPreference auslesen
		ListPreference driverPref = (ListPreference) findPreference( getString( R.string.pref_category_driver_id_key ) );

		String[] driverIds = new String[drivers.size()];
		String[] driverNames = new String[drivers.size()];

		// Entries und EntryValues befüllen
		for (int i = 0; i < drivers.size(); i++)
		{
			driverIds[i] = drivers.get( i ).getId().toString();
			driverNames[i] = drivers.get( i ).getName();
		}

		// Werte in der ListPreference ablegen
		driverPref.setEntryValues( driverIds );
		driverPref.setEntries( driverNames );
	}

	private void updatePreferenceSummary( Preference preference )
	{
		if (preference == null)
		{
			return;
		}

		// Wenn EditTextPreference ...
		if (preference instanceof EditTextPreference)
		{
			// ... dann casten und Wert anzeigen bzw. aktualisieren
			EditTextPreference editPref = (EditTextPreference) preference;
			if (StringUtils.isNotBlank( editPref.getText() ))
			{
				preference.setSummary( getString( R.string.pref_summary_prefix_text ) + " " + editPref.getText() );
			}
			else
			{
				preference.setSummary( getString( R.string.pref_summary_prefix_text ) + " "
						+ getString( R.string.pref_summary_notset_text ) );
			}
		}
		// Wenn ListPreference ...
		else if (preference instanceof ListPreference)
		{
			// ... dann casten und Wert anzeigen bzw. aktualisieren
			ListPreference listPref = (ListPreference) preference;
			if (listPref.getEntry() != null && StringUtils.isNotBlank( listPref.getEntry().toString() ))
			{
				preference.setSummary( getString( R.string.pref_summary_prefix_text ) + " " + listPref.getEntry() );
			}
			else
			{
				preference.setSummary( getString( R.string.pref_summary_prefix_text ) + " "
						+ getString( R.string.pref_summary_notset_text ) );
			}
		}
		// Wenn PreferenceCategory ...
		else if (preference instanceof PreferenceCategory)
		{
			// ... dann casten und updateSummary Methode rekursiv aufrufen, um alle Preferences der Kategorie anzuzeigen
			// bzw. zu aktualisieren
			PreferenceCategory prefCategory = (PreferenceCategory) preference;
			for (int i = 0; i < prefCategory.getPreferenceCount(); i++)
			{
				updatePreferenceSummary( prefCategory.getPreference( i ) );
			}
		}
	}

	private BroadcastReceiver m_driverInfoReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive( Context context, Intent intent )
		{
			if (intent == null)
			{
				return;
			}

			// Status auslesen
			boolean successful = intent.getBooleanExtra( ExtraConstants.SUCCESSFUL_EXTRA, false );

			if (successful)
			{
				// Auslesen ob Ansicht aktualisiert werden muss
				boolean refresh = intent.getBooleanExtra( ExtraConstants.REFRESH_EXTRA, false );

				if (refresh)
				{
					// Fahrerliste aktualisieren
					updateDriverListPreference();
					// Ausgewählten Fahrer zurücksetzen
					((ListPreference) findPreference( getString( R.string.pref_category_driver_id_key ) )).setValue( null );
				}

				// Progress Dialog schließen
				m_driverInfoProgressDialog.dismiss();
			}
			else
			{
				// Progress Dialog schließen
				m_driverInfoProgressDialog.dismiss();

				// Fehlermeldung auslesen
				String message = intent.getStringExtra( ExtraConstants.ERROR_MSG_EXTRA );
				// Fehlermeldung anzeigen
				showErrorDialog( message );
			}
		}
	};

	private void showErrorDialog( String message )
	{
		// Wenn beide Provider nicht aktiviert sind muss der Benutzer aufgefordert werden diese zu aktivieren
		AlertDialog.Builder builder = new AlertDialog.Builder( this );
		builder.setMessage( message ).setCancelable( false );
		builder.setPositiveButton( R.string.service_errordialog_bt_ok, new DialogInterface.OnClickListener()
		{

			public void onClick( DialogInterface dialog, int id )
			{
				// Wenn OK Button geklickt wird, Dialog schließen
				dialog.cancel();
			}
		} );
		// Hinweisdialog anzeigen
		builder.create().show();
	}

}
