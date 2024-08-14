/**
 This application is for demonstration use only. It contains known application security
vulnerabilities that were created expressly for demonstrating the functionality of
application security testing tools. These vulnerabilities may present risks to the
technical environment in which the application is installed. You must delete and
uninstall this demonstration application upon completion of the demonstration for
which it is intended. 

IBM DISCLAIMS ALL LIABILITY OF ANY KIND RESULTING FROM YOUR USE OF THE APPLICATION
OR YOUR FAILURE TO DELETE THE APPLICATION FROM YOUR ENVIRONMENT UPON COMPLETION OF
A DEMONSTRATION. IT IS YOUR RESPONSIBILITY TO DETERMINE IF THE PROGRAM IS APPROPRIATE
OR SAFE FOR YOUR TECHNICAL ENVIRONMENT. NEVER INSTALL THE APPLICATION IN A PRODUCTION
ENVIRONMENT. YOU ACKNOWLEDGE AND ACCEPT ALL RISKS ASSOCIATED WITH THE USE OF THE APPLICATION.

IBM AltoroJ
(c) Copyright IBM Corp. 2008, 2013 All Rights Reserved.
 */

package com.ibm.security.appscan.altoromutual.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.security.appscan.altoromutual.util.OperationsUtil;
import com.ibm.security.appscan.altoromutual.util.ServletUtil;

/**
 * This servlet allows to transfer funds between existing accounts
 * Servlet implementation class TransverServlet
 * 
 * @author Alexei
 */
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	* This method handles GET HTTP requests received by the server, providing an additional layer of backend abstraction.
	* In this case, it is designed to reroute these GET requests to the doPost method.
	* 
	* @param req  Client's HTTP request data containing parameters, headers, etc.
	* @param resp Server's HTTP response data to be sent to the client.
	* @throws ServletException if an input or output error is detected when the servlet handles the GET request
	* @throws IOException if the request for the GET could not been handled
	*/
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	/**
	* This method handles POST requests to perform a transfer operation from one account to another.
	* First, it checks if a user is logged in. If not, it redirects to the login page.
	* Then, it gets the account id, the target account id (to which the amount is transferred), and the transfer amount from the request parameters.
	* Then it processes the transfer using the `doServletTransfer` method from `OperationsUtil`.
	* Finally, it sets a message regarding the transfer status in the request and forwards the request and response to the "transfer.jsp" page.
	* 
	* @param request HttpServletRequest object that contains the request from the client to the server
	* @param response HttpServletResponse object that contains the response from the server to the client
	* @throws ServletException if servlet-specific error occurs
	* @throws IOException if an input-output error occurs
	*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(!ServletUtil.isLoggedin(request)){
			response.sendRedirect("login.jsp");
			return ;
		}
		String accountIdString = request.getParameter("fromAccount");
		long creditActId = Long.parseLong(request.getParameter("toAccount"));
		double amount = Double.valueOf(request.getParameter("transferAmount"));
		
		
		String message = OperationsUtil.doServletTransfer(request,creditActId,accountIdString,amount);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");
		request.setAttribute("message", message);
		dispatcher.forward(request, response);	
	}

}
