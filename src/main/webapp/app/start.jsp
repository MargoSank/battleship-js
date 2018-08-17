<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>
<div class="w3-container" style="display: inline-block">
    <p>
        <button type="button" class="w3-btn w3-red w3-border w3-round-large" onclick="logout()">Log out</button>
    </p>
</div>
<div class="w3-container" style="display: inline-block">
    <p>
        <button type="button" class="w3-btn w3-green w3-border w3-round-large" onclick="startGame()">Start Game</button>
    </p>
</div>
<script>
    function logout() {
        fetch("<c:url value='/api/auth/logout'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/";
            });
    }
    function startGame() {
        fetch("<c:url value='/api/game'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/app/placement.jsp";
            });
    }
</script>
</body>
</html>
