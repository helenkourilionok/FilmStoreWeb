package by.training.filmstore.service.impl;

import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;

public final class PoolConnectionServiceInitializer {
	
	//logging
		public static void initPoolConnection(){
			try {
				PoolConnection.getInstance();
			} catch (PoolConnectionException e) {
				throw new RuntimeException("JDBC Driver error", e);
			}
		}
		
		public static void destroyPoolConnection(){
			try {
				PoolConnection poolConnection = PoolConnection.getInstance();
				poolConnection.disposePoolConnection();
			} catch (PoolConnectionException e) {
				throw new RuntimeException("JDBC Driver error", e);
			}
			
		}

}
