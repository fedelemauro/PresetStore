<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="operazione" value="${param.rimuovi != null ? 'Rimozione' : (prodotto == null ? 'Aggiungi' : 'Modifica')}"/>
<jsp:include page="/WEB-INF/jsp/header.jsp">
    <jsp:param name="pageTitle" value="${operazione} prodotto"/>
</jsp:include>

    <h1>File Upload</h1>

<form action="Upload" method="post" enctype="multipart/form-data">
    Descrizione: <input type="text" name="descrizione" /><br>
    File da caricare: <input type="file" name="file" /><br>
    <input type="submit" value="Invia" />
</form>


<%@include file="/WEB-INF/jsp/footer.html"%>


