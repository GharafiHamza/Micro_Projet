package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.util.StringUtils;

import models.Article;
import tools.ConnectionDB;

/**
 * Servlet implementation class Consultation
 */
@WebServlet("/Consultation")
public class Consultation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Consultation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionDB con = new ConnectionDB();
		ArrayList<Article> articles = new ArrayList<Article>();
		ResultSet rs = null;
		String sql;
		if(request.getParameter("categories") == null || request.getParameter("categories").equals("All")) {
			sql = "select * from articles;";
		}else sql = "select * from articles where categorie='"+request.getParameter("categories")+"';";

		try {
			rs = con.getSt().executeQuery(sql);
			while(rs.next()) {
				articles.add(new Article(rs.getInt("codearticle"),rs.getString("titre"),rs.getString("auteur"),rs.getString("editeur"),rs.getString("prix"),rs.getInt("stock"),rs.getString("categorie"),rs.getString("photo")));
			}
			request.setAttribute("articles", articles);
			request.setAttribute("categorie", request.getParameter("categories"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		RequestDispatcher view = request.getRequestDispatcher("catalogue.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
