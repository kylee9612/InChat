<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-08-01
  Time: 오후 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/community.css">
</head>
<c:set var="pageParam" value="${param.page}"/>
<c:if test="${pageParam eq null}">
    <c:set var="pageParam" value="1"/>
</c:if>
<fmt:formatNumber value="${pageParam}" var="pageNo" type="number"/>
<div class="wrap">
    <h2 class="pageTitle">자유게시판</h2>
    <div class="contents">
        <ul>
            <li class="nav">
                <label class="no">번호</label>
                <label class="writer">작성자</label>
                <label class="title">제목</label>
                <label class="createdAt">작성일</label>
                <label class="viewCount">조회수</label>
            </li>
            <div id="cons">
            <c:if test="${boardList.size() != 0}">
                <c:set var="pages" value="${boardList.size()%7==0 ? boardList.size()/7 : boardList.size()/7+1}"/>
                <c:forEach var="board" items="${boardList}" begin="${(pageNo-1)*7}" end="${pageNo*7-1}">
                    <li class = "board" onclick="location.href='/communityView?code=<c:out value="${board.getCode()}"/> '">
                        <p class="no">${board.getCode()}</p>
                        <p class = "writer">${board.getWriter()}</p>
                        <p class="title">${board.getTitle()}</p>
                        <p class="createdAt">${fn:substring(board.getCreatedAt(),0, 10)}</p>
                        <p class="viewCount">${board.getViewCount()}</p>
                    </li>
                </c:forEach>
            </c:if>
            </div>
            <p style="text-align: center; margin-top : 5%">
                <c:forEach var="sta" begin="1" end="${pages}">
                    <span style="cursor: pointer" onclick="location.href='/community?page=${sta}'"><c:out value="${sta} "/></span>
                </c:forEach>
            </p>
            <button class="write_btn" onclick="location.href='/communityWrite?page=${pageNo}'">글쓰기</button>
            </ul>
    </div>
</div>

<%@ include file="../fix/footer.jsp" %>