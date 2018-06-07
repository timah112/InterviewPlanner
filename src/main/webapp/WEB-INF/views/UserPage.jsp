<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Interview Planner</title>
</head>
<body>
	<%
		Date date = new Date();
	%>

	<div>
		<h2>Welcome.</h2>
		The current date is:
		<%=date%>
	</div>


	<div class="container">
		<div class="title" align="center">Add a Passenger</div>

		<%
			if (request.getAttribute("errors") != null) {
		%>

		<fieldset id="error_field">

			<legend align="center">Errors</legend>
			<ul>
				<%
					if (request.getAttribute("first_name_error") != null) {
				%>
				<li class="error">First Name Error - Please Enter More Characters.</li>
				<%
					}
				%>

				<%
					if (request.getAttribute("last_name_error") != null) {
				%>
				<li class="error">Last Name Error</li>
				<%
					}
				%>

			</ul>
		</fieldset>
		<%
			}
		%>

	</div>

	<fieldset>
		<form action="UserPageServlet" method="post">
			<legend align="center">Candidate Details:</legend>

			<div class="inputField">
				<label form="first-name" class="inputLabel">First Name: </label> <input
					name="first-name" type="text"
					value="<%=request.getAttribute("first-name")%>"></input>
			</div>

			<div class="inputField">
				<label form="last-name" class="inputLabel">Last Name: </label> <input
					name="last-name" type="text"
					value="<%=request.getAttribute("last-name")%>"></input>
			</div>

			<div class="inputField">
				<label form="gender" class="inputLabel">Gender: </label> <select
					name="gender" type="text">
					<option value="Male">Male</option>
					<option value="Female">Female</option>
					<option value="Transgender">Transgender</option>
				</select>
			</div>

			<div class="inputfield" id="availabilityField">
				<input id="availabilityButton" type="submit"
					value="Add Availability" onclick="addAvailibility();"></input>
			</div>

			<div class="inputfield" id="submitfield">
				<input id="submitBtn" type="submit" value="Submit"></input>
			</div>
		</form>


	</fieldset>


</body>
</html>