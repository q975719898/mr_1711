<%--
  <%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/4
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<link rel="stylesheet" type="text/css"
      href="<%=path%>/js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=path%>/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery-easyui/jquery.easyui.min.js"></script>
<script src="<%=path%>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<head>
    <title>list</title>
</head>
<body>
<table id="dg"></table>
</body>
<script>
    $(function(){
        ooo();
    })
    function ooo(){
        $('#dg').datagrid({
            url:'<%=path%>/TbUser/select/',
            fitColumns:true,//自适应
            singleSelect:true,
            pagination:true,
            pageSize:5,
            pageList:[5,10,15,20,25,30],
            columns:[[
                {field:'ck',align:'center',width:50,checkbox:true},
                {field:'id',title:'用户编号',width:100},
                {field:'username',title:'用户名称',width:100},
                {field:'password',title:'用户密码',width:100},
                {field:'phone',title:'用户手机',width:100},
                {field:'email',title:'用户邮箱',width:100},
                {field:'created',title:'创建时间',width:100},
                {field:'updated',title:'更新时间',width:100},
            ]],
            /* 工具栏开始 */
            toolbar: [{
                iconCls: 'icon-edit',
                text:'新增',
                handler: function(){
                    addGoods();
                }
            },'-',{
                iconCls: 'icon-help',
                text:'删除',
                handler: function(){
                    deleteGoods();
                }
            },'-',{
                iconCls: 'icon-help',
                text:'修改',
                handler: function(){
                    updateGoods();
                }
            }],
            /* 工具栏end */

        });
        /* datagrid End */
    }
</script>
</html>
