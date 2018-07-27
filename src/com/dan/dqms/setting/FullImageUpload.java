package com.dan.dqms.setting;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import org.dqms.config.SystemSetting;
import org.dqms.db.Department;
import org.dqms.db.Device;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.SystemDetailsList;
import com.dan.dqms.staticvar.StaticVariables;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/FullImageUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
// 100 MB
public class FullImageUpload extends HttpServlet {

	private static final long serialVersionUID = 205242440643911308L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try{

		StringBuffer sb = new StringBuffer();

		sb.append(StaticVariables.getRealPath());

		sb.append("images_dqms/");

		String[] deptCheckbox = null;
		
		String pathForImage = null;

		String image_upload = request.getParameter("image_upload");
		String name = request.getParameter("imageName");
		String deviceCheckbox = request.getParameter("deviceCheckbox");

		// type 1 for mdu

		if ("1".equals(deviceCheckbox)) {
			sb.append("mdu_img/");
			
			pathForImage="images_dqms/mdu_img/";

		}

		// type 2 for wdu
		else if ("2".equals(deviceCheckbox)) {
			sb.append("wdu_img/");
			
			pathForImage="images_dqms/wdu_img/";

		}
		String uploadFilePath = sb.toString();

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

		String fileName = null;
		// Get all the parts from request and write it to the file on server

		for (Part part : request.getParts()) {
			fileName = getFileName(part);
			if (fileName.length() != 0) {

				if (fileName.endsWith(".png") || fileName.endsWith(".jpg")

				|| fileName.endsWith(".JPG") || fileName.endsWith(".PNG")

				) {
					BufferedImage bi = ImageIO.read(part.getInputStream());

					int w = bi.getWidth();
					int h = bi.getHeight();

					if ((w == 1280 && h == 720) || (w == 720 && h == 1280)) {
						

						if (request.getParameter("deptCheckbox") != null) {

							deptCheckbox = request
									.getParameterValues("deptCheckbox");

							

							if (null != deptCheckbox && deptCheckbox.length > 0)

							{
								part.write("" + uploadFilePath + File.separator
										+ fileName);
								insertAdvertis(deptCheckbox, deviceCheckbox,
										fileName, SystemSetting.mdu_IMAGE_PATH+pathForImage+fileName,
										name);
								request.setAttribute("message", fileName
										+ " File uploaded successfully..!!");
							}
						} else {
							request.setAttribute("message", 
									" please select department..!!");
						}

					} else {
						request.setAttribute("message",
								"Please select image of resolution :1280*720 or 720*1280 ");
					}
				} else if (fileName.endsWith(".mp4")
						|| fileName.endsWith(".MP4")) {

					long fileSize = part.getSize();

					if ("1".equals(deviceCheckbox)
							&& (double) fileSize / (1024 * 1024) < 60) {

						if (request.getParameter("deptCheckbox") != null) {

							deptCheckbox = request
									.getParameterValues("deptCheckbox");

							if (null != deptCheckbox && deptCheckbox.length > 0)

							{
								part.write(uploadFilePath + File.separator
										+ fileName);
								deptCheckbox = request
										.getParameterValues("deptCheckbox");

								insertAdvertis(deptCheckbox, deviceCheckbox,
										fileName, SystemSetting.mdu_IMAGE_PATH+pathForImage+fileName,
										name);
								request.setAttribute("message", fileName
										+ " File uploaded successfully..!!");
							}
						} else {
							request.setAttribute("message", 
									" please select department..!!");
						}

					}

					else {

						request.setAttribute("message",
								"Please select MDU..!! ");
					}

				}

				else {

					request.setAttribute("message", "Please select file format mp4 ..!! ");
				}
			} else {
				// request.setAttribute("message",
				// "Please select image first!");
			}

		}

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		request.setAttribute("deptList", deptList);
		request.setAttribute("deviceList", deviceList);

		
		}
		catch(Exception e)
		{
			Print.logException("Exception in upload image class exception", e);
		}
		request.getRequestDispatcher("image_upload_form.jsp").forward(request,
				response);

	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");

		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
	}

	public void insertAdvertis(String[] a, String b, String imageName,
			String imagePath, String name) {
		String query = null;
			
		if (a.length > 0) {

			for (int i = 0; i < a.length; i++) {

				query = "insert into advertisement(depart_id,device_type,image_name,image_path,name) values('" + a[i] + "' , '"
						+ b + "','" + imageName + "','" + imagePath + "','"
						+ name + "') ";

				stateExecute(query);
			}

		}

	}

	public void stateExecute(String str) {

		TokenGeneratorData tk = new TokenGeneratorData();

		Connection con = tk.connection();

		try {
			Statement st = con.createStatement();

			int i = st.executeUpdate(str);

			if (i > 0) {
			} else {
			}
            con.close();
		} catch (SQLException e) {
			Print.logException("Exception in Image name insert database sql exception", e);
		}

	}

}
