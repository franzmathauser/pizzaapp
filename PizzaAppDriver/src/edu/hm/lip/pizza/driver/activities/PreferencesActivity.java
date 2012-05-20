package edu.hm.lip.pizza.driver.activities;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;

import edu.hm.lip.pizza.driver.PreferencesConstants;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.exceptions.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exceptions.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resources.Driver;
import edu.hm.lip.pizza.driver.util.HttpConnector;
import edu.hm.lip.pizza.driver.util.JsonMapper;

/**
 * Diese Klasse repräsentiert die Einstellungs-Activity der Applikation.
 * 
 * @author Stefan Wörner
 */
public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{

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

		// Dynamische Preferences initialisieren
		initializeDynamicPreferences();
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

		// Über alle Preferences iterieren ...
		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++)
		{
			// ... und updateSummary Methode aufrufen um aktuellen Wert anzuzeigen
			updatePreferenceSummary( getPreferenceScreen().getPreference( i ) );
		}

		// Dynamische Preferences (neu) laden
		loadDynamicPreferences();

		// Für Änderungen an den Einstellungen registrieren, um ggf. die Summary anzupassen
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
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
		Preference preference = findPreference( key );

		// updateSummary Methode aufrufen um ggf. Werte zu aktualisieren
		updatePreferenceSummary( preference );

		if (getString( R.string.pref_category_server_hostname_key ).equals( preference.getKey() )
				|| getString( R.string.pref_category_server_port_key ).equals( preference.getKey() ))
		{
			loadDynamicPreferences();
		}
	}

	private void initializeDynamicPreferences()
	{
		// Driver ListPreference auslesen
		ListPreference driverPref = (ListPreference) findPreference( getString( R.string.pref_category_driver_id_key ) );

		// Dummy-Werte erzeugen (Leer)
		String[] driverIds = new String[0];
		String[] driverNames = new String[0];

		// Werte in der ListPreference ablegen
		driverPref.setEntryValues( driverIds );
		driverPref.setEntries( driverNames );
	}

	private void loadDynamicPreferences()
	{
		// TODO Server Request asynchron -> Service

		List<Driver> drivers = null;

		try
		{
			// Pfad zusammenbauen
			StringBuilder path = new StringBuilder();
			path.append( "drivers" );

			// GetRequest absetzen
			HttpResponse response = HttpConnector.doGetRequest( path.toString(), MediaType.APPLICATION_JSON );

			// Liste mit Driver Objekten erzeugen
			drivers = JsonMapper.fromJSONArray( response.getEntity().getContent(), Driver.class );
		}
		catch (HostnameNotSetException e)
		{
			// TODO Benachrichtigung oder ignorieren?
			return;
		}
		catch (HttpStatusCodeException e)
		{
			// TODO Benachrichtigung oder ignorieren?
			Log.e( this.getClass().getName(), e.getMessage() );
		}
		catch (IOException e)
		{
			// TODO Benutzerbenachrichtigung
			Log.e( this.getClass().getName(), e.getMessage() );

			for (StackTraceElement element : e.getStackTrace())
			{
				Log.e( this.getClass().getName(), element.toString() );
			}
		}

		if (drivers != null)
		{
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
}
