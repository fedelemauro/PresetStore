<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Ordini"/>
</jsp:include>


<section>
    <h1>Ordini </h1>
    <table>
        <thead>
        <tr>
            <th>ID ORDINE </th>
            <th>TOTALE</th>
            <th>PRODOTTI</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ordini}" var="ordine">
            <tr>
                <td>${ordine.id}</td>
                <td>${ordine.prezzoEur} €</td>
                <td>
                    <form action="OrdiniUtente" method="post">
                        <input type="hidden" name="id" value="${ordine.id}">
                        <input type="submit" name="dettagli" value="Dettagli" >
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>












<%@include file="footer.html"%>
