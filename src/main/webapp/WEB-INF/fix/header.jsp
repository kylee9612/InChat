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
    <meta property="og:title" content="InChat">
    <meta charset="UTF-8" property="og:description" content="실시간 그룹채팅과 일대일 채팅 및 커뮤니티 기능을 즐기세요!">
    <meta property="og:image" content="img/InChat.png">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="css/header.css">
    <title>InChat</title>
</head>
<body>
<script src="script/validation.js"></script>
<div class="header_wrap">
    <div class="container">
        <span>
            <a class="logo" onclick="movePage('/index')">InChat></a>
        </span>
        <ul class="gnb">
            <c:set var="log" value="${log}"/>
            <c:choose>
                <c:when test="${log!=null}">
                    <c:out value='
                <li onclick="logOutAction()">
                    <span>Log Out</span>
                </li>
                <li onclick="movePage(`/mypage`)">
                    <span >My Page</span>
                </li>
                ' escapeXml="false"/>
                </c:when>
                <c:otherwise>
                    <c:out value='
                <li onclick="movePage(`/login`)">
                    <span>Log In</span>
                </li>
                <li onclick="movePage(`/joinIn`)">
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
        }catch (error){
            location.href=uri;
        }
        location.href=uri;
    }
</script>

