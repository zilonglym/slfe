<!DOCTYPE html>
<html>

<head>

<#include "../../head.ftl">
    <title>运行监测管理-监控日志</title>
    <style type="text/css">
        #toolbar input, button, select {
            float: left;
            margin-top: 10px;
            margin-left: 10px;
            margin-bottom: 10px;
            font-size: 12px;
        }

    </style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div class="example-wrap">

    <div class="hidden-xs" id="toolbar" role="group">
        <input style="width:250px;" name="resourceName" id="resourceName" placeholder="资源名称"
               class="input-sm form-control">

        <input style="width:250px;" name="startTime" id="startTime" placeholder="开始时间"
               class="input-sm form-control">

        <input style="width:250px;" name="endTime" id="endTime" placeholder="截止时间"
               class="input-sm form-control">

    <#--</select>-->
        <button id="query" type="button" class="btn btn-sm btn-primary">
            搜索
        </button>

    </div>
    <table id="table" data-height="400" data-mobile-responsive="true">

    </table>

</div>

<script type="text/javascript">
    (function (base) {
        //初始化事件
        function initEvent() {

            laydate.render({
                elem: '#startTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
            });

            laydate.render({
                elem: '#endTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
            });

            //查询按钮事件
            $('#query').click(function () {
                $('#table').bootstrapTable('refresh', {
                    pageNumber: 1
                });
            })
        };

        //得到查询的参数
        function queryParams(params) {

            var temp = {
                limit: params.limit,    //页面大小
                offset: params.offset,   //页码
                resourceName: $('#resourceName').val(),
                startTime: $('#startTime').val(),
                endTime: $("#endTime").val(),

            };
            return temp;
        };

        function refreshTable() {
            $('#query').click();
        }


        function initTable() {
            $('#table').bootstrapTable({
                url: base + '/monitorLog/getList',    //请求后台的URL（*）
                method: 'post',                     //请求方式（*）
                contentType: "application/json",
                toolbarAlign: 'right',               //工具栏对齐方式
                buttonsAlign: 'right',               //按钮对齐方式
                striped: false,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: true,                    //是否启用排序
                sortOrder: "asc",                   //排序方式
                sortName: "id",                  // 排序字段
                queryParams: queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [15, 25, 50, 100],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 650,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                cardView: false,                    //是否显示详细视图
                detailView: false,                  //是否显示父子表
                showRefresh: false,                   //刷新按钮
                columns: [{
                    field: 'resourceCode',
                    title: '资源名称'
                }, {
                    field: 'sourceExchangerName',
                    title: '数据来源'
                }, {
                    field: 'operatorName',
                    title: '操作人'
                }, {
                    field: 'operator',
                    title: '操作账号'
                }, {
                    field: 'operatorTimeStr',
                    title: '操作时间'
                }, {
                    field: 'operatorTypeStr',
                    title: '操作类型'
                }, {
                    field: 'remark',
                    title: '备注'
                }]
            });
        }

        $(function () {
            initTable();
            initEvent();
        });
    })(base);
</script>
</body>

</html>
