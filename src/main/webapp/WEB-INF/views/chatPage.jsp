<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-27
  Time: 오후 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/chatroom.css">
</head>
<c:if test="${log==null}">
    <c:redirect url="/index"/>
</c:if>
<div class="wrap">
    <div class="contents">
        <div id="text-area">
            <div id="text-input">
            </div>
            <div id="input_area">
                <input type="text" id="msg" placeholder="Type a message..." required>
                <button type="submit" class="btn" id="button_send" onclick="sendMessage()">전송</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>
    let roomId = ${room.getRoom_code()};
    let username = ${log.getNickname()};

    let sock = new SockJS("/stomp/chat");
    let stomp = Stomp.over(sock);

    document.addEventListener("keydown",event=>{
        if(event.keyCode===13){
            sendMessage();
        }
    })

    function sendMessage() {
        let msg = document.getElementById("msg");
        console.log(username + ":" + msg.value);
        if(msg.value !== '') {
            stomp.send('/pub/chat/message',{},JSON.stringify({
                roomId : roomId,
                message : msg.value,
                writer : username
            }));
            msg.value = '';
        }
    }

    stomp.connect({}, function() {

        stomp.subscribe("/sub/chat/room"+roomId, function (chat){
            let content = JSON.parse(chat.body);

            let writer = content.writer;
            let str = "<div class='alert " + target + "'>";
            str += "<p>" + writer + " : " + message + "</p>";
            str += "</div></div>";
            let target = '';
            if(writer===usernick){
                target = 'first';
            }else{
                target = 'secondary';
            }
            $("#text-input").append(str);
        })

        stomp.send('pub/chat/enter',{}, JSON.stringify({roodId : roomId, writer : username}));
    });
</script>
<%@ include file="../fix/footer.jsp" %>