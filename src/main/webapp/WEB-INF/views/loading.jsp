<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-28
  Time: 오전 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../fix/header.jsp" %>
<c:choose>
    <c:when test="${log==null}">
        <c:redirect url="/index"/>
    </c:when>
</c:choose>
<div class="wrap">
    <div>
        <p id="loading">Loading</p>
    </div>
</div>
<script>
    const loading = $("#loading");
    const user = {
        "id": '${log.getId()}'
    };

    let order = false;

    jQuery.ajax({
        type: "POST",
        url: "/v1/queue-addition",
        contentType: 'application/json',
        data: JSON.stringify(user),
        datatype: "JSON",
        success: function (e) {
            console.log(e);
            if (e === true) {
                location.href = "/chatPage";
                order = true;
            }
        },
        error: function () {
        }
    });

    let timer = 0;

    setInterval(() => {
        if (timer !== 3) {
            loading.append(" .");
            timer++;
        } else if (timer === 3) {
            loading.html("Loading");
            timer = 0;
        }
    }, 1000);
</script>
<%@ include file="../fix/footer.jsp" %>
