package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import org.dqms.db.AppToken;

import org.dqms.util.Print;

import com.dan.dqms.token.TokenGeneratorData;

public class AppTokenList {

	public List<AppToken> getTokenAppoint() {

		TokenGeneratorData tk = new TokenGeneratorData();

		List<AppToken> tokenList = new ArrayList<AppToken>();

		Statement statement = null;
		Connection con = tk.connection();

		ResultSet rs = null;
		String Query = "select * from app_token_no";
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(Query);

			while (rs.next()) {

				AppToken appToken = new AppToken();

				int app_token_id = rs.getInt("app_token_id");
				String app_token_value = rs.getString("app_token_value");

				appToken.setApp_token_id(app_token_id);

				appToken.setApp_token_value(app_token_value);

				tokenList.add(appToken);

			}
           con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  AppTokenList class", e);
		}
		
		return tokenList;

	}

	public List<AppToken> getTokenDetails() {
		TokenGeneratorData tk = new TokenGeneratorData();

		List<AppToken> tokenList = new ArrayList<AppToken>();

		Statement statement = null;
		Connection con = tk.connection();

		ResultSet rs = null;
		String Query = "select * from token_details where app_walk_id = 2 ";
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(Query);

			while (rs.next()) {

				AppToken appToken = new AppToken();

				int token_no = rs.getInt("token_no");
				int app_walk_id = rs.getInt("app_walk_id");

				appToken.setTokenNo(token_no);

				appToken.setAppWalk(app_walk_id);

				tokenList.add(appToken);

			}
            con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  AppTokenList class" , e);
		}
		return tokenList;
	}
	public List<AppToken> getTokenDetails1(String doc_id) {
		TokenGeneratorData tk = new TokenGeneratorData();

		List<AppToken> tokenList = new ArrayList<AppToken>();

		Statement statement = null;
		Connection con = tk.connection();

		ResultSet rs = null;
		String Query = "select * from token_details where app_walk_id = 2 && user_id="+doc_id;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(Query);

			while (rs.next()) {

				AppToken appToken = new AppToken();

				int token_no = rs.getInt("token_no");
				int app_walk_id = rs.getInt("app_walk_id");

				appToken.setTokenNo(token_no);

				appToken.setAppWalk(app_walk_id);

				tokenList.add(appToken);

			}
           con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  AppTokenList class" , e);
		}
		return tokenList;
	}
	public List<AppToken> getRemainTokenAppoint() {
		TokenGeneratorData tk = new TokenGeneratorData();

		List<AppToken> app_tokenListArr = getTokenAppoint();

		try {

			List<AppToken> tokenListArr = getTokenDetails();

			

			if (tokenListArr.size() > 0) {

				for (int i = 0; i < tokenListArr.size(); i++) {
					
					int tokenNo = tokenListArr.get(i).getTokenNo();
					
					
					
					if(app_tokenListArr.size() > 0 )
					{
						for(int j = 0; j < app_tokenListArr.size(); j++)
						{
							if(tokenNo == app_tokenListArr.get(j).getApp_token_id())
							{
								app_tokenListArr.remove(j);
								break;
							}
							
						}
					}

				}

			

			}

		} catch (Exception e) {
			Print.logException("Exception in  AppTokenList class" , e);
		}
		return app_tokenListArr;
	}
	
	public List<AppToken> getRemainTokenAppoint1(String Doc_id) {
		TokenGeneratorData tk = new TokenGeneratorData();

		List<AppToken> app_tokenListArr = getTokenAppoint();

		try {

			List<AppToken> tokenListArr = getTokenDetails1(Doc_id);

			

			if (tokenListArr.size() > 0) {

				for (int i = 0; i < tokenListArr.size(); i++) {
					
					int tokenNo = tokenListArr.get(i).getTokenNo();
					
					
					
					if(app_tokenListArr.size() > 0 )
					{
						for(int j = 0; j < app_tokenListArr.size(); j++)
						{
							if(tokenNo == app_tokenListArr.get(j).getApp_token_id())
							{
								app_tokenListArr.remove(j);
								break;
							}
							
						}
					}

				}

			

			}

		} catch (Exception e) {
			Print.logException("Exception in  AppTokenList class" , e);
		}
		return app_tokenListArr;
	}
	
		
		
	public static void main(String[] args) {

		 AppTokenList t = new AppTokenList();

		// for(int i = 0;i<t.getRemainTokenAppoint1("3").size();i++)
		 //{
		// System.out.println(t.getRemainTokenAppoint1("3").get(i).getApp_token_id());
		// }
	}

}
