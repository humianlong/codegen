<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>更新用户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../../css/oksub.css">
    <script src="../../../js/okconfig.js"></script>
    <script type="text/javascript" src="../../../lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <!--form表单-->
    <form class="layui-form ok-form" lay-filter="filter">
        <input type="text" name="id" hidden>
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
                <button class="layui-btn" lay-submit lay-filter="edit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="../../../lib/layui/layui.js"></script>
<script>
    let initData,okLayer,upload,okUtils;
    function initForm(data) {
        let jsonString = JSON.stringify(data);
        initData = JSON.parse(jsonString);
        layui.use(["element", "form", "laydate","upload", "okLayer", "okUtils"], function () {
            let form = layui.form;
            okUtils = layui.okUtils;
            upload = layui.upload;
            let laydate = layui.laydate;
            okLayer = layui.okLayer;
            okLoading.close();
            form.val("filter", initData);
            form.on("submit(edit)", function (data) {
                okUtils.ajax("/${config.catalog}/${table.javaNameL}/save", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("编辑成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });

        })
    }

</script>
</body>
</html>
