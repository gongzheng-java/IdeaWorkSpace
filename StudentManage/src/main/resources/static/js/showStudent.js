/**
 * Created by gongzheng on 2019-08-06.
 *
 */

$(document).ready(function (){

    /**
     * 表单提交
     */
    $("#query_btn").click(function(){

      /* var  createtime=$("input[name='createtime']").val();
       alert("时间值："+createtime);
       alert($('input[name="createtime"]').attr('type'));*/

        /*var name = $.trim($("#username").val());

         var pwd = $.trim($("#pwd").val());
         if(name == ""){
         alert("姓名不能为空");
         return;
         }
         if(pwd == ""){
         alert("密码不能为空")
         return;
         }*/
        $("#loginForm").submit();
    });

    // 全选下拉框按钮
    $(".parentCheckBox").click(function() {
        if (this.checked) {
            $(".subCheckBox").each(function() {
                this.checked = true;
            });
        } else {
            $(".subCheckBox").each(function() {
                this.checked = false;
            });
        }
    });

    $('#js-export').click(function(){
        window.location.href="/exportFile/excel";
    });

});





