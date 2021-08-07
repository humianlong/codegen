<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../../css/oksub.css">
    <script type="text/javascript" src="../../../lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <!--模糊搜索区域-->
    <#--<div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">
            <input class="layui-input" placeholder="姓名" autocomplete="off" name="name">
            <input class="layui-input" placeholder="手机号" autocomplete="off" name="tel">
            <button class="layui-btn" lay-submit="" lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </form>
    </div>-->
    <!--数据表格-->
    <table class="layui-hide" style="table-layout: fixed" id="tableId" lay-filter="tableFilter"></table>
</div>
<!--js逻辑-->
<script src="../../../js/okconfig.js"></script>
<script src="../../../lib/layui/layui.js"></script>
<script>
    layui.use(["element", "jquery", "table", "upload", "form", "laydate", "okLayer", "okUtils", "okMock"], function () {
        let table = layui.table;
        let form = layui.form;
        let laydate = layui.laydate;
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let $ = layui.jquery;

        okLoading.close($);
        let userTable = table.render({
            elem: '#tableId',
            // startByZero : 0,
            url: getTokenUrl('/${config.catalog}/${table.javaNameL}/listByPage'),
            limit: 20,
            where:{
                scenicCode : sessionStorage.getItem("scenicCode")
            },
            request:{
                pageName: 'pageNo',
                limitName: 'pageSize'
            },
            method : 'post',
            page: true,
            toolbar: true,
            toolbar: "#toolbarTpl",
            size: "lg",
            cols: [[
                {type: "checkbox", fixed: "left"},
                <#list table.columns as column>
                <#if column.view>
                {field: "${column.javaName}", title: "${column.comment}", width: "10%"},
                </#if>
                </#list>
                {title: "操作", width: "10%", align: "center", fixed: "right", templet: "#operationTpl"}
            ]],
            parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.rows //解析数据列表
                };
            },
            done: function (res, curr, count) {

            }
        });

        form.on("submit(search)", function (data) {
            userTable.reload({
                where: data.field,
                page: {curr: 0},
                scenicCode : sessionStorage.getItem("scenicCode")
            });
            return false;
        });

        table.on("toolbar(tableFilter)", function (obj) {
            switch (obj.event) {
                case "batchEnabled":
                    batchEnabled();
                    break;
                case "batchDisabled":
                    batchDisabled();
                    break;
                case "batchDel":
                    batchDel();
                    break;
                case "add":
                    add();
                    break;
                case "batchAdd":
                    batchAdd();
                    break;
            }
        });

        table.on("tool(tableFilter)", function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case "edit":
                    edit(data);
                    break;
                case "disabled":
                    disabled(data);
                    break;
                case "enable":
                    enable(data);
                    break;
                case "resetPswd":
                    resetPswd(data);
                    break;
                case "setRole":
                    setRole(data);
                    break;
                case "del":
                    del(data.id);
                    break;
            }
        });

        function batchDel() {
            okLayer.confirm("确定要批量删除吗？", function (index) {
                layer.close(index);
                let idsStr = okUtils.tableBatchCheckCol(table,'id');
                if (idsStr) {
                    okUtils.ajax("/${config.catalog}/${table.javaNameL}/delete", "post", {ids: idsStr}, true).done(function (response) {
                        console.log(response);
                        okUtils.tableSuccessMsg(response.message);
                    }).fail(function (error) {
                        console.log(error)
                    });
                }
            });
        }

        function add() {
            okLayer.open("添加", "${table.javaNamePage}-add.html", "90%", "90%", null, function () {
                userTable.reload();
            })
        }

        function edit(data) {
            okLayer.open("更新", "${table.javaNamePage}-edit.html", "90%", "90%", function (layero) {
                let iframeWin = window[layero.find("iframe")[0]["name"]];
                okUtils.ajax("/${config.catalog}/${table.javaNameL}/get/"+data.id, "get", null, true).done(function (response) {
                    iframeWin.initForm(response.data);
                }).fail(function (error) {
                    console.log(error)
                });
            }, function () {
                userTable.reload();
            })
        }

        function del(id) {
            okLayer.confirm("确定要删除吗？", function () {
                okUtils.ajax("/${config.catalog}/${table.javaNameL}/delete", "post", {ids: id}, true).done(function (response) {
                    console.log(response);
                    okUtils.tableSuccessMsg(response.message);
                }).fail(function (error) {
                    console.log(error)
                });
            })
        }
    })
</script>
<!-- 头工具栏模板 -->
<script type="text/html" id="toolbarTpl">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDel">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
    </div>
</script>
<!-- 行工具栏模板 -->
<script type="text/html" id="operationTpl">
    <button type="button" class="layui-btn layui-btn-warm layui-btn-xs" title="编辑" lay-event="edit">编辑</button>
    <button type="button" class="layui-btn layui-btn-danger layui-btn-xs" title="删除" lay-event="del">删除</button>
</script>
</body>
</html>
