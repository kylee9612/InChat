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
    <div class = "right_bg">
        <video autoplay loop muted>
            <source src="video/index_text.mp4" type="video/mp4">
        </video>
    </div>
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
        <button onclick="location.href = '/loading'">Start 1:1 Chat!</button>
        <button onclick ="location.href='/chatroom'">Start Group Chat!</button>
        <br>
        <button onclick ="location.href = '/community'" style = "width : 330px; margin: 0 0 0 1%;">To Our Community</button>
    </div>
</div>
<%@ include file="../fix/footer.jsp"%>