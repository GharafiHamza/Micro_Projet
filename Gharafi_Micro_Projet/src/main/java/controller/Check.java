package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Client;
import tools.ConnectionDB;

/**
 * Servlet implementation class Check
 */
@WebServlet("/Check")
public class Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionDB con = new ConnectionDB();
		ResultSet rs = null;
		Client client = null;
		String url = "welpage.jsp";
		String sql = "select * from clients ";
		try {
			rs = con.getSt().executeQuery(sql);
			while(rs.next()) {
				if((rs.getString("email").equals(request.getParameter("email")))){
					if(rs.getString("motpasse").equals(request.getParameter("pwd"))){
						client = new Client(rs.getString("email"),rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse"),rs.getString("ville"),rs.getString("tel"),rs.getInt("codepostal"));
						request.setAttribute("client", client);
					}else {
						url = "connect.jsp";
						request.setAttribute("para","le mot de passe incorrect , verifier le mot de passe !!");
					}
				}else {
					url = "connect.jsp";
					request.setAttribute("para","l'email est incorrect , verifier votre email !!");
				}
				//System.out.println(rs.getString("motpasse"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionDB con = new ConnectionDB();
		Client client = null;
		String url = "welpage.jsp";
		String sql = "insert into clients (`email`, `nom`, `prenom`, `adresse`, `codepostal`, `ville`, `tel`, `motpasse`)  values('"+request.getParameter("email")+"','"+request.getParameter("nom")+"','"+request.getParameter("prenom")+"','"+request.getParameter("adresse")+"','"+request.getParameter("codepostal")+"','"+request.getParameter("ville")+"','"+request.getParameter("tel")+"','"+request.getParameter("pwd")+"');";
		try {
			con.getSt().executeUpdate(sql);
			client = new Client(request.getParameter("email"),request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("adresse"),request.getParameter("ville"),request.getParameter("tel"),Integer.parseInt(request.getParameter("codepostal")));
			request.setAttribute("client", client);
			
		} catch (SQLException e) {
			url = "signup.jsp";
			e.printStackTrace();
		}
		con.close();
		RequestDispatcher view = request.getRequestDispatcher(url);
		view.forward(request, response);
	
	}

}
