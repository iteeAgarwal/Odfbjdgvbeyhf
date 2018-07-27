package com.dan.dqms.token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class TokenGeneratorData {

	public Connection connection() {
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName(SystemSetting.DB_DRIVER);
			con = DriverManager.getConnection(SystemSetting.DB_URL,
					SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

		} catch (SQLException e) {
			Print.logException("Exception in  TokenGeneratorData class" , e);
		} catch (Exception e) {
			Print.logException("Exception in  TokenGeneratorData class" , e);

		}
		return con;

	}

	
}
