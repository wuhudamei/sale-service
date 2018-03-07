package cn.damei.rest.sale.workorder;

import cn.damei.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Deprecated
@Controller
public class OldDateDeal extends BaseController {

    @Autowired
    private DataSource dataSource;

    //@RequestMapping(value = "/damei/oldDate/do")
    public void deal(HttpServletResponse response){

        response.setContentType("utf-8");

        Connection con = null;

        Statement workOrderState = null;
        Statement custState = null;
        Statement custIdState = null;
        Statement upState = null;
        Statement updateState = null;
        Statement stateInsert = null;

        ResultSet reCount = null;
        ResultSet result = null;
        ResultSet rs = null;
        ResultSet rskey = null;

        PrintWriter writer = null;

        //统计总处理条数
        int count = 0;
        //新增客户数
        int custCount = 0;

        try{
            //建立连接
            con = dataSource.getConnection();

            //开启事务
            con.setAutoCommit(false);

            workOrderState = con.createStatement();
            custState = con.createStatement();
            custIdState = con.createStatement();
            upState = con.createStatement();
            updateState = con.createStatement();
            stateInsert = con.createStatement();



            reCount = workOrderState.executeQuery("SELECT count(*) FROM sale_workorder WHERE LENGTH(customer_id) = 36 OR customer_id IS NULL");
            while(reCount.next()) {
                System.out.println("需要处理的旧工单总数" + reCount.getLong("count(*)"));
            }
            // 总用户数 1846 //总工单数 8813

            //查询有问题需要处理的 工单
            result = workOrderState.executeQuery("SELECT id,customer_id,customer_name,customer_address,customer_mobile,liable_company FROM sale_workorder WHERE LENGTH(customer_id) = 36 OR customer_id IS NULL");


            while(result.next()){
                Long id = result.getLong("id");
                String customerId = result.getString("customer_id");
                String customerName = result.getString("customer_name");
                String customerMobile = result.getString("customer_mobile");
                String customerAddress = result.getString("customer_address");
                Long companyId = result.getLong("liable_company");

                //通过客户名称,电话,所属公司查询客户 ,customer_name,customer_mobile,customer_address,company
                rs = custState.executeQuery("SELECT id FROM sale_customer" +
                        "      WHERE  customer_name='" + customerName +
                        "'        AND customer_mobile='"+ customerMobile +
                        "'        AND company="+ companyId );

                if(rs.next()) {
                    //查到了
                    Long cId = rs.getLong("id");
                    //String cName = rs.getString("customer_name");
                    //String cMobile = rs.getString("customer_mobile");
                    //String cAddress = rs.getString("customer_address");
                    //Long cCompanyId = rs.getLong("company");

                    //有该客户 就更新id
                    upState.executeUpdate("UPDATE sale_workorder SET customer_id = '"+ cId +"' WHERE id = " + id);
                }else{
                    //没查到
                    stateInsert.executeUpdate("insert into sale_customer (customer_name,customer_mobile,customer_address,company) VALUES ('"+ customerName +"','"
                            + customerMobile + "','" + customerAddress + "'," + companyId +")");

                    rskey = custIdState.executeQuery("select id from sale_customer where customer_name='" + customerName +"' AND customer_mobile='" + customerMobile + "'");
                    if (rskey.next()) {
                        //插入返回的主键

                        Long newCustId = rskey.getLong("id");
                        System.out.println("数据主键：" + newCustId);
                        //给当前工单的客户id更新
                        updateState.executeUpdate("UPDATE sale_workorder SET customer_id = '"+ newCustId +"' WHERE id = " + id);
                    }

                    custCount++;
                }

                //处理了一条
                count++;

            }

            writer = response.getWriter();

            System.out.println("总共处理了 " + count + "条工单数据");
            System.out.println("新增客户 " + custCount + "条");
            System.out.println("新增后 总客户数: " + custCount + 1846);

            con.commit();
            con.setAutoCommit(true);
            writer.write("总共处理了 " + count + "条工单数据新增客户 " + custCount + "条");
        }catch(Exception e){
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            writer.write("error!!!");
        }finally{
            try{
                //关闭连接
                workOrderState.close(); ;
                custState.close();
                custIdState.close();
                upState.close();
                updateState.close();
                stateInsert.close();

                reCount.close();
                result.close();
                rs.close();
                rskey.close();

                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}
