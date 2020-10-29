<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="operazione" value="${param.rimuovi != null ? 'Rimozione' : (prodotto == null ? 'Aggiungi' : 'Modifica')}"/>
<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="pageTitle" value="${operazione} prodotto"/>
</jsp:include>
<h3>File caricato con successo: <a href="${uploaded}">${uploaded}</a>.<br></h3>
<ul>
<li><h2><a href="upload.jsp">Carica una nuova immagine</a></h2>
<li><h2><a href="AdminProdotto">Ritorna alla creazione prodotto</a></h2></li>
</ul>

<%@include file="/WEB-INF/jsp/footer.html"%>