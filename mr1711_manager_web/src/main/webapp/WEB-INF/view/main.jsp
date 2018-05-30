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
<head>
    <title>Title</title>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'North Title',split:true" style="height:100px;">
    <h1>后台管理系统</h1>
</div>
<div data-options="region:'west',title:'West',split:true" style="width:160px;">
    <%--tree--%>
        <ul id="Mytree" class="easyui-tree">
            <li>
                <span>欢迎光临管理系统</span>
                <ul>
                    <li>
                        <ul>
                            <li>
                                <span><a href="javascript:addTabs('学生管理系统','<%=path%>/tolist/')">学生管理系统</a></span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    <%--tree--%>
</div>
<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
<%--选项卡--%>
    <div id="my_tabs" class="easyui-tabs" style="width:500px;height:250px;" fit="true">
    </div>
</div>
</body>
<script>
    function addTabs(tit,url) {
        var exi = $('#my_tabs').tabs('exists', tit);
        if (!exi) {
            $('#my_tabs').tabs('add', {
                title: tit,
                content: '<iframe style="width:100%;height:100%;position:relative;" src="' + url + '" frameborder="0" scrolling="true" ></iframe>',
                selected: true,
                closable: true,
                tools: [{
                    iconCls: 'icon-mini-refresh',
                    handler: function () {
                        var tab = $('#my_tabs').tabs("getSelected");
                        $('#my_tabs').tabs('update', {
                            tab: tab,
                            options: {}
                        });
                    }
                }]
            });
        } else {
            $('#my_tabs').tabs('select', tit);
        }
    }
</script>
</html>
