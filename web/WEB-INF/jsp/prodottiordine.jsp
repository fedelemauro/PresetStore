<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"></jsp:include>



<section>
    <h1>Ordine N. ${id} </h1>
    <table>
        <thead>
        <tr>
            <th>PRODOTTI ORDINATI</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${prodotti}" var="prodotto">
            <tr>
                <td>${prodotto.nome}</td>
                <td>${prodotto.prezzoEuro} €</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</section>




<%@include file="footer.html"%>
