var websocket;
$(function(){
    var roomType = $("#roomType").val();
    if(roomType == 0){
        // 加入房间(socket，第一类房间)
        doWebsocket();
    }
})

//websocket
function doWebsocket(){
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		if(top.location == self.location) { // && ($('.messageNum').length > 0 || $("#todoNum").length > 0)
			
			var host = window.location.host;
			var socketUrl = "ws://" + window.location.host + "/room/" + $("#name").val() + "/" + $("#roomId").val();
			websocket = new WebSocket(socketUrl);
			
			//连接错误
			websocket.onerror = function () {
				console.log("连接失败");
			};

			//连接成功建立的回调方法
			websocket.onopen = function () {
				send();
				console.log("连接成功");
			}

			//接收到消息的回调方法
			websocket.onmessage = function (event) {
				if(event.data != null && event.data != 'error' && typeof(event.data) != 'undefined'){
					var obj = JSON.parse(event.data);
					receiveMsg(obj);
				}
			}


			//连接关闭的回调方法
			websocket.onclose = function () {
			    //setMessageInnerHTML("WebSocket连接关闭");
			}
			//将消息显示在网页上
			function setMessageInnerHTML(innerHTML) {
			  document.getElementById('message').innerHTML += innerHTML + '<br/>';
			}

			//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
			window.onbeforeunload = function () {
			    closeWebSocket();
			    //closeWebSockethead();
			}

			//关闭WebSocket连接
			function closeWebSocket() {
				if(websocket.readyState == 1){
					websocket.close();
				}
			}

			function cancel(){
				var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
				parent.layer.close(index); // 再执行关闭
			}
			
		}
	}else {
		errorMsg(getI18Text("common-websocket-version"));
	}
}

//发送消息
function send() {
    var msg = $("textarea").val();
    if(websocket.readyState == 1){
        websocket.send(msg);
    }
    $("textarea").val("");
}

// 接收到消息
function receiveMsg(obj){
    if(!obj.msg || !obj.name){
        return;
    }
    var str;
    if($("#name").val() == obj.name){
        str = '<div class="box left">';
        str += '<div class="right">';
    }else{
        str = '<div class="box right">';
        str += '<div class="left">';
    }
    str += '<div class="name">' + obj.name + '</div>';
    str += '<div class="msg">' + obj.msg + '</div>';
    str += '</div></div>';
    
    $(".content").append(str);
}
//页面跳转，请空sessionStorage
function goHref(obj){
	clearSessionStorage();
	location.href = $(obj).data("href");
}