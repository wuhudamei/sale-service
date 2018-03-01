DROP TABLE IF EXISTS `sale_permission`;
CREATE TABLE `sale_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `module` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `seq` int(11) NOT NULL COMMENT '排序字段',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for sale_role
-- ----------------------------
DROP TABLE IF EXISTS `sale_role`;
CREATE TABLE `sale_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(80) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for sale_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sale_role_permission`;
CREATE TABLE `sale_role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

-- ----------------------------
-- Table structure for sale_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sale_user_role`;
CREATE TABLE `sale_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色关联表';

-- ----------------------------
-- Table structure for sale_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sale_dictionary`;
CREATE TABLE `sale_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `parent_code` int(11) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '1:重要类别1\r\n            2:重要类别2\r\n            3:责任类别1\r\n            4:责任类别2',
  `sort` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_organization
-- ----------------------------
DROP TABLE IF EXISTS `sale_organization`;
CREATE TABLE `sale_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(20) DEFAULT NULL COMMENT '如:\r\n            北京 bj\r\n            上海:sh\r\n            广州:gz 等',
  `org_name` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `family_code` varchar(100) DEFAULT NULL COMMENT '例: 1-2-3 ',
  `status` int(1) DEFAULT NULL COMMENT '0:无效 \r\n            1:正常',
  `create_date` varchar(20) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_user
-- ----------------------------
DROP TABLE IF EXISTS `sale_user`;
CREATE TABLE `sale_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `department` int(11) DEFAULT NULL,
  `company` int(11) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL COMMENT '0:无效\r\n            1:正常',
  `create_date` varchar(20) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `sale_user_bind`;
CREATE TABLE `sale_user_bind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '本地用户id',
  `oid` varchar(40) NOT NULL COMMENT '其他平台的id',
  `platform` varchar(20) NOT NULL COMMENT '平台',
  `bind_time` datetime NOT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_user_plan
-- ----------------------------
DROP TABLE IF EXISTS `sale_user_plan`;
CREATE TABLE `sale_user_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(500) DEFAULT NULL COMMENT '计划内容',
  `start_time` varchar(20) NOT NULL COMMENT '开始日期',
  `end_time` varchar(20) NOT NULL COMMENT '结束日期',
  `status` varchar(20) DEFAULT NULL COMMENT '计划状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户计划表';

-- ----------------------------
-- Table structure for sale_workorder
-- ----------------------------
DROP TABLE IF EXISTS `sale_workorder`;
CREATE TABLE `sale_workorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workorder_code` varchar(50) DEFAULT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  `customer_id` varchar(50) DEFAULT NULL COMMENT '客户ID',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户姓名',
  `customer_mobile` varchar(20) DEFAULT NULL COMMENT '客户电话',
  `customer_address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `contract_start_time` varchar(30) DEFAULT NULL COMMENT '合同开始时间',
  `contract_complete_time` varchar(30) DEFAULT NULL COMMENT '合同结束时间',
  `stylist_name` varchar(50) DEFAULT NULL COMMENT '设计师名称',
  `stylist_mobile` varchar(20) DEFAULT NULL COMMENT '设计师电话',
  `contractor_name` varchar(50) DEFAULT NULL COMMENT '项目经理名称',
  `contractor_mobile` varchar(20) DEFAULT NULL COMMENT '项目经理电话',
  `supervisor_name` varchar(50) DEFAULT NULL COMMENT '监理人名称',
  `supervisor_mobile` varchar(20) DEFAULT NULL COMMENT '监理人电话',
  `important_degree1` int(11) DEFAULT NULL,
  `important_degree2` int(11) DEFAULT NULL,
  `reception_person` int(11) DEFAULT NULL,
  `reception_time` varchar(20) DEFAULT NULL,
  `src_department` int(11) DEFAULT NULL,
  `src_company` int(11) DEFAULT NULL,
  `problem` text,
  `liable_department` int(11) DEFAULT NULL,
  `liable_person` int(11) DEFAULT NULL,
  `liable_type1` int(11) DEFAULT NULL,
  `liable_type2` int(11) DEFAULT NULL,
  `treament_plan` varchar(3000) DEFAULT NULL,
  `treament_time` varchar(20) DEFAULT NULL,
  `feedback_time` varchar(20) DEFAULT NULL,
  `visit_result` varchar(2000) DEFAULT NULL,
  `statu_flag` int(1) DEFAULT NULL COMMENT '0:无效1:正常',
  `order_status` varchar(20) DEFAULT NULL COMMENT '待处理,待回复,待回访,回访未执行,已解决',
  `copy_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_latvian_ci DEFAULT NULL COMMENT '是否复制1-已复制 为空表示未复制,只针对order_status为回访未执行状态',
  `create_user` int(11) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_workorder_rmk
-- ----------------------------
DROP TABLE IF EXISTS `sale_workorder_rmk`;
CREATE TABLE `sale_workorder_rmk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work_order_id` int(11) DEFAULT NULL,
  `operation_date` varchar(20) DEFAULT NULL,
  `operation_user` int(11) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `operation_type` varchar(20) DEFAULT NULL COMMENT '处理\r\n            回复\r\n            回访\r\n            结案\r\n            备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sale_pro_brand
--品牌事项分类表
-- ----------------------------
DROP TABLE IF EXISTS `sale_pro_brand`;
CREATE TABLE `sale_pro_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type1_id` int(11) DEFAULT NULL COMMENT '事项分类id',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--日期时间 2017/9/1
--修改人：
--描  述：
-- 1: 新增审批表
--执行状态 已执行
DROP TABLE IF EXISTS `sale_treament_approval`;
CREATE TABLE `sale_treament_approval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `old_time` datetime DEFAULT NULL COMMENT '原先预计完成时间',
  `new_time` datetime DEFAULT NULL COMMENT '申请预计完成时间',
  `workorder_id` int(11) DEFAULT NULL COMMENT '工单id',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '修改原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--日期时间 2017/9/1
--修改人：
--描  述：
-- 1: 新增审批结果表
--执行状态 已执行
DROP TABLE IF EXISTS `sale_treament_result`;
CREATE TABLE `sale_treament_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `approval_result` varchar(255) DEFAULT NULL COMMENT '审批结果  1 通过 2 驳回',
  `remarks` varchar(500) DEFAULT NULL COMMENT '审批结果 说明',
  `approval_id` int(11) DEFAULT NULL COMMENT '对应的申请记录',
  `workorder_id` int(11) DEFAULT NULL COMMENT '工单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--日期时间 2017/9/1
--修改人：胡期波
--描  述： 处理时限表
--执行状态 已执行
CREATE TABLE `sale_time_limit` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` int(4) NOT NULL COMMENT '部门',
  `department_id` int(4) NOT NULL COMMENT '组织',
  `question_category_id` int(4) NOT NULL COMMENT '类别',
  `question_type_id` int(4) NOT NULL COMMENT '类型',
  `duration` int(4) NOT NULL COMMENT '最大时长',
  `create_user` int(10) NOT NULL,
  `crate_date` datetime DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
 COMMENT='处理时限表';

--日期时间 2017/9/1
--修改人：李福田
--描  述： 工单推送失败表
--执行状态 已执行
CREATE TABLE `sale_workorder_push_fail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_order_id` int(11) DEFAULT NULL COMMENT '工单id',
  `work_order_code` varchar(255) DEFAULT NULL COMMENT '工单编号',
  `remarks` varchar(255) DEFAULT NULL COMMENT '推送失败原因',
  `push_date` datetime DEFAULT NULL COMMENT '最新推送失败时间',
  `push_result` varchar(255) DEFAULT '0' COMMENT ' 推送结果 0 失败 1 成功',
  `syn_date` datetime DEFAULT NULL COMMENT '失败 的同步时间 ',
  `push_number` int(11) DEFAULT '0' COMMENT '推送次数',
  `push_type` enum('TURNDOWN','PUSH','') DEFAULT NULL COMMENT ' PUSH("推送失败"),TURNDOWN("在驳回失败");',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
