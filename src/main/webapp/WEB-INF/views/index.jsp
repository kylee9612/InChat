<%@ page import="com.inchat.inchat.domain.UserVO" %><%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../fix/header.jsp"%>
<head>
    <link rel = 'stylesheet' href = 'css/index.css'>
</head>
<div class = "wrap">
    <div class = "contents">
        <p class = "word">
           <span>WelCome To Spring World</span><br>
            <%
                UserVO userVO = null;
                if(request.getAttribute("login")!= null&&request.getAttribute("login").equals("failed")){
            %>
            <script>
                alert("Login Failed");
            </script>
            <span></span>
            <%}
                else if(request.getAttribute("userVO") != null){
                    userVO = (UserVO) request.getAttribute("userVO");
            %>
            <span><%=userVO.getNick()%></span>
            <%
            }else if(request.getAttribute("join") != null && request.getAttribute("join").equals("true")){
            %>
            <script>
                alert("Join Successful!");
            </script>
            <%
            }
            %>
        </p>
    </div>
</div>
<%@ include file="../fix/footer.jsp"%>