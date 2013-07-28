package br.unirio.projetodswgae.config;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;

public class ControladorSistemas {
	private static String[] NOMES_SISTEMAS;
	
	/**
	 * Retorna os nomes dos sistemas
	 */
	public static String[] getNomesSistemas()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Sistema");
		
		PreparedQuery pq = datastore.prepare(q);
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Entity result : pq.asIterable()) {
			arrayList.add((String) result.getProperty("nome"));
			}
		NOMES_SISTEMAS = arrayList.toArray(new String[arrayList.size()]);
		return NOMES_SISTEMAS;
	}
}
