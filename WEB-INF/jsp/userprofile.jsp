<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><%@taglib prefix="c"
                                         uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Profilo"/>
</jsp:include>


<h1>Profilo Utente</h1>
<h5>${notifica}</h5>
<form name="profiloutente" action="UserProfile" method="post">
    <input type="hidden" name="id" value="${utente.id}">
    <label>Nome (solo lettere e spazi)</label>
    <input type="text" name="nome" oninput="validaNome()" style="border-style: groove" value="${utente.nome}">
    <label>Username (almeno 6 caratteri, solo lettere e numeri, non utilizzato da altri utenti)</label>
    <input type="text" name="username" oninput="validaUsername()" style="border-style: groove" value="${utente.username}" >
    <label>Email</label>
    <input type="text" name="email" oninput="validaEmail()"style="border-style: groove" value="${utente.email}">

    <input type="submit" name="moduser" id="moduser" value="modifica" disabled>
</form>

<script>
    var borderOk = '2px solid #080';
    var borderNo = '2px solid #f00';
    var usernameOk = false;

    var nomeOk = false;
    var emailOk = false;

    function validaUsername() {     //AJAX
        var input = document.forms['profiloutente']['username'];
        if (input.value.length >= 6 && input.value.match(/^[0-9a-zA-Z]+$/)) {
            // verifica se esiste un utente con lo stesso username
            var xmlHttpReq = new XMLHttpRequest();
            xmlHttpReq.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200
                    && this.responseText == '<ok/>') {
                    usernameOk = true;
                    input.style.border = borderOk;
                } else {
                    input.style.border = borderNo;
                    usernameOk = false;
                }
                cambiaStatoRegistrami();
            }
            xmlHttpReq.open("GET", "VerificaUsername?username="
                + encodeURIComponent(input.value), true);
            xmlHttpReq.send();
        } else {
            input.style.border = borderNo;
            usernameOk = false;
            cambiaStatoRegistrami();
        }
    }


    function validaNome() {
        var input = document.forms['profiloutente']['nome'];
        if (input.value.trim().length > 0
            && input.value.match(/^[ a-zA-Z\u00C0-\u00ff]+$/)) {
            input.style.border = borderOk;
            nomeOk = true;
        } else {
            input.style.border = borderNo;
            nomeOk = false;
        }
        cambiaStatoRegistrami();
    }

    function validaEmail() {
        var input = document.forms['profiloutente']['email'];
        if (input.value.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/)) {
            input.style.border = borderOk;
            emailOk = true;
        } else {
            input.style.border = borderNo;
            emailOk = false;
        }
        cambiaStatoRegistrami();
    }

    function cambiaStatoRegistrami() {
        if (usernameOk && nomeOk && emailOk) {
            document.getElementById('moduser').disabled = false;

        } else {
            document.getElementById('moduser').disabled = true;
        }
    }
</script>


<%@include file="footer.html"%>