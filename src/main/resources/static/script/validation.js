function joinAction(user){
    const uri = "/v1/add-user"
    const redirect_uri = "/index"
    sendAjaxNoReturn("Join",user,uri,redirect_uri)
}

function logOutAction(){
    const uri = "/v1/logout-user"
    const redirect_uri = "/index"
    sendAjaxNoReturn("Log Out", null, uri,redirect_uri)
}

function updateAction(user){
    const uri = "/v1/update-user"
    const redirect_uri = "/mypage"

    sendAjax("Update",user,uri,redirect_uri)
}

function deleteAction(id){
    let user = {
        "id" : id
    }
    const uri = "/v1/delete-user"
    const redirect_uri = "/index"
    sendAjaxNoReturn("Delete",user,uri,redirect_uri)
}

function sendAjax(type, data, uri,redirect_uri){
    jQuery.ajax({
        type: "POST",
        url: uri,
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: "JSON",
        success: function (e) {
            if(e !== null) {
                alert(type+" Success!")
                location.href = redirect_uri
            }
            else{
                alert(type+" Failed");
            }
        },
        error : function (request,error){
            alert(type+" Failed");
            //alert("code : "+request.status+"\nmessage : "+request.responseText+"\nerror : "+error)
        }
    });
}

function sendAjaxNoReturn(type, data, uri,redirect_uri){
    jQuery.ajax({
        type: "POST",
        url: uri,
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: "JSON",
        success: function () {
            alert(type+" Success!");
            location.href = redirect_uri;
        },
        error : function (){
            alert(type+" Success");
            location.href = redirect_uri;
            //alert("code : "+request.status+"\nmessage : "+request.responseText+"\nerror : "+error)
        }
    });
}

function addQueue(user){
    jQuery.ajax({
        type: "POST",
        url: "/v1/queue-addition",
        contentType: 'application/json',
        data: JSON.stringify(user),
        datatype: "JSON",
        success: function (e) {
        },
        error: function () {
            addQueue(user);
        }
    });
}

function checkQueue(user){
    jQuery.ajax({
        type: "POST",
        url: "/v1/check-queue",
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: "JSON",
        success: function (e) {
            console.log("e : " + e);
            console.log("validation : "+ validation);
            if(e === true){
                validation = true;
            }
        }
    });
}

function waitQueue(user){
    jQuery.ajax({
        type: "POST",
        url: "/v1/wait-queue",
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: "JSON",
        success: function (e) {
            room = e;
            console.log("room no : "+room);
            if(e!==0){
                location.href = "/chatPage?room-code="+e;
            }
        }
    });
}