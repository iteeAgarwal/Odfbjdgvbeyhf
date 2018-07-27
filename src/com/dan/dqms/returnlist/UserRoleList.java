package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dqms.db.Roles;
import org.dqms.util.Print;

import com.dan.dqms.token.TokenGeneratorData;


public class UserRoleList {
	
	
	TokenGeneratorData tk = new TokenGeneratorData();
	Connection con = tk.connection();

	

	public List<Roles> getUserRoles() {

		List<Roles> roleList = new ArrayList<Roles>();

		try {

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from roles_details ");
			 
			while (rs.next()) {
				Roles roles = new Roles();
				roles.setRole_id(rs.getInt("role_id"));
				roles.setRole_name(rs.getString("role_name"));
				roleList.add(roles);

			}
			con.close();
		} catch (Exception e) {
			Print.logException("Exception in UserRoleList class " , e);
		}

		return roleList;

	}


}
