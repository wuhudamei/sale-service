<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-group">
    <label for="remarks" class="col-sm-2 control-label">操作记录</label>

    <table class="col-sm-10 table table-bordered" id="remarks">
        <thead>
        <td>编号</td>
        <td>操作人</td>
        <td>操作类型</td>
        <td>操作内容</td>
        <td>操作时间</td>
        </thead>
        <tr v-for="(index, remark) in remarks">
            <td>{{$index+1}}</td>
            <td>{{remark.operationUser.name}}</td>
            <td>{{remark.operationName}}</td>
            <td>{{remark.remark}}</td>
            <td>{{remark.operationDate}}</td>
        </tr>
    </table>
</div>