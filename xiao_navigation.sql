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

 Date: 26/08/2023 02:19:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `menu` VALUES (20, 0, '日志管理', 0, 'navigation', 'Layout', '', 'el-icon-tickets', 1, 1, '2023-04-29 18:27:41', '2023-08-18 00:33:26', 0);
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
INSERT INTO `menu` VALUES (56, 50, '标签批量删除', 2, '', '', 'tag:bathRemove', '', 1, 0, '2023-08-16 17:48:49', '2023-08-16 17:48:49', 0);
INSERT INTO `menu` VALUES (57, 50, '分配类别', 2, 'assignSort', 'navigation/role/assignSort', 'tag:assignSort', '', 1, 0, '2023-08-16 19:21:34', '2023-08-16 19:21:34', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `role_menu` VALUES (81, 2, 2);
INSERT INTO `role_menu` VALUES (82, 2, 3);
INSERT INTO `role_menu` VALUES (83, 2, 6);
INSERT INTO `role_menu` VALUES (84, 2, 7);
INSERT INTO `role_menu` VALUES (85, 2, 8);
INSERT INTO `role_menu` VALUES (86, 2, 9);
INSERT INTO `role_menu` VALUES (87, 2, 42);
INSERT INTO `role_menu` VALUES (88, 2, 50);
INSERT INTO `role_menu` VALUES (89, 2, 51);
INSERT INTO `role_menu` VALUES (90, 2, 52);
INSERT INTO `role_menu` VALUES (91, 2, 53);
INSERT INTO `role_menu` VALUES (92, 2, 54);
INSERT INTO `role_menu` VALUES (93, 2, 56);
INSERT INTO `role_menu` VALUES (94, 2, 57);

-- ----------------------------
-- Table structure for sort
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort`  (
  `sort_id` bigint NOT NULL COMMENT '类别ID',
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
INSERT INTO `sort` VALUES (7073042872142446592, '测试', '测试专用', 1, 0, '2023-06-29 21:51:33', '2023-08-13 01:51:21');
INSERT INTO `sort` VALUES (7083888084491825152, '专用', '测试专用', 1, 0, '2023-07-29 15:18:11', '2023-07-29 15:18:11');
INSERT INTO `sort` VALUES (7089033678948458496, '生活', '测试', 0, 0, '2023-08-12 20:04:57', '2023-08-13 02:03:07');

-- ----------------------------
-- Table structure for sort_tag
-- ----------------------------
DROP TABLE IF EXISTS `sort_tag`;
CREATE TABLE `sort_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sort_id` bigint NOT NULL COMMENT '类别ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7093813884686970881 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sort_tag
-- ----------------------------
INSERT INTO `sort_tag` VALUES (7083889427105312768, 7073042872142446592, 7083585521875746816);
INSERT INTO `sort_tag` VALUES (7083889432750845952, 7083888084491825152, 7083585521875746816);
INSERT INTO `sort_tag` VALUES (7093073963785502720, 7083888084491825152, 7093068647853309952);
INSERT INTO `sort_tag` VALUES (7093813882057142272, 7073042872142446592, 7093786989345910784);
INSERT INTO `sort_tag` VALUES (7093813883504177152, 7083888084491825152, 7093786989345910784);
INSERT INTO `sort_tag` VALUES (7093813884686970880, 7089033678948458496, 7093786989345910784);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` bigint NOT NULL COMMENT '标签ID',
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
INSERT INTO `tag` VALUES (7083585521875746816, '测试1', 'group1/M00/00/00/wKgUgGTl5feAWcWeAAAlvnX7j78295.ico', NULL, '测试专用1', 1, 0, '2023-07-28 19:15:55', '2023-07-29 11:37:35');
INSERT INTO `tag` VALUES (7083596148363034624, '33', 'group1/M00/00/00/wKgUgGTl5NOASsYEAAAlvnX7j78402.ico', NULL, '测试专用', 1, 0, '2023-07-28 19:58:08', '2023-08-23 20:57:47');
INSERT INTO `tag` VALUES (7083830547545452544, '测试', 'group1/M00/00/00/wKgUgGTdxAyAd-_7AALxXtyM8xw057.ico', NULL, '测试专用', 1, 0, '2023-07-29 11:29:33', '2023-08-23 20:51:06');
INSERT INTO `tag` VALUES (7090452233346621440, '122', 'group1/M00/00/00/wKgUgGTdrvSAGuLAAALxXtyM8xw228.ico', 'http://localhost:81/#/navigation/tag', '22', 1, 0, '2023-08-16 18:01:46', '2023-08-23 20:51:09');
INSERT INTO `tag` VALUES (7090906013276098560, '1', 'group1/M00/00/00/wKgUgGTdukyAMvWAAALxXtyM8xw576.ico', '3', '1', 1, 0, '2023-08-18 00:04:56', '2023-08-23 20:51:12');
INSERT INTO `tag` VALUES (7090906660130050048, '2', 'group1/M00/00/00/wKgUgGTdu0iAXRrnAALxXtyM8xw541.ico', '2', '2', 2, 0, '2023-08-18 00:07:30', '2023-08-23 20:51:16');
INSERT INTO `tag` VALUES (7090911063155200000, '111', 'group1/M00/00/00/wKgUgGTdv2KAXvcdAALxXtyM8xw462.ico', '111', '111', 112, 0, '2023-08-18 00:25:00', '2023-08-23 20:51:19');
INSERT INTO `tag` VALUES (7093035019832246272, '测试111', 'group1/M00/00/00/wKgUgGTl566AUOLIAAAlvnX7j78523.ico', '测试', '测试', 0, 0, '2023-08-23 21:04:51', '2023-08-23 21:04:50');
INSERT INTO `tag` VALUES (7093037178325942272, '请求', 'group1/M00/00/00/wKgUgGTl6aWALTTcAAAlvnX7j78513.ico', '1', '1', 1, 0, '2023-08-23 21:13:25', '2023-08-23 21:13:25');
INSERT INTO `tag` VALUES (7093038134199435264, '7', 'group1/M00/00/00/wKgUgGTl6ryAJqdEAAAlvnX7j78699.ico', '7', '7', 7, 0, '2023-08-23 21:17:13', '2023-08-23 21:17:13');
INSERT INTO `tag` VALUES (7093066883720658944, '0', 'group1/M00/00/00/wKgUgGTmB7GAf25vAAAIqDyOcTk493.jpg', '12', '1', 1, 0, '2023-08-23 23:11:28', '2023-08-23 23:11:27');
INSERT INTO `tag` VALUES (7093067060707704832, '77', 'group1/M00/00/00/wKgUgGTmB9OAe0I2AAANKI4aIMc207.jpg', '7', '7', 7, 1, '2023-08-23 23:12:10', '2023-08-23 23:35:11');
INSERT INTO `tag` VALUES (7093068433847336960, '4556', 'group1/M00/00/00/wKgUgGTmCUeAZ5elAAAFjti_7Hk438.jpg', '456', '456', 1, 1, '2023-08-23 23:17:37', '2023-08-23 23:20:51');
INSERT INTO `tag` VALUES (7093068647853309952, '21', 'group1/M00/00/00/wKgUgGTmGFSAYdlOAAAKRPB7V98751.jpg', 'https://coisini.cn', '1211', 1, 1, '2023-08-23 23:18:28', '2023-08-23 23:20:07');
INSERT INTO `tag` VALUES (7093084076185337856, '123', 'group1/M00/00/00/wKgUgGTmGROAQIVBAAANKI4aIMc966.jpg', '123', '123', 1, 0, '2023-08-24 00:19:47', '2023-08-24 00:19:46');
INSERT INTO `tag` VALUES (7093084156778889216, '1214', 'group1/M00/00/00/wKgUgGTmGSyAfEveAAAMIv-SJH4164.jpg', '11', '123', 11, 0, '2023-08-24 00:20:06', '2023-08-24 00:20:05');
INSERT INTO `tag` VALUES (7093084893009264640, '777', 'group1/M00/00/00/wKgUgGTmQ1OAPxoLAAAKprtwdBo382.jpg', NULL, '7', 0, 0, '2023-08-24 00:23:01', '2023-08-26 00:30:35');
INSERT INTO `tag` VALUES (7093126719577534464, '12我', 'group1/M00/00/00/wKgUgGTmRBGAeQHRAAAIrEw9Yjc334.jpg', '121', '121', 1, 0, '2023-08-24 03:09:14', '2023-08-24 03:09:13');
INSERT INTO `tag` VALUES (7093127372785856512, '5566', 'group1/M00/00/00/wKgUgGTmRL2AKrmqAAAMhHEi3ic337.jpg', NULL, NULL, 6, 0, '2023-08-24 03:11:49', '2023-08-26 00:53:05');
INSERT INTO `tag` VALUES (7093129333178380288, '2222', 'group1/M00/00/00/wKgUgGTmRr2AaP-AAAAJEHts0vo705.jpg', '222', '22', 2, 0, '2023-08-24 03:19:37', '2023-08-24 03:19:36');
INSERT INTO `tag` VALUES (7093129914647326720, '1231', 'group1/M00/00/00/wKgUgGTmR0-ASCXuAAAMfuQeUGs770.jpg', '1121', '121', 11, 0, '2023-08-24 03:21:55', '2023-08-24 03:21:55');
INSERT INTO `tag` VALUES (7093786989345910784, '这是一个长名称的标签', 'group1/M00/00/00/wKgUgGTnhtKATDE_AAE9JUVmXDw97.jfif', 'https://coisini.cn', '长名称标签', 1, 0, '2023-08-25 22:52:54', '2023-08-25 22:52:54');

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
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记（0可用 1已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$uIcjawZNHaiUypPJ8yOqmOCgOmAOHwzimnJW/8h4Tvd7SEl8bhDbS', 'admin', '15099909888', 'https://c-ssl.dtstatic.com/uploads/blog/202209/06/20220906115559_bd126.thumb.1000_0.jpg', NULL, 0, '2021-05-31 18:08:43', '2023-08-18 00:29:04', 0);
INSERT INTO `user` VALUES (2, 'xiang', '$2a$10$uIcjawZNHaiUypPJ8yOqmOCgOmAOHwzimnJW/8h4Tvd7SEl8bhDbS', '香香', '123456', NULL, NULL, 0, '2023-03-21 17:50:46', '2023-08-12 17:00:22', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
