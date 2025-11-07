package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DBConnection {
	private static DBConnection instance;
	Connection DBConnection = null;

	public DBConnection() {
		try {

			MysqlDataSource dataSource = new MysqlDataSource();

			Properties props = new Properties();

			try (FileInputStream file = new FileInputStream("src\\main\\resources\\conexion.properties")) {
				props.load(file);
			}

			dataSource.setUrl(props.getProperty("url"));
			dataSource.setUser(props.getProperty("user"));
			dataSource.setPassword(props.getProperty("password"));

			DBConnection = dataSource.getConnection();
			System.out.println("Conexion realizada correctamente.");

		} catch (SQLException | IOException e) {
			System.out.println("Error al conextar con mysql: " + e.getMessage());
		}

	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		return instance.DBConnection;
	}

}
