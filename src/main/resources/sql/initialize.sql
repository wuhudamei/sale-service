-- 初始化超级管理员
INSERT INTO sale_user (ID,account,NAME,PASSWORD,salt,phone,email,sex,department,company,STATUS) VALUES (1,'admin','超级管理员','9502be17e6ab9e09c01b2a9a6afaffe7d0c672fc','144c2b5765f6e248','','','MALE',3,2,'NORMAL');

-- 初始化超级管理员角色
Insert into sale_role (ID,NAME,DESCRIPTION) values (1,'超级管理员','超级管理员');

-- 超级管理员账户和角色关联
Insert into sale_user_role (USER_ID,ROLE_ID) values (1,1);


delete from sale_permission;
-- 所有权限表
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(1,'所有权限','embed',0,'*');

Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(2,'用户管理-查询','用户管理',1,'admin:user:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(3,'用户管理-新增','用户管理',1,'admin:user:add');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(4,'用户管理-编辑','用户管理',1,'admin:user:edit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(5,'用户管理-重置密码','用户管理',1,'admin:user:resetpwd');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (6,'用户管理-设置角色','用户管理',1,'admin:user:setrole');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (7,'用户管理-启用禁用','用户管理',1,'admin:user:opt');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (8,'用户管理-删除','用户管理',1,'admin:user:del');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (9,'角色管理-查询','角色管理',2,'admin:role:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (10,'角色管理-添加','角色管理',2,'admin:role:add');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (11,'角色管理-编辑','角色管理',2,'admin:role:edit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (12,'角色管理-授权','角色管理',2,'admin:role:autho');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (13,'角色管理-删除','角色管理',2,'admin:role:delete');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (14,'组织架构-列表','组织架构',3,'organization:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(15,'组织架构-添加','组织架构',3,'organization:add');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(16,'组织架构-编辑','组织架构',3,'organization:edit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(17,'组织架构-删除','组织架构',3,'organization:delete');

Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(18,'数据字典-列表','数据字典',4,'dictionary:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(19,'数据字典-添加','数据字典',4,'dictionary:add');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(20,'数据字典-编辑','数据字典',4,'dictionary:edit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(21,'数据字典-删除','数据字典',4,'dictionary:delete');

Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(22,'工单管理-列表','工单管理',5,'workorder:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(23,'工单管理-发起','工单管理',5,'workorder:add');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(24,'工单管理-处理','工单管理',5,'workorder:deal');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(25,'工单管理-回复','工单管理',5,'workorder:reply');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(26,'工单管理-回访','工单管理',5,'workorder:visit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(27,'工单管理-复制','工单管理',5,'workorder:copy');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(28,'工单管理-备注','工单管理',5,'workorder:remark');

INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('43', '工单管理-导出', '工单管理', '5', 'workorder:export');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('37', '工单管理菜单-待处理', '工单管理', '7', 'workorder:menu:pending');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('38', '工单管理菜单-已处理', '工单管理', '7', 'workorder:menu:completed');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('39', '工单管理菜单-待分配', '工单管理', '7', 'workorder:menu:assign');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('40', '工单管理菜单-待回复', '工单管理', '7', 'workorder:menu:nreply');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('41', '工单管理菜单-待回访', '工单管理', '7', 'workorder:menu:nvisit');
INSERT INTO sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) VALUES ('42', '工单管理菜单-工单库', '工单管理', '7', 'workorder:menu:ordermanage');


Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(29,'报表统计','报表统计',5,'workorder:statistics');

Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(30,'首页列表','首页',6,'index:list');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(31,'待处理','首页',6,'index:pending');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(32,'待回复','首页',6,'index:nreply');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(33,'待回访','首页',6,'index:nvisit');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(34,'回访未执行','首页',6,'index:unexecuted');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(35,'快速发起工单','首页',6,'index:quickOrder');
Insert into sale_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(36,'新建计划','首页',6,'index:createPlan');


-- 为管理员角色设置权限
Insert into sale_role_permission (ROLE_ID,PERMISSION_ID) values (1,1);

-- 售后数据字典新增的数据 
INSERT INTO `sale_dictionary` VALUES ('198', '工程类', '0', '5', '1', null);
INSERT INTO `sale_dictionary` VALUES ('199', '质检类', '0', '5', '2', null);
INSERT INTO `sale_dictionary` VALUES ('200', '设计类', '0', '5', '3', null);
INSERT INTO `sale_dictionary` VALUES ('201', '主材品类', '0', '5', '4', null);
INSERT INTO `sale_dictionary` VALUES ('202', '家具类(门,床,桌子)', '0', '5', '5', null);
INSERT INTO `sale_dictionary` VALUES ('203', '家电类', '0', '5', '6', null);
INSERT INTO `sale_dictionary` VALUES ('205', '投诉1', '0', '7', '1', null);
INSERT INTO `sale_dictionary` VALUES ('206', '投诉2', '0', '7', '2', null);
INSERT INTO `sale_dictionary` VALUES ('207', '投诉3', '0', '7', '3', null);
INSERT INTO `sale_dictionary` VALUES ('208', '投诉>3', '0', '7', '4', null);
INSERT INTO `sale_dictionary` VALUES ('211', '预对外', '0', '7', '5', null);
INSERT INTO `sale_dictionary` VALUES ('212', '已对外', '0', '7', '6', null);
INSERT INTO `sale_dictionary` VALUES ('215', '咨询', '198', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('216', '维修', '198', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('219', '测量', '198', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('220', '下单', '198', '6', '4', null);
INSERT INTO `sale_dictionary` VALUES ('221', '安装', '198', '6', '5', null);
INSERT INTO `sale_dictionary` VALUES ('222', '咨询', '199', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('223', '上门', '199', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('224', '判责', '199', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('225', '咨询', '200', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('226', '设计', '200', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('227', '量房', '200', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('228', '下单', '200', '6', '4', null);
INSERT INTO `sale_dictionary` VALUES ('229', '退换货', '200', '6', '5', null);
INSERT INTO `sale_dictionary` VALUES ('230', '结算', '200', '6', '6', null);
INSERT INTO `sale_dictionary` VALUES ('231', '退款', '200', '6', '7', null);
INSERT INTO `sale_dictionary` VALUES ('232', '咨询', '201', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('233', '下单', '201', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('234', '安装', '201', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('235', '维修', '201', '6', '4', null);
INSERT INTO `sale_dictionary` VALUES ('236', '测量', '201', '6', '5', null);
INSERT INTO `sale_dictionary` VALUES ('237', '退换货', '201', '6', '6', null);
INSERT INTO `sale_dictionary` VALUES ('238', '咨询', '202', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('239', '送货', '202', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('240', '维修', '202', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('241', '退换货', '202', '6', '4', null);
INSERT INTO `sale_dictionary` VALUES ('242', '安装', '202', '6', '5', null);
INSERT INTO `sale_dictionary` VALUES ('243', '咨询', '203', '6', '1', null);
INSERT INTO `sale_dictionary` VALUES ('244', '送货', '203', '6', '2', null);
INSERT INTO `sale_dictionary` VALUES ('245', '维修', '203', '6', '3', null);
INSERT INTO `sale_dictionary` VALUES ('246', '退换货', '203', '6', '4', null);
INSERT INTO `sale_dictionary` VALUES ('247', '安装', '203', '6', '5', null);
INSERT INTO `sale_dictionary` VALUES ('248', '多次来电', '0', '8', '1', null);
INSERT INTO `sale_dictionary` VALUES ('249', '服务态度差', '0', '8', '2', null);
INSERT INTO `sale_dictionary` VALUES ('250', '承诺不兑现', '0', '8', '3', null);
INSERT INTO `sale_dictionary` VALUES ('251', '虚假封账', '0', '8', '4', null);
INSERT INTO `sale_dictionary` VALUES ('252', '服务不规范', '0', '8', '5', null);
INSERT INTO `sale_dictionary` VALUES ('253', '服务乱收费', '0', '8', '6', null);
INSERT INTO `sale_dictionary` VALUES ('254', '服务拖期', '0', '8', '7', null);
INSERT INTO `sale_dictionary` VALUES ('255', '工程延期', '0', '8', '8', null);
INSERT INTO `sale_dictionary` VALUES ('256', '一次服务不到位', '0', '8', '9', null);
INSERT INTO `sale_dictionary` VALUES ('257', '多次安装', '0', '8', '10', null);
INSERT INTO `sale_dictionary` VALUES ('258', '多次返工', '0', '8', '11', null);
INSERT INTO `sale_dictionary` VALUES ('259', '技术差', '0', '8', '12', null);
INSERT INTO `sale_dictionary` VALUES ('260', '多次维修', '0', '8', '13', null);
INSERT INTO `sale_dictionary` VALUES ('261', '产品质量', '0', '8', '14', null);
INSERT INTO `sale_dictionary` VALUES ('262', '服务质量', '0', '8', '15', null);
INSERT INTO `sale_dictionary` VALUES ('263', '质量事故', '0', '8', '16', null);
INSERT INTO `sale_dictionary` VALUES ('264', '预对外投诉', '0', '8', '17', null);
INSERT INTO `sale_dictionary` VALUES ('265', '已对外投诉', '0', '8', '18', null);

-- 售后工单轨迹表  新增列
--投诉原因
alter table sale_workorder_rmk add column complaint_type_id int(11); 

-- 售后工单表  新增列
--投诉原因
alter table sale_workorder add column complaint_type int(11); 
--事项分类
alter table sale_workorder add column question_type1 int(11); 
--问题类型
alter table sale_workorder add column question_type2 int(11); 
--被拒绝时间
alter table sale_workorder add column refusedagain_time datetime; 
--拒绝时间
alter table sale_workorder add column refuse_time datetime; 
--催单次数
alter table sale_workorder add column urge_times int(11); 
--催单是否已读
alter table sale_workorder add column is_read tinyint(11);
--品牌
alter table sale_workorder add column brand int(11);
--分派时间
alter table sale_workorder add column fenpai_date datetime;
--工单来源
alter table sale_workorder add column source varchar(100);
--合同信息
alter table sale_workorder add column order_no varchar(200);

-- 售后组织机构  新增列
--部门类型
alter table sale_organization add column dep_type varchar(100);

--数据字典  新增列 
--是否禁用
alter table sale_dictionary add COLUMN status int;