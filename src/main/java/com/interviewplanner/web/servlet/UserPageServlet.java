package com.interviewplanner.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.interviewplanner.web.bean.UserBean;
import com.interviewplanner.web.dao.UserInfoDao;
import com.interviewplanner.web.entity.UserEntity;
import com.interviewplanner.web.enums.Gender;

/*
 * Browser sends Http Request to Web Server
 * 
 * Code in Web Server => Input:HttpRequest, Output: HttpResponse
 * JEE with Servlets
 * 
 * Web Server responds with Http Response
 */

//Java Platform, Enterprise Edition (Java EE) JEE6

//Servlet is a Java programming language class 
//used to extend the capabilities of servers 
//that host applications accessed by means of 
//a request-response programming model.

//1. extends javax.servlet.http.HttpServlet
//2. @WebServlet(urlPatterns = "/login.do")
//3. doGet(HttpServletRequest request, HttpServletResponse response)
//4. How is the response created?

@WebServlet(urlPatterns = "/UserPageServlet")
public class UserPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserInfoDao user = new UserInfoDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setAttribute("first-name", "");
		request.setAttribute("last-name", "");
		request.setAttribute("dob", "");
		request.setAttribute("gender", "");

		Calendar cal = Calendar.getInstance();
		Date dob = cal.getTime();

		/*
		 * try {
		 * 
		 * UserEntity userEntity = new UserEntity("Atif114", "Atif", "Tirmizi", dob,
		 * "Male", "Delitte", "Software Dev"); // Use session object to save java object
		 * // create student object System.out.println("Creating new session obj"); //
		 * create session factory Configuration con = new
		 * Configuration().configure().addAnnotatedClass(UserEntity.class); // create
		 * session SessionFactory sf = con.buildSessionFactory();
		 * 
		 * // start a transaction Session session = sf.openSession();
		 * 
		 * // save the entity object Transaction tx = session.beginTransaction();
		 * 
		 * // commit transaction session.save(userEntity);
		 * //session.getTransaction().commit();
		 * 
		 * tx.commit();
		 * 
		 * System.out.println("DOne!");
		 * 
		 * }
		 * 
		 * finally { // factory.close(); }
		 */

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/UserPage.jsp");
		view.forward(request, response); // request and response to the web browser

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserBean userBean = new UserBean();
		request.setAttribute("errors", false);

		String firstName = request.getParameter("first-name");
		if (firstName.length() <= 1 || firstName.matches(".*\\d.*")) {
			request.setAttribute("errors", true);
			request.setAttribute("first_name_error", true);

			request.setAttribute("first-name", "");
		}

		else {
			userBean.setFirstName(firstName);
			request.setAttribute("first-name", firstName);
			System.out.println(firstName);
		}

		String lastName = request.getParameter("last-name");

		if (lastName.length() <= 1 || lastName.matches(".*\\d.*")) {
			request.setAttribute("errors", true);
			request.setAttribute("last_name_error", true);

			request.setAttribute("last-name", "");
		}

		else {
			userBean.setLastName(lastName);
			request.setAttribute("last-name", lastName);
			System.out.println(lastName);
		}

		// Gender:
		String gender = request.getParameter("gender");
		System.out.println("Gender: " + gender);
		userBean.setGender(Gender.valueOf(gender));

		// This call block will execute if the error does exist or is true
		if ((Boolean) request.getAttribute("errors")) {
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/UserPage.jsp");

			// the request object is the object where we set all the attributes
			// the response object is where the jsp will write the jsp response and send it
			// back to the browser
			view.forward(request, response);
		} else {
			// calling the doGet Method from above
			doGet(request, response);
			user.userInfoSave(userBean);

			// If the submit button is clicked properly
			if (request.getParameter("submitBtn") != null) {

				ServletContext sc = this.getServletContext();

				// This ensures only 1 thread is counting the number of beans
				synchronized (this) {
					ArrayList<UserBean> beanList = new ArrayList<UserBean>();
					beanList.add(userBean);

					sc.setAttribute("userInfo", beanList);

				}
				// This tells the browser to go to a new URL(after successful form submission):
				response.sendRedirect("");

			}
		}
	}

}