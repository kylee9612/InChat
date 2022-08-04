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
>
<script>
    const loading = $("#loading");
    const user = {
        "id": '${log.getId()}'
    };
    let validation = false;
    let room = null;
    let timer = 0;

    addQueue(user);

    function queueWork() {
        if (validation === true) {
            waitQueue(user);
        } else {
            checkQueue(user);
        }
    }

    setInterval(() => {
        if (timer !== 3) {
            loading.append(" .");
            timer++;
        } else if (timer === 3) {
            loading.html("Loading");
            timer = 0;
        }
        queueWork();
    }, 1000);

    document.addEventListener("keydown", event => {
        if(event.keyCode === 116){
            delQueue();
        }
    })

    function delQueue(){
        jQuery.ajax({
            type: "POST",
            url: "/v1/delete-queue",
            contentType: 'application/json',
            data: JSON.stringify(user),
            dataType: "JSON",
        });
    }

</script>
<%@ include file="../fix/footer.jsp" %>
