<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"><jsp:param name="pageTitle" value="Home"/></jsp:include>

<section>
<h3 style="font-family: Impact; text-align: center;background-color: #abb5ba;" >Fantastici preset da applicare ai tuoi scatti da condividere sui social </h3>
</section>
<h1  id="flip" style="font-family: Impact; text-decoration: underline; text-align: center; ">SCOPRI I NOSTRI PRESET </h1>

<section>
    <grid>
        <c:forEach items="${prodotti}" begin="0" end="5" var="prodotto">
            <div col="1/3" style=" text-align: center">
                <h3>
                    <a href="Prodotto?id=<c:out value="${prodotto.id}"/>" style="font-family: Impact;"><c:out value="${prodotto.nome}" /></a>
                </h3>
                <a href="Prodotto?id=<c:out value="${prodotto.id}"/>"><img src="img/products/<c:out value="${prodotto.id}"/>.jpg"></a>
                <h4><c:out value="${prodotto.prezzoEuro}" /> &euro;</h4>
            </div>
        </c:forEach>
    </grid>
    <br>
    <br>
    <h1  style="font-family: 'Arial Black'; text-decoration: underline; text-align: center; "><a href="faq.jsp">F.A.Q.</a></h1>

</section>

<%@include file="footer.html"%>




