$(function(){
    
})

//发送消息
function entry() {
    var form = getParams();
    if($.isEmptyObject(form)){
        return;
    }
    
    post("/room/entry", form, true);
}
