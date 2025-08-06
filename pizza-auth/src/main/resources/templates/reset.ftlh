<#ftl encoding="UTF-8">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Request to Reset Your Password</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #607FF2;
            font-size: 20px;
            height: 100%;
        }
        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }
        #a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="body">
    <div id="div">
        <h1 style="text-align: center">Request to reset your password.</h1>
        <p>Hello, ${name}!</p>
        <p>This is reset password mail. Use this code for change password.</p>
        <p>With best regards, <br>John's Ass.</p>
        <p>Code: ${token}</p>
    </div>
</div>
</body>
</html>