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

import models.Article;
import tools.ConnectionDB;

/**
 * Servlet implementation class Dets
 */
@WebServlet("/Dets")
public class Dets extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dets() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionDB con = new ConnectionDB();
		Article article = null;
		ResultSet rs = null;
		String sql = "select * from articles where codearticle='"+request.getParameter("artag")+"';";
		try {
			rs = con.getSt().executeQuery(sql);
			while(rs.next()) {
				article = new Article(rs.getInt("codearticle"),rs.getString("titre"),rs.getString("auteur"),rs.getString("editeur"),rs.getString("prix"),rs.getInt("stock"),rs.getString("categorie"),rs.getString("photo"));		
			}
			request.setAttribute("article", article);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		RequestDispatcher view = request.getRequestDispatcher("ardetail.jsp");
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
