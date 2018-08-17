<%--
  Created by IntelliJ IDEA.
  User: Margo
  Date: 17.08.2018
  Time: 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>The game ended</title>
    <style>

    </style>
</head>
<body onload="activePlayer()">
    <h1>The game ended</h1>
<div id="win" class="w3-hide">
    <h2>You win!</h2>
</div>
<div id="lose" class="w3-hide">
    <h2>You lose!</h2>
</div>
<script>
    function activePlayer() {
        console.log("checking status");
        fetch("<c:url value='/api/game/status'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (game) {
            if ( game.playerActive) {
                document.getElementById("win").classList.remove("w3-hide");
                document.getElementById("lose").classList.add("w3-hide");
            } else {
                document.getElementById("win").classList.add("w3-hide");
                document.getElementById("lose").classList.remove("w3-hide");
            }
        });
    }
</script>

</body>
</html>
