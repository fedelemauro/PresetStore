<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/header.jsp"><jsp:param name="pageTitle" value="Faq  "/></jsp:include>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>                //JQUERY
    $(document).ready(function(){
        $("#flip").click(function(){
            $("#panel").slideDown("slow");
        });
        $("#panel").click(function(){
            $("#panel").slideUp("slow");
        });
    });
</script>
<script>                //JQUERY
$(document).ready(function(){
    $("#flip1").click(function(){
        $("#panel1").slideDown("slow");
    });
    $("#panel1").click(function(){
        $("#panel1").slideUp("slow");
    });
});
</script>

<script>                //JQUERY
$(document).ready(function(){
    $("#flip2").click(function(){
        $("#panel2").slideDown("slow");
    });
    $("#panel2").click(function(){
        $("#panel2").slideUp("slow");
    });
});
</script>
<link href = "slidepanel.css" rel="stylesheet">
<br>
<br>


<ul>

<li><h6 id="flip" style="font-family: Impact; text-decoration: underline; ">Quando riceverò i miei preset? </h6>
<p id="panel">I preset verrano consegnati direttamente nella tua casella e-mail entro 24 ore dall'acquisto</p>
</li>

<li><h6 id="flip1" style="font-family: Impact; text-decoration: underline;">Su quali foto posso utilizzare i preset?</h6>
    <p id="panel1">Puoi utilizzarlo su tutte le foto che vuoi. L'importante è usare Adobe Lightroom per applicarli</p>
</li>

<li><h6 id="flip2" style="font-family: Impact; text-decoration: underline;">Su quali social conviene usarli ?</h6>
    <p id="panel2">Su tutti i social in circolazione, come Instagram, Facebook, Twitter, ecc.</p>
</li>

</ul>

<%@include file="/WEB-INF/jsp/footer.html"%>