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
<h2>Top 10 players</h2>
<table class="w3-table-all">
    <thead>
    <tr class="w3-green">
        <th>Place</th>
        <th>Name</th>
        <th>Shot</th>
    </tr>
    </thead>
    <tr>
        <td>1</td>
        <td>Smith</td>
        <td>50</td>
    </tr>
    <tr>
        <td>2</td>
        <td>Jackson</td>
        <td>94</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Johnson</td>
        <td>67</td>
    </tr>
</table>
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
    function drawTopTable() {
        fetch("<c:url value='/api/game/top'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (cells) {
            cells.forEach(function myFunction(myObject) {
                w3.displayObject("id01", myObject);
            });
        });
    }
</script>
</body>
</html>
