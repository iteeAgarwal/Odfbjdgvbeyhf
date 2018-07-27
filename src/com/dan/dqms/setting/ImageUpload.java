package com.dan.dqms.setting;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Device;

import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.SystemDetailsList;

@WebServlet("/ImageUpload")
public class ImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImageUpload() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		request.setAttribute("deptList", deptList);
		request.setAttribute("deviceList", deviceList);

		request.getRequestDispatcher("image_upload_form.jsp").forward(request,
				response);

	}

}