package edu.hm.basic.object;

/**
 * Basis Konstanten fuer alle Klassen, Objekte, usw.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class BasicConstants
{

	/**
	 * Privater Konstruktor.
	 */
	private BasicConstants()
	{

	}

	/**
	 * Standard Context Factory.
	 */
	public static final String RMI_CONTEXT_FACTORY = "org.jboss.security.jndi.JndiLoginInitialContextFactory";

	/**
	 * HTTP Context Factory.
	 */
	public static final String HTTP_CONTEXT_FACTORY = "org.jboss.naming.HttpNamingContextFactory";

	/**
	 * PKG Prefix.
	 */
	public static final String URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";

}
