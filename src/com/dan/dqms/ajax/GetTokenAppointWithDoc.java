package com.dan.dqms.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.AppToken;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.json.simple.JSONObject;

import beans.TokenAppointHelper;

import com.dan.dqms.returnlist.AppTokenList;
import com.dan.dqms.returnlist.UserList;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetTokenAppointWithDoc
 */
@WebServlet("/GetTokenAppointWithDoc")
public class GetTokenAppointWithDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTokenAppointWithDoc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setContentType("text/html");
	
		try {
			String DocID = request.getParameter("doc_id");
			AppTokenList appToken = new AppTokenList();
			List<AppToken> aptoken = appToken.getRemainTokenAppoint1(DocID);
			List<TokenAppointHelper> token= new ArrayList<TokenAppointHelper>();
			JSONObject js = new JSONObject();
			for(int i=0;i<aptoken.size();i++)
			{
				token.add(new TokenAppointHelper(aptoken.get(i).getApp_token_id(),aptoken.get(i).getApp_token_value()));
				
			}
			//UserList userListOb = new UserList();

			Gson gson = new Gson();

			String jsonList = gson.toJson(token);

			String jsonListOfInfo = " {\"alert\":" + jsonList + " }";
			response.getWriter().write(jsonListOfInfo);
		
   	   
		} catch (Exception e) {
			
			Print.logException("Exception in GetTokenAppointWithDoc class",e);
		}

	}

}
