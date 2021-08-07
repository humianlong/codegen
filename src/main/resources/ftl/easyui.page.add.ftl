<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../../css/oksub.css">
    <script src="../../../js/okconfig.js"></script>
    <script type="text/javascript" src="../../../lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <!--form表单-->
    <form class="layui-form layui-form-pane ok-form">
        <#list table.columns as column>
            <#if column.view>
                <div class="layui-form-item">
                    <label class="layui-form-label">${column.comment}</label>
                    <div class="layui-input-block">
                        <input type="text" name="${column.javaName}" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </#if>
        </#list>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="add">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="../../../lib/layui/layui.js"></script>
<script>
    let okLayer,upload,okUtils;
    layui.use(["element", "form", "laydate","upload", "okLayer", "okUtils"], function () {
        let form = layui.form;
        let laydate = layui.laydate;
        upload = layui.upload;
        okLayer = layui.okLayer;
        okUtils = layui.okUtils;
        okLoading.close();

        form.on("submit(add)", function (data) {
            okUtils.ajax("/${config.catalog}/${table.javaNameL}/save", "post", data.field, true).done(function (response) {
                console.log(response);
                okLayer.greenTickMsg("添加成功", function () {
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                });
            }).fail(function (error) {
                console.log(error)
            });
            return false;
        });

    });
</script>
</body>
</html>
