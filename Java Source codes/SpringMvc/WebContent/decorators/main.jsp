<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><decorator:title default=""/>综合小例子</title>
		<decorator:head />
	</head>
	<body>

	<table  width="100%" height="100%" border="1">
			<tr >
				<td colspan="2" height="20%">
		          <img src="./images/1.bmp" height="100" width="100%">
				</td>
			</tr>
			<tr>
				<td width="20%" height="60%">
		         <%--
		          <img src="./images/3.jpg">
		          --%>
		          <jsp:include page="../pages/left.jsp"></jsp:include>
				</td>
				<td width="80%">
				 <decorator:body />
				</td>
			</tr>
			<tr height="20%">
				<td colspan="2" height="20%">
					
						 <img src="./images/2.jpg" height="100" width="100%s">
					
				</td>
			</tr>
		</table>
	</body>
</html>
