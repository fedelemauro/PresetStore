<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>
<section>
    <h1>Carrello</h1>
    <h5 style="color: red">${notifica}</h5>

    <grid>
        <c:forEach items="${carrello.prodotti}" var="pq">
            <div col="1/3">
                <a href="#"><img src="img/products/${pq.prodotto.id}.jpg"></a>
            </div>
            <div col="2/3">
                <h3>
                    <a href="Prodotto?id=${pq.prodotto.id}">${pq.prodotto.nome}</a>
                </h3>
                <p>${pq.prodotto.descrizione}</p>
                <h5>Quantità: ${pq.quantita}, Prezzo unit.: ${pq.prodotto.prezzoEuro} &euro;, Prezzo tot.: ${pq.prezzoTotEuro} &euro;</h5>
                <form action="Carrello" method="post">
                    <input type="hidden" name="prodId" value="${pq.prodotto.id}">
                    <input type="hidden" name="setNum" value="0">
                    <input type="submit" value="Rimuovi">
                </form>
            </div>
        </c:forEach>
        <c:if test="${empty carrello.prodotti}">
            <div col="1">Nessun prodotto nel carrello</div>
        </c:if>
    </grid>
</section>
<c:if test="${not empty carrello.prodotti}">
    <section>
        <grid>
            <div col="1/3">
                <h2>Totale: ${carrello.prezzoTotEuro} &euro;</h2>
            </div>


            <div col="1/3">
                <form action="Acquisto" method="post">
                    <input type="hidden" name="prezzo" value="${carrello.prezzoTotCent}">
                    <c:forEach items="${carrello.prodotti}" var="pro" >
                        <input type="hidden" name="idprodotto" value="${pro.prodotto.id}">
                        <input type="hidden" name="qprodotto" value="${pq.quantita}">
                    </c:forEach>
                    <input type="submit" value="Completa acquisto">
                </form>
            </div>
        </grid>
    </section>
</c:if>
<%@include file="footer.html"%>
