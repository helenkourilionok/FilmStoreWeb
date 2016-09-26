package by.training.filmstore.service.impl;

import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;

public final class PoolConnectionServiceInitializer {

	public static void initPoolConnection() throws RuntimeException {
		try {
			PoolConnection.getInstance();
		} catch (PoolConnectionException e) {
			throw new RuntimeException("JDBC Driver error!Can't create connection with database!", e);
		}
	}

	public static void destroyPoolConnection() throws RuntimeException {
		try {
			PoolConnection poolConnection = PoolConnection.getInstance();
			poolConnection.disposePoolConnection();
		} catch (PoolConnectionException e) {
			throw new RuntimeException("JDBC Driver error!Can't destroy connection with database!", e);
		}

	}

}
