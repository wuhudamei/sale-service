--日期时间
--修改人：Andy
--描  述： 
-- 1:用户表新增部门码字段:dep_code
-- 2:用户表新增集团码字段:org_code
-- 3:将account字段参数更新至org_code
--执行状态
ALTER TABLE sale_user ADD COLUMN  dep_code VARCHAR(10) COMMENT '3位部门码,与集团码联合组成8位员工号码(频繁变动)' after account;
ALTER TABLE sale_user ADD COLUMN  org_code VARCHAR(15) COMMENT '5位集团码,以便以后扩展给予8位长度' after dep_code;
update sale_user set org_code=account; 

--日期时间 2017/7/25
--修改人：Paul
--描  述： 
-- 1:工单表 修改重要程度1 类型为int
-- 2:工单表 品牌 类型为int
--执行状态 已执行
 ALTER TABLE sale_workorder MODIFY COLUMN important_degree1 INT(11);
 ALTER TABLE sale_workorder MODIFY COLUMN brand INT(11);
 ALTER TABLE sale_workorder MODIFY COLUMN source INT(11);


--日期时间 2017/8/30
--修改人：Paul
--描  述：
-- 1:工单表 新增责任供应商id 类型为int(11)
--执行状态 已执行
ALTER TABLE sale_workorder ADD COLUMN liable_supplier INT(11) COMMENT '责任供应商id';

--日期时间 2017/8/31
--修改人：Paul
--描  述：
-- 1:sale_user 新增消息提醒标记 类型为TINYINT(1)
--执行状态 已执行
ALTER TABLE sale_user ADD COLUMN remind_flag TINYINT(1) DEFAULT 1 COMMENT '是否进行微信消息提醒 1提醒, 0不提醒 默认1';

--日期时间 2017/9/1
--修改人：Ryze
--描  述：
-- 1:工单表 新增延期申请标记 类型为VARCHAR(1)
--执行状态 已执行
ALTER TABLE sale_workorder ADD COLUMN treament_time_update  VARCHAR(1) COMMENT '延期申请标记 null 未申请 0 申请 1通过 2驳回';

--日期时间 2017/9/1
--修改人：Ryze
--描  述：
-- 1:员工表 新增部门负责人标记 类型为VARCHAR(1)
--执行状态 已执行
ALTER TABLE sale_user ADD COLUMN department_head   VARCHAR(1) COMMENT '部门负责人标记 0/null 不是  1 是';

--日期时间 2017/9/1
--修改人：Paul
--描  述：
-- 1:客户 不回访标记 类型为 TINYINT(1)
--执行状态 已执行
ALTER TABLE sale_customer ADD COLUMN black_flag TINYINT(1)  DEFAULT 0 COMMENT '黑名单标记 0:否 1:是 ';

--日期时间 2017/9/26
--修改人：Paul
--描  述：
-- 1:用户 兼职字段 类型为 varchar(20)
--执行状态 已执行
alter table sale_user add part_time_job varchar(500) DEFAULT NULL COMMENT'兼职,多部门使用&拼接';

--日期时间 2017/10/31
--修改人：Paul
--描  述：
-- 1:工单表 新增 合同实际开工时间/实际竣工时间
-- 2: 新增分配意见
--执行状态 已执行
ALTER TABLE sale_workorder ADD actual_start_time VARCHAR (20) DEFAULT NULL COMMENT'实际开始时间-- 来自合同' AFTER contract_complete_time ;
ALTER TABLE sale_workorder ADD actual_completion_time VARCHAR (20) DEFAULT NULL COMMENT'实际完成时间-- 来自合同' AFTER actual_start_Time;
ALTER TABLE sale_workorder ADD suggestion VARCHAR (500) DEFAULT NULL COMMENT'分配意见' ;

--日期时间 2017/12/19
--修改人：Paul
--描  述：
-- 1:工单表 修改 图片字段大小
--执行状态 已执行
alter table sale_workorder  modify column photo varchar(3000) COMMENT '图片路径';