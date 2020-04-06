$('#tb_users').bootstrapTable({
    url: "/adminAjax/users", // 请求的后台URL（*）
    method: 'get', // 请求方式：get/post（*）
    toolbar: '#toolbar', // 工具按钮用哪个容器
    cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    showRefresh: false, // 是否显示刷新按钮
    sortable: false, // 是否启用排序
    sortOrder: "asc", // 排序方式
    minimumCountColumns: 2, // 最少允许的列数
    showToggle: false, // 是否显示详细视图和列表视图的切换按钮
    showColumns: false, // 是否显示列操作按钮
    detailView: false, // 是否显示详细视图
    striped: true, // 设置为true会有隔行变色效果
    dataType: "json", // 服务器返回的数据类型
    pagination: true, // 设置为true会在底部显示分页条
    // queryParamsType : "limit",
    // 设置为limit则会发送符合RESTFull格式的参数
    singleSelect: true, // 设置为true将禁止多选
    clickToSelect: true, // 是否启用点击选中行
    cardView: false, // 是否显示详细视图
    paginationLoop: true,
    maintainSelected: true,
    // contentType : "application/x-www-form-urlencoded",
    // 发送到服务器的数据编码类型
    pageSize: 2, // 如果设置了分页，每页数据条数
    pageList: [2, 5, 10], // 可供选择的每页的行数（*）

    pageNumber: 1, // 如果设置了分布，首页页码
    search: false, // 是否显示搜索框
    uniqueId: "id", // 每一行的唯一标识，一般为主键列
    sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"

    queryParams: function(params) {
        return {
            // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
            limit: params.limit,
            pageSize: params.pageSize,
            offset: params.offset,
        };
    },
    responseHandler: function(res) {
        //console.log(res);
        return { //return bootstrap-table能处理的数据格式
            "total": res.totalCount,
            "rows": res.list
        }
    },
    // 请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
    // queryParamsType = 'limit' ,返回参数必须包含
    // limit, offset, search, sort, order 否则, 需要包含:
    // pageSize, pageNumber, searchText, sortName,
    // sortOrder.
    // 返回false将会终止请求
    columns: [{
        title: '序号',
        field: 'id',
        align: 'left',
        valign: 'center',
        width: '10%',
        formatter: function(value, row, index) {
            return index + 1;
        }

    }, {
        title: '用户ID',
        field: 'id',
        align: 'left',
        valign: 'center',

    }, {
        title: '用户姓名',
        field: 'username',
        align: 'center',
        valign: 'center',
        cellStyle: function(value, row, index) {
            return {
                css: {
                    "word-wrap": "break-word",
                    "word-break": "normal"
                }
            };
        }

    },{
        title: '性别',
        field: 'sex',
        align: 'center',
        valign: 'center',
        formatter: function (value, row, index) {
            return value=='M'?'男':'女';
        } //格式化日期
    },  {
        title: '出生日期',
        field: 'birth',
        align: 'center',
        valign: 'center',
        // formatter: function (value, row, index) {
        //  return ;
        // } //格式化日期
    },{
        title: 'Item Operate',
        align: 'center',
        field: 'id',
        clickToSelect: false,
        formatter:  function operateFormatter(value, row, index) {
            var btn = '<button type="button" id="delUser" class="btn btn-outline-danger" userId='
                + value
                + ' onclick="delUser(this)">删除</button><button type="button" id="dupUser" class="btn btn-outline-info" ' +
                ' onclick="updUser('+value + ')">修改</button>'
            return btn;
        }
    }]
});

// 删除用户
function delUser(dom) {
    var message = confirm("确认删除嘛？");
    if (message == true) {
        $.ajax({
            url : '/adminAjax/user/' + $(dom).attr("userId"),
            type : 'delete',
            success : function(data) {
                alert("删除成功");
                $('#tb_users').bootstrapTable('refresh');//刷新表格
            },
            error : function(data){
                alert("服务器繁忙")
            }
        });
    }
}

// 编辑用户
function updUser(id) {
    $("#modalTitle").text("编辑");
    $.ajax({
        url : '/adminAjax/user/' +id,
        type : 'get',
        success : function(data) {
            $('#userOfId').show().attr('readonly',"readonly").val(data.id);
            $("#username").val(data.username);
            $('#password').attr('hidden','hidden').val(data.password);
            if(data.sex=='M'){
                $('#sexM').attr("checked","checked");
            } else {
                $('#sexF').attr("checked","checked");
            }
            $('#birth').val(data.birth);
            $("#modalEdit").modal("show");
        },
        error : function(data){
            alert("服务器繁忙")
        }
    });
    //console.log("测试");
}

//"新增"按钮
$(".btn-add").click(function() {
    $("#modalTitle").text("添加新用户");
    $('#idDiv').hide();
    $("#username").val("antRain");
    $('#password').val("123456").removeAttr('hidden');
    $('#sexM').attr("checked","checked");
    $('#birth').val('1999-02-23');
    $("#modalEdit").modal("show");
});


$("#saveButton").click(function () {
    var modalTitle = $("#modalTitle").html();
    var method ="post";
    var username = $('#username').val();
    var password = $('#password').val();
    var id = $('#userOfId').val();
    var sex =$('input[name="sex"]:checked').val();
    var birth = $("#birth").val();
    var user ={
        "username":username,
        "password":password,
        "sex":sex,
        "birth":birth
    };
    if(modalTitle=="编辑"){
        method = 'put';
        user.id = id;
    }
    $.ajax({
        url : '/adminAjax/user',
        type : method,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(user),//把 JavaScript 对象转换为字符串。
        success : function(data) {
            $('#tb_users').bootstrapTable('refresh');//刷新表格
        },
        error : function(data){
            alert("服务器繁忙")
        }
    });
    $("#modalEdit").modal("hide");
});

