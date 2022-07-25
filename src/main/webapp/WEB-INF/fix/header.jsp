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
    <link rel="stylesheet" href="css/header.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <title>InChat</title>
</head>
<body>
<div class="header_wrap">
    <div class="container">
        <span>
            <a class="logo" onclick="location.href = '/index'">InChat></a>
        </span>
        <ul class="gnb">
            <c:set var="log" value="${log}"/>
            <c:choose>
                <c:when test="${log!=null}">
                    <c:out value='
                <li onclick="logOutAction()">
                    <span>Log Out</span>
                </li>
                <li onclick="location.href=`/mypage`">
                    <span >My Page</span>
                </li>
                ' escapeXml="false"/>
                </c:when>
                <c:otherwise>
                    <c:out value='
                <li onclick="location.href = `/login`">
                    <span>Log In</span>
                </li>
                <li onclick="location.href = `/joinIn`">
                    <span>Join In</span>
                </li>
                ' escapeXml="false"/>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
