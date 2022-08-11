<%@ page import="com.inchat.inchat.domain.UserVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 10:55
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <link rel="shortcut icon" href="img/InChat.png">
    <meta charset="UTF-8">
    <meta property="og:title" content="InChat">
    <meta property="og:description" content="Enjoy Live Chat!">
    <meta property="og:image" content="https://i.ibb.co/1mFZN0n/inchat-thumb.png">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="css/header.css">
    <title>InChat</title>
</head>
<body onbeforeunload="delQueue()">
<script src="script/validation.js"></script>
<div class="header_wrap">
    <div class="container">
        <span>
            <a class="logo" onclick="location.href='/index'">InChat></a>
        </span>
        <ul class="gnb">
            <c:set var="log" value="${log}"/>
            <c:choose>
                <c:when test="${log!=null}">
                    <c:out value='
                <li onclick="movePage(`/logout`)">
                    <span>Log Out</span>
                </li>
                <li onclick="location.href=`/mypage`">
                    <span >My Page</span>
                </li>
                ' escapeXml="false"/>
                </c:when>
                <c:otherwise>
                    <c:out value='
                <li onclick="location.href=`/login`">
                    <span>Log In</span>
                </li>
                <li onclick="location.href=`/joinIn`">
                    <span>Join In</span>
                </li>
                ' escapeXml="false"/>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<script>
    let sock;

    function movePage(uri){
        try {
            if (sock !== undefined) {
                onClose();
            }
            else if(location.href.indexOf("/loading") >= 0){
                delQueue();
            }
            else if(uri === "/logout"){
                logOutAction();
                return;
            }
        }catch (error){
            location.href=uri;
        }
        if(uri !== "")
            location.href=uri;
    }

    window.addEventListener("beforeunload",function (e){
        e.preventDefault();
        movePage("");
    });

</script>

