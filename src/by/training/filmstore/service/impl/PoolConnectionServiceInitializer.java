package by.training.filmstore.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.dao.pool.PoolConnection;
import by.training.filmstore.dao.pool.PoolConnectionException;

public final class PoolConnectionServiceInitializer {

	private final static Logger logger = LogManager.getLogger(PoolConnectionServiceInitializer.class);

	public static void initPoolConnection() {
		try {
			PoolConnection.getInstance();
		} catch (PoolConnectionException e) {
			logger.error("Error pool connection initialization!");
			throw new RuntimeException("JDBC Driver error", e);
		}
	}

	public static void destroyPoolConnection() {
		try {
			PoolConnection poolConnection = PoolConnection.getInstance();
			poolConnection.disposePoolConnection();
		} catch (PoolConnectionException e) {
			logger.error("Can't destroy pool connection!");
			throw new RuntimeException("JDBC Driver error", e);
		}

	}

}
