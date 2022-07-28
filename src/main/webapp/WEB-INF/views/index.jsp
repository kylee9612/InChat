<%@ page import="com.inchat.inchat.domain.UserVO" %><%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../fix/header.jsp"%>
<head>
    <link rel = 'stylesheet' href = 'css/index.css'>
</head>
<div class = "wrap">
    <div class = "bg" src="img/bg.png"></div>
    <div class = "contents">
        <p class = "word">
           <span>WelCome To
               <c:choose>
               <c:when test = "${log==null}">
                   Spring
               </c:when>
                <c:otherwise>
                    <c:out value="${log.getNickname()}"/>
                </c:otherwise>
               </c:choose>
               World
           </span><br>
        </p>
        <button onclick="location.href = '/loading'">Start to Chat!</button>
        <button onclick ="location.href='/chatroom'">Start Group Chat!</button>
    </div>
</div>
<%@ include file="../fix/footer.jsp"%>