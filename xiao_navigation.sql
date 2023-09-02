/*
 Navicat Premium Data Transfer

 Source Server         : 本地8.0 - 3307
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3307
 Source Schema         : xiao_navigation

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 02/09/2023 18:55:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户名称',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '登录IP',
  `login_ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP归属地',
  `login_ip_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP城市信息（省市区）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7096618835255566337 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '所属上级',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '类型(0目录 1菜单 2按钮)',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  `sort_value` int NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态(0正常 1禁用)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (2, 0, '系统管理', 0, 'navigation', 'Layout', NULL, 'el-icon-s-tools', 0, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:22', 0);
INSERT INTO `menu` VALUES (3, 2, '用户管理', 1, 'user', 'navigation/user/list', '', 'el-icon-s-custom', 1, 0, '2023-02-20 13:14:20', '2023-08-12 15:48:04', 0);
INSERT INTO `menu` VALUES (4, 2, '角色管理', 1, 'role', 'navigation/role/list', '', 'el-icon-user-solid', 2, 0, '2023-02-20 13:14:20', '2023-08-12 15:48:16', 0);
INSERT INTO `menu` VALUES (5, 2, '菜单管理', 1, 'menu', 'navigation/menu/list', '', 'el-icon-s-unfold', 3, 0, '2023-02-20 13:14:20', '2023-08-12 15:48:24', 0);
INSERT INTO `menu` VALUES (6, 3, '查看', 2, NULL, NULL, 'user:list', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (7, 3, '添加', 2, NULL, NULL, 'user:add', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (8, 3, '修改', 2, NULL, NULL, 'user:update', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (9, 3, '删除', 2, NULL, NULL, 'user:remove', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (10, 4, '查看', 2, NULL, NULL, 'role:list', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (11, 4, '添加', 2, NULL, NULL, 'role:add', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (12, 4, '修改', 2, NULL, NULL, 'role:update', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (13, 4, '删除', 2, NULL, NULL, 'role:remove', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (14, 5, '查看', 2, NULL, NULL, 'menu:list', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (15, 5, '添加', 2, NULL, NULL, 'menu:add', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (16, 5, '修改', 2, NULL, NULL, 'menu:update', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (17, 5, '删除', 2, NULL, NULL, 'menu:remove', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (18, 3, '分配角色', 2, NULL, NULL, 'user:assignRole', NULL, 1, 0, '2023-02-20 13:14:20', '2023-04-29 10:31:26', 0);
INSERT INTO `menu` VALUES (19, 4, '分配权限', 2, 'assignAuth', 'navigation/role/assignAuth', 'role:assignAuth', NULL, 1, 0, '2023-02-20 13:14:20', '2023-08-12 15:48:34', 0);
INSERT INTO `menu` VALUES (20, 0, '日志管理', 0, 'navigation', 'Layout', '', 'el-icon-tickets', 1, 0, '2023-04-29 18:27:41', '2023-08-29 13:09:38', 0);
INSERT INTO `menu` VALUES (21, 20, '登录日志管理', 1, 'loginLog', 'navigation/loginLog/list', '', 'el-icon-document-remove', 1, 0, '2023-04-29 18:50:36', '2023-08-12 15:48:42', 0);
INSERT INTO `menu` VALUES (22, 20, '操作日志管理', 1, 'operLog', 'navigation/operLog/list', '', 'el-icon-document-remove', 1, 0, '2023-04-29 18:51:39', '2023-08-12 15:48:50', 0);
INSERT INTO `menu` VALUES (23, 21, '查看', 2, '', '', 'loginLog.list', '', 1, 0, '2023-04-29 18:53:04', '2023-04-29 18:53:04', 0);
INSERT INTO `menu` VALUES (24, 21, '删除', 2, '', '', 'loginLog.remove', '', 1, 0, '2023-04-29 18:53:48', '2023-04-29 18:53:48', 0);
INSERT INTO `menu` VALUES (25, 21, '批量删除', 2, '', '', 'loginLog.batchRemove', '', 1, 0, '2023-04-29 18:54:12', '2023-04-29 18:54:12', 0);
INSERT INTO `menu` VALUES (26, 22, '查看', 2, '', '', 'operLog.list', '', 1, 0, '2023-04-29 18:54:53', '2023-04-29 18:54:53', 0);
INSERT INTO `menu` VALUES (27, 22, '删除', 2, '', '', 'operLog.remove', '', 1, 0, '2023-04-29 18:55:16', '2023-04-29 18:55:16', 0);
INSERT INTO `menu` VALUES (28, 22, '操作日志批量删除', 2, '', '', 'operLog.batchRemove', '', 1, 0, '2023-04-29 18:57:49', '2023-04-29 18:57:49', 0);
INSERT INTO `menu` VALUES (41, 4, '分配部门', 2, 'assignDept', 'system/sysRole/assignDept', 'dept:assignAuth', '', 1, 0, '2023-05-02 23:03:54', '2023-05-02 23:03:54', 0);
INSERT INTO `menu` VALUES (42, 0, '前台管理', 0, 'navigation', 'Layout', '', 'el-icon-tickets', 1, 0, '2023-08-12 15:59:38', '2023-08-12 15:59:38', 0);
INSERT INTO `menu` VALUES (43, 42, '类别管理', 1, 'sort', 'navigation/sort/list', '', 'el-icon-s-operation', 1, 0, '2023-08-12 16:00:43', '2023-08-12 16:00:43', 0);
INSERT INTO `menu` VALUES (44, 43, '查看', 2, '', '', 'sort:list', '', 1, 0, '2023-08-12 16:02:40', '2023-08-12 16:02:40', 0);
INSERT INTO `menu` VALUES (45, 43, '添加', 2, '', '', 'sort:add', '', 1, 0, '2023-08-12 16:03:07', '2023-08-12 16:03:07', 0);
INSERT INTO `menu` VALUES (46, 43, '修改', 2, '', '', 'sort:update', '', 1, 0, '2023-08-12 16:03:23', '2023-08-12 16:03:23', 0);
INSERT INTO `menu` VALUES (47, 43, '删除', 2, '', '', 'sort:remove', '', 1, 0, '2023-08-12 16:03:44', '2023-08-12 16:03:44', 0);
INSERT INTO `menu` VALUES (48, 43, '更改状态', 2, '', '', 'sort:status', '', 1, 0, '2023-08-12 16:04:11', '2023-08-12 16:04:11', 0);
INSERT INTO `menu` VALUES (49, 43, '类别批量删除', 2, '', '', 'sort:batchRemove', '', 1, 0, '2023-08-12 23:12:26', '2023-08-12 23:12:26', 0);
INSERT INTO `menu` VALUES (50, 42, '标签管理', 1, 'tag', 'navigation/tag/list', '', 'el-icon-s-operation', 1, 0, '2023-08-16 17:21:48', '2023-08-16 17:21:48', 0);
INSERT INTO `menu` VALUES (51, 50, '查看', 2, '', '', 'tag:list', '', 1, 0, '2023-08-16 17:46:47', '2023-08-16 17:46:47', 0);
INSERT INTO `menu` VALUES (52, 50, '添加', 2, '', '', 'tag:add', '', 1, 0, '2023-08-16 17:47:06', '2023-08-16 17:47:06', 0);
INSERT INTO `menu` VALUES (53, 50, '修改', 2, '', '', 'tag:update', '', 1, 0, '2023-08-16 17:47:20', '2023-08-16 17:47:20', 0);
INSERT INTO `menu` VALUES (54, 50, '删除', 2, '', '', 'tag:remove', '', 1, 0, '2023-08-16 17:47:43', '2023-08-16 17:47:43', 0);
INSERT INTO `menu` VALUES (55, 50, '更改标签状态', 2, '', '', 'tag:status', '', 1, 0, '2023-08-16 17:48:17', '2023-08-16 17:48:17', 0);
INSERT INTO `menu` VALUES (56, 50, '标签批量删除', 2, '', '', 'tag:batchRemove', '', 1, 0, '2023-08-16 17:48:49', '2023-08-16 17:48:49', 0);
INSERT INTO `menu` VALUES (57, 50, '分配类别', 2, 'assignSort', 'navigation/role/assignSort', 'tag:assignSort', '', 1, 0, '2023-08-16 19:21:34', '2023-08-16 19:21:34', 0);
INSERT INTO `menu` VALUES (62, 0, '个人中心', 0, 'navigation', 'Layout', '', 'el-icon-user-solid', 1, 0, '2023-09-02 15:06:39', '2023-09-02 15:06:39', 0);
INSERT INTO `menu` VALUES (63, 62, '个人信息', 1, 'personal', 'navigation/personal/list', '', 'el-icon-s-custom', 1, 0, '2023-09-02 15:08:56', '2023-09-02 15:08:56', 0);
INSERT INTO `menu` VALUES (64, 63, '修改信息', 2, '', '', 'user:update', '', 1, 0, '2023-09-02 15:11:06', '2023-09-02 15:11:06', 0);

-- ----------------------------
-- Table structure for oper_log
-- ----------------------------
DROP TABLE IF EXISTS `oper_log`;
CREATE TABLE `oper_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_user_id` bigint NULL DEFAULT NULL COMMENT '操作人员ID',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '主机IP',
  `oper_ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP归属地',
  `oper_ip_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP城市信息（省市区）',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7096625308526256129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员', 'SYSTEM', '系统管理员', '2023-01-20 18:19:28', '2023-01-21 09:20:20', 0);
INSERT INTO `role` VALUES (2, '香香测试', 'xiang', '测试员', '2023-03-21 17:51:53', '2023-03-21 17:51:53', 0);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL DEFAULT 0,
  `menu_id` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_menu_id`(`menu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (14, 1, 2);
INSERT INTO `role_menu` VALUES (15, 1, 3);
INSERT INTO `role_menu` VALUES (16, 1, 6);
INSERT INTO `role_menu` VALUES (17, 1, 7);
INSERT INTO `role_menu` VALUES (18, 1, 8);
INSERT INTO `role_menu` VALUES (19, 1, 9);
INSERT INTO `role_menu` VALUES (20, 1, 18);
INSERT INTO `role_menu` VALUES (21, 1, 4);
INSERT INTO `role_menu` VALUES (22, 1, 10);
INSERT INTO `role_menu` VALUES (23, 1, 11);
INSERT INTO `role_menu` VALUES (24, 1, 12);
INSERT INTO `role_menu` VALUES (25, 1, 13);
INSERT INTO `role_menu` VALUES (26, 1, 19);
INSERT INTO `role_menu` VALUES (27, 1, 41);
INSERT INTO `role_menu` VALUES (28, 1, 5);
INSERT INTO `role_menu` VALUES (29, 1, 14);
INSERT INTO `role_menu` VALUES (30, 1, 15);
INSERT INTO `role_menu` VALUES (31, 1, 16);
INSERT INTO `role_menu` VALUES (32, 1, 17);
INSERT INTO `role_menu` VALUES (33, 1, 42);
INSERT INTO `role_menu` VALUES (34, 1, 43);
INSERT INTO `role_menu` VALUES (35, 1, 44);
INSERT INTO `role_menu` VALUES (36, 1, 45);
INSERT INTO `role_menu` VALUES (37, 1, 46);
INSERT INTO `role_menu` VALUES (38, 1, 47);
INSERT INTO `role_menu` VALUES (39, 1, 48);
INSERT INTO `role_menu` VALUES (40, 1, 49);
INSERT INTO `role_menu` VALUES (41, 1, 50);
INSERT INTO `role_menu` VALUES (42, 1, 51);
INSERT INTO `role_menu` VALUES (43, 1, 52);
INSERT INTO `role_menu` VALUES (44, 1, 53);
INSERT INTO `role_menu` VALUES (45, 1, 54);
INSERT INTO `role_menu` VALUES (46, 1, 55);
INSERT INTO `role_menu` VALUES (47, 1, 56);
INSERT INTO `role_menu` VALUES (48, 1, 57);
INSERT INTO `role_menu` VALUES (49, 1, 60);
INSERT INTO `role_menu` VALUES (121, 2, 2);
INSERT INTO `role_menu` VALUES (122, 2, 3);
INSERT INTO `role_menu` VALUES (123, 2, 6);
INSERT INTO `role_menu` VALUES (124, 2, 4);
INSERT INTO `role_menu` VALUES (125, 2, 10);
INSERT INTO `role_menu` VALUES (126, 2, 5);
INSERT INTO `role_menu` VALUES (127, 2, 14);
INSERT INTO `role_menu` VALUES (128, 2, 20);
INSERT INTO `role_menu` VALUES (129, 2, 21);
INSERT INTO `role_menu` VALUES (130, 2, 23);
INSERT INTO `role_menu` VALUES (131, 2, 22);
INSERT INTO `role_menu` VALUES (132, 2, 26);
INSERT INTO `role_menu` VALUES (133, 2, 42);
INSERT INTO `role_menu` VALUES (134, 2, 43);
INSERT INTO `role_menu` VALUES (135, 2, 44);
INSERT INTO `role_menu` VALUES (136, 2, 45);
INSERT INTO `role_menu` VALUES (137, 2, 46);
INSERT INTO `role_menu` VALUES (138, 2, 47);
INSERT INTO `role_menu` VALUES (139, 2, 48);
INSERT INTO `role_menu` VALUES (140, 2, 49);
INSERT INTO `role_menu` VALUES (141, 2, 50);
INSERT INTO `role_menu` VALUES (142, 2, 51);
INSERT INTO `role_menu` VALUES (143, 2, 52);
INSERT INTO `role_menu` VALUES (144, 2, 53);
INSERT INTO `role_menu` VALUES (145, 2, 54);
INSERT INTO `role_menu` VALUES (146, 2, 55);
INSERT INTO `role_menu` VALUES (147, 2, 56);
INSERT INTO `role_menu` VALUES (148, 2, 57);

-- ----------------------------
-- Table structure for sort
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort`  (
  `sort_id` bigint NOT NULL COMMENT '类别ID',
  `user_id` bigint NOT NULL COMMENT '所属用户ID',
  `sort_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类别名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类别描述',
  `ord` int NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0正常 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`sort_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sort
-- ----------------------------
INSERT INTO `sort` VALUES (7073042872142446592, 1, '后端开发', '后端开发', 1, 0, '2023-06-29 21:51:33', '2023-08-31 00:06:54');
INSERT INTO `sort` VALUES (7083888084491825152, 1, '常用', '经常使用', 1, 0, '2023-07-29 15:18:11', '2023-08-31 00:06:54');
INSERT INTO `sort` VALUES (7089033678948458496, 1, 'coisni专属', 'coisni专属', 0, 0, '2023-08-12 20:04:57', '2023-08-31 00:06:54');
INSERT INTO `sort` VALUES (7095816687802392576, 2, '香香专属', '香香专属', 1, 0, '2023-08-31 13:18:12', '2023-08-31 13:18:11');
INSERT INTO `sort` VALUES (7095913066981761024, 2, '测试', '测试', 1, 0, '2023-08-31 19:41:10', '2023-08-31 19:41:10');
INSERT INTO `sort` VALUES (7095917762232483840, 2, '开发', '开发', 1, 0, '2023-08-31 19:59:50', '2023-08-31 19:59:49');

-- ----------------------------
-- Table structure for sort_tag
-- ----------------------------
DROP TABLE IF EXISTS `sort_tag`;
CREATE TABLE `sort_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `sort_id` bigint NOT NULL COMMENT '类别ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7095948139210780673 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sort_tag
-- ----------------------------
INSERT INTO `sort_tag` VALUES (7094201011354595328, 1, 7089033678948458496, 7094197805702574080);
INSERT INTO `sort_tag` VALUES (7094211411609513984, 1, 7089033678948458496, 7094210248034086912);
INSERT INTO `sort_tag` VALUES (7094211447860883456, 1, 7089033678948458496, 7094210697793499136);
INSERT INTO `sort_tag` VALUES (7094211460561235968, 1, 7089033678948458496, 7094211370912182272);
INSERT INTO `sort_tag` VALUES (7094211931443163136, 1, 7083888084491825152, 7094211911897706496);
INSERT INTO `sort_tag` VALUES (7094212694928125952, 1, 7083888084491825152, 7094212345089617920);
INSERT INTO `sort_tag` VALUES (7095877137424453632, 1, 7073042872142446592, 7095877017899372544);
INSERT INTO `sort_tag` VALUES (7095917793085784064, 2, 7095816687802392576, 7094213514079891456);
INSERT INTO `sort_tag` VALUES (7095917794297937920, 2, 7095913066981761024, 7094213514079891456);
INSERT INTO `sort_tag` VALUES (7095917795514286080, 2, 7095917762232483840, 7094213514079891456);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  `user_id` bigint NOT NULL COMMENT '所属用户',
  `tag_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签名称',
  `tag_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签图标',
  `tag_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签描述',
  `ord` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0正常 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (7094197805702574080, 1, 'coisini', 'group1/M00/00/00/wKgUgGTocnWAYUIAAAEIPiQdEUk394.ico', 'https://coisini.cn', 'coisini寓意：怦然心动', 0, 0, '2023-08-27 02:05:20', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094210248034086912, 1, 'coisini-ge', 'group1/M00/00/00/wKgUgGTofX6AYyPoAAAOoUAIiJI941.png', 'https://gitee.com/xiangshaw/', 'gitee', 1, 0, '2023-08-27 02:54:47', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094210697793499136, 1, 'coisini-gh', 'group1/M00/00/00/wKgUgGTofcaAciUHAAAUpLUQwTc545.png', 'https://github.com/xiangshaw', 'github', 1, 0, '2023-08-27 02:56:34', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094211370912182272, 1, 'coisini-c', 'group1/M00/00/00/wKgUgGTofo6AAoCQAAAS1VYCxcE194.png', 'https://blog.csdn.net/qq_44870331', 'CSDN', 1, 0, '2023-08-27 02:59:15', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094211911897706496, 1, 'ChatGPT', 'group1/M00/00/00/wKgUgGTofyyAbPFbAAAUAIcXlzM069.png', 'https://chat.openai.com', 'AI', 1, 0, '2023-08-27 03:01:24', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094212345089617920, 1, 'google-Ai', 'group1/M00/00/00/wKgUgGTof5yALsWsAAAQ_tdARqQ831.png', 'https://bard.google.com', 'google AI', 1, 0, '2023-08-27 03:03:07', '2023-08-31 00:06:00');
INSERT INTO `tag` VALUES (7094213514079891456, 2, 'MyBatis-Plus', 'group1/M00/00/00/wKgUgGTogLKAGLd8AADBQ-fMaxk496.png', 'https://baomidou.com/', 'MyBatis-Plus', 1, 0, '2023-08-27 03:07:46', '2023-08-31 12:35:20');
INSERT INTO `tag` VALUES (7095877017899372544, 1, 'MyBatis-Plus', 'group1/M00/00/00/wKgUgGTwOIGACQp1AADBQ-fMaxk438.png', 'https://baomidou.com/', 'MyBatis-Plus', 1, 0, '2023-08-31 17:17:56', '2023-08-31 17:17:55');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7095499853437947905 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$uIcjawZNHaiUypPJ8yOqmOCgOmAOHwzimnJW/8h4Tvd7SEl8bhDbS', 'admin', '15099909888', 'group1/M00/00/00/wKgUgGTy8fyAIkJzAAwPzrIsWmg875.gif', 'admin', 0, '2021-05-31 18:08:43', '2023-09-02 18:25:38', '2023-09-02 18:51:21', 0);
INSERT INTO `user` VALUES (2, 'xiang', '$2a$10$uIcjawZNHaiUypPJ8yOqmOCgOmAOHwzimnJW/8h4Tvd7SEl8bhDbS', '香香', '123456', 'group1/M00/00/00/wKgUgGToZX-ATQ9sAAEvMf6EO-A341.jpg', NULL, 0, '2023-03-21 17:50:46', NULL, '2023-08-27 00:28:27', 0);
INSERT INTO `user` VALUES (7095499853437947904, '1', '$2a$10$MHoR6HjfphZn3OwzsooSku4ko72kbLFcobfX10SCso0SI6vHThIeC', '用户7095499853437947904', '19215656524', NULL, NULL, 0, '2023-08-30 16:19:12', NULL, '2023-08-30 16:19:12', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint NOT NULL DEFAULT 0 COMMENT '角色id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1696515511304601602 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
