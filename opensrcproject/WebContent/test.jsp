<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%@ page import="opensrcproject.NaverAPI" %>
	<%
		String keyword = request.getParameter("one");
	NaverAPI t = new NaverAPI(keyword);
	String[] str=null;
	if(t.getP()!=null){
		str = t.getP();
	}
	request.setAttribute("����", str);
	%>
	<form action="<%=request.getContextPath()%>/NewFile.jsp" method="post">
	<input type="submit" value ="�ǵ��ư���">
	</form>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>