package edu.hm.lip.pizza.driver.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Stefan Wörner
 */
public final class JsonMapper
{

	private ObjectMapper m_objectMapper;

	private static JsonMapper m_instance;

	private JsonMapper()
	{
		m_objectMapper = new ObjectMapper();
	}

	private static JsonMapper getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new JsonMapper();
		}
		return m_instance;
	}

	/**
	 * @param resourceObject
	 * @return
	 * @throws IOException
	 */
	public static String toJSON( final Object resourceObject ) throws IOException
	{
		return getInstance().m_objectMapper.writeValueAsString( resourceObject );
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T fromJSON( final InputStream json, final Class<T> clazz ) throws IOException
	{
		return getInstance().m_objectMapper.readValue( json, clazz );
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> fromJSONArray( final InputStream json, final Class<T> clazz ) throws IOException
	{
		JavaType type = getInstance().m_objectMapper.getTypeFactory().constructCollectionType( List.class, clazz );
		return getInstance().m_objectMapper.readValue( json, type );
	}
}
