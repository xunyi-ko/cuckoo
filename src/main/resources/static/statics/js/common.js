var layer, form, laydate;
layui.use(['layer', 'form', 'laydate'], function(){
    layer = layui.layer, form = layui.form, laydate = layui.laydate;
})

function msg(string, func){
    layer.msg(string, {icon: 1, time: 2000}, func);
}

function alert(string, func){
    layer.msg(string, {icon: 5, time: 2000}, func);
}

function confirm(string, func){
    layer.confirm(string, {icon: 3}, func);
}

/**
 * post方式跳转页面
 * @param url   跳转路径
 * @param params    参数  可以为空
 * @param ifJump    是否打开新页面 可以为空，默认当前页面打开
 * @returns
 */
function post(url, params, ifJump){
    var tempForm = document.createElement("form");
    tempForm.action = url;
    tempForm.method = "post";
    if(ifJump){
        tempForm.target="_blank";
    }
    document.body.appendChild(tempForm);

    var length = $("#nav.nav-xs").length;
    
    if(!$.isEmptyObject(params)){
        for ( var x in params) {
            var tempInput = document.createElement("input");
            tempInput.type = 'hidden';
            tempInput.name = x;
            tempInput.value = params[x];
            tempForm.appendChild(tempInput);
    
            // 电脑版时记录滚动条高度
            if (length == 0 && x == "top") {
                document.cookie = "top=" + params[x];
            }
        }
    }
    tempForm.submit();
}

//国际化资源
function getText(key, para) {
  var tt = "";
  try {
      tt = $.i18n.prop(key, para);
  } catch (err) {
  }
  if (tt != "" && tt.indexOf(key) == -1) {
      // 替换成功
      return tt;
  }
  
  jQuery.i18n.properties({// 加载资浏览器语言对应的资源文件
      name : 'strings', // 资源文件名称
      path : '/statics/i18n/', // 资源文件路径
      mode : 'map', // 用Map的方式使用资源文件中的值
      language : 'zh',
      callback : function() {// 加载成功后设置显示内容
          tt = $.i18n.prop(key, para);
      }
  });
  return tt;
}

function getParams(){
    // 参数列表
    var params = {};
    // 标志位。由于layui.each内部return并不会退出function，所以用标志位来判断是否需要退出function
    var flg = false;
    var doms = $(".param:visible");
    layui.each(doms, function(index, item){
        var dom = $(item);
        var label = dom.find("label");
        
        var id;
        var value;
        if(label && label.length != 0){
            var name = label.html();
            var input = dom.find("input, select, textarea");
            
            if(input.attr("type") == "radio"){
                id = input.attr("name");
                value = $("input[name=" +id+ "]:checked").val();
            }else if(input.attr("type") == "checkbox"){
                id = input.attr("name");
                var boxes = $("input[name=" +id+ "]:checked");
                value = new Array();
                for(var i=0;i<boxes.length;i++){
                    value.push($(input[i]).val());
                }
            }else{
                id = input.attr("id");
                value = input.val();
            }
            // 检验非空
            // 不能为空
            if(label.hasClass("require")){
                if(!value && value !== 0){
                    alert(name + getText("common.canntBeNull"));
                    return flg = true;
                }
            }
            
            // 检验长度
            var maxlength = input.prop("maxlength");
            if(maxlength > 0){
                if(value && value.length > maxlength){
                    alert(name + getText("common.lengthCheck1") + maxlength + getText("common.lengthCheck2"));
                    return flg = true;
                }
            }
            
            params[id] = value;
        }else{
            id = dom.prop("id");
            value = dom.val();
            
            if(dom.hasClass("require")){
                if(!value && value !== 0){
                    alert("参数缺失");
                    return flg = true;
                }
            }
            params[id] = value;
        }
    })
    if(flg){
        return;
    }
    return params;
}