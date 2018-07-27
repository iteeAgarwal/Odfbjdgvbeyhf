package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dqms.db.Department;
import org.dqms.db.Device;
import org.dqms.util.Print;

import com.dan.dqms.setting.AdvertiseBean;
import com.dan.dqms.token.TokenGeneratorData;

public class ImagesList {

	public List<AdvertiseBean> getImages() {
		List<AdvertiseBean> imagesList = new ArrayList<AdvertiseBean>();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();

		try {

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from advertisement");

			while (rs.next()) {

				AdvertiseBean advertiseBean = new AdvertiseBean();

				int dept_ID = rs.getInt("depart_id");

				if (!deptList.isEmpty()) {
					for (Department list : deptList) {
						if (dept_ID == list.getDepart_id()) {
							advertiseBean.setDepart_name(list.getDepart_name());
						}
					}
				}
				int deviceType = rs.getInt("device_type");
                advertiseBean.setDevice_type(deviceType);
				if (!deviceList.isEmpty()) {
					for (Device list : deviceList) {
						if (deviceType == list.getType()) {
							advertiseBean.setDevice_name(list.getDevice_name());
						}
					}
				}

				advertiseBean.setImage_name(rs.getString("image_name"));

				advertiseBean.setImage_path(rs.getString("image_path"));

				advertiseBean.setName(rs.getString("name"));
				advertiseBean.setId(rs.getInt("id"));

				imagesList.add(advertiseBean);

			}
          con.close();
          
		} catch (Exception e) {

			Print.logException("Exception in ImagesList class ", e);
		}

		return imagesList;

	}

}
