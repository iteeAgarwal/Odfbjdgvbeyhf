package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dqms.db.Department;
import org.dqms.db.TokenGroup;
import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.token.TokenGeneratorData;
import com.dan.dqms.token.TokenStatusBean;

public class TokenStaList {
	
	
	
	public List<TokenStatusBean> getTokenStatus() {
		ArrayList<TokenStatusBean> tokenStatus = new ArrayList<TokenStatusBean>();

		TokenGeneratorData tg = new TokenGeneratorData();

		Connection con = tg.connection();

		TokGroupList tk = new TokGroupList();
		
		DepartmentList departmentList = new DepartmentList();
		
		List<Department> deptList = departmentList.getDeptList();

		UserList userOb = new UserList();

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from token_doc_summary");

			while (rs.next()) {

				String docName = "";

				String groupName = "";
				
				int deptID = 0;
				
				String deptName = "";

				TokenStatusBean bean = new TokenStatusBean();

				int userID = rs.getInt("user_id");

				ArrayList<User> userListArr = (ArrayList<User>) userOb
						.getUsers(String.valueOf(userID));

				if (userListArr.size() > 0) {
					docName = userListArr.get(0).getName();
				}

				bean.setUser_id_to(docName);

				int tokenID = rs.getInt("token_group_id");

				List<TokenGroup> tokenGroupList = (ArrayList<TokenGroup>) tk
						.getTOGroupListByID(String.valueOf(tokenID));

				if (tokenGroupList.size() > 0) {
					
					groupName = tokenGroupList.get(0).getToken_group_name();
					
					deptID = tokenGroupList.get(0).getDepart_id();
				}
				
				if(deptList.size() > 0)
				{
					for(Department list :deptList)
					{
						if(deptID == list.getDepart_id())
						{
							bean.setDepartName(list.getDepart_name());
						}
					}
				}

				bean.setToken_group_id_to(groupName);

				bean.setTotal_token_to(rs.getInt("total_token")+rs.getInt("total_token_walk"));

				bean.setDisplay_token(rs.getString("display_token"));

				tokenStatus.add(bean);
			}
           con.close();
		} catch (Exception e) {
			Print.logException("Exception in token status list class  ", e);
		}

		return tokenStatus;

	}
	
	
	public List<TokenStatusBean> getTokenStatusByGroup(int groupID) {
		ArrayList<TokenStatusBean> tokenStatus = new ArrayList<TokenStatusBean>();

		TokenGeneratorData tg = new TokenGeneratorData();

		Connection con = tg.connection();

		TokGroupList tk = new TokGroupList();
		
		DepartmentList departmentList = new DepartmentList();
		
		List<Department> deptList = departmentList.getDeptList();

		UserList userOb = new UserList();

		try {
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from token_doc_summary where token_group_id ='"+groupID+"' ");

			while (rs.next()) {

				String docName = "";

				String groupName = "";
				
				int deptID = 0;
				
				String deptName = "";

				TokenStatusBean bean = new TokenStatusBean();

				int userID = rs.getInt("user_id");

				ArrayList<User> userListArr = (ArrayList<User>) userOb
						.getUsers(String.valueOf(userID));

				if (userListArr.size() > 0) {
					docName = userListArr.get(0).getName();
				}

				bean.setUser_id_to(docName);

				int tokenID = rs.getInt("token_group_id");

				List<TokenGroup> tokenGroupList = (ArrayList<TokenGroup>) tk
						.getTOGroupListByID(String.valueOf(tokenID));

				if (tokenGroupList.size() > 0) {
					
					groupName = tokenGroupList.get(0).getToken_group_name();
					
					deptID = tokenGroupList.get(0).getDepart_id();
				}
				
				if(deptList.size() > 0)
				{
					for(Department list :deptList)
					{
						if(deptID == list.getDepart_id())
						{
							bean.setDepartName(list.getDepart_name());
						}
					}
				}

				bean.setToken_group_id_to(groupName);

				bean.setTotal_token_to(rs.getInt("total_token"));

				bean.setDisplay_token(rs.getString("display_token"));

				tokenStatus.add(bean);
			}
			con.close();

		} catch (Exception e) {
			
			Print.logException("Exception in token status list class  ", e);
		}

		return tokenStatus;

	}
}



