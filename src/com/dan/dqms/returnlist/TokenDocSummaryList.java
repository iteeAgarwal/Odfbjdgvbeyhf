package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dqms.util.Print;

import com.dan.dqms.token.TokenGeneratorData;

public class TokenDocSummaryList {

	public int getCurrentToken(int docID) {
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		int current_token = 0;
		
		String Query = "select * from token_doc_summary where user_id ='"+docID+"'";
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(Query);

			while (rs.next()) {
				
				current_token = rs.getInt("current_token");

			}

		} catch (SQLException e) {
			Print.logException("Exception in  TokenDocSummary class returnList package", e);
			
		}

		return current_token;
	}

}
