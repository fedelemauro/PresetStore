<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><%@taglib prefix="c"
                                uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>
<section>
    <h1>Carrello</h1>
    <h5 style="color: red">${notifica}</h5>


<%@include file="footer.html"%>

