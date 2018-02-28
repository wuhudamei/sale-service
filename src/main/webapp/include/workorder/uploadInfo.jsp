<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div v-if="links.length>0" class="form-group">
    <label for="problem" class="col-sm-2 control-label"></label>
    <div class="col-sm-8">
        <ul>
            <li v-for="link in links" style="float: left;list-style-type:none;">

                <a v-if="link.a!=''" :href="link.a" target="_blank">
                    <img :src="link.a" alt="" style="width: 105px;margin-left: 60px">
                </a>
            </li>
        </ul>
    </div>
</div>