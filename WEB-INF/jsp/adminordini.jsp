<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="operazione" value="${param.rimuovi != null ? 'Rimozione' : (categoria == null ? 'Aggiungi' : 'Modifica')}"/>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Admin Ordini"/>
</jsp:include>
<section>
    <h1>Pannello Ordini</h1>

    <table>
        <thead>
        <tr>
            <th>ID ORDINE</th>
            <th>TOTALE</th>
            <th>ID UTENTE</th>
            <th>NOME UTENTE</th>
            <th>EMAIL</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${ordini}" var="ordine">
            <tr>
                <td>${ordine.id}</td>
                <td>${ordine.prezzoEur}</td>
                <td>${ordine.utente.id}</td>
                <td>${ordine.utente.nome}</td>
                <td>${ordine.utente.email}</td>

                <td></td>

                <td>
                    <form action="AdminOrdini" method="post">
                        <input type="hidden" name="id" value="${ordine.id}">
                        <input type="submit" name="rimuovi" value="Rimuovi" >
                        <input type="submit" name="dettagli" value="Dettagli" >
                    </form>

                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</section>
<%@include file="footer.html"%>