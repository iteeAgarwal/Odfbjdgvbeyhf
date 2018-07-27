package com.dan.dqms.setting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.ImagesList;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/ImagesListServlet")
public class ImagesListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImagesListServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String cmdDelete=request.getParameter("cmdDelete");
		String radioID=request.getParameter("radioroomID");
		
		
		if(cmdDelete!=null && radioID!=null){
			int id=Integer.parseInt(radioID);
			deleteImageVideo(id);
		}
		


		ImagesList imageList = new ImagesList();

		List<AdvertiseBean> imagesList = imageList.getImages();

		request.setAttribute("imagesList", imagesList);

		request.getRequestDispatcher("image_upload_list.jsp").forward(request,
				response);

	}

	private int deleteImageVideo(int id) {

		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		int res = 0;
		try {
			Statement st = con.createStatement();

			res = st.executeUpdate("delete from advertisement where id = '"
					+ id + "'  ");
           con.close();
		} catch (Exception e) {
			Print.logException("Exception " ,e);
		}
      
		return res;

	}

}
