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

 Date: 17/08/2023 01:12:37
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
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `menu` VALUES (20, 0, '日志管理', 0, 'navigation', 'Layout', '', 'el-icon-tickets', 1, 0, '2023-04-29 18:27:41', '2023-04-29 18:47:29', 0);
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
INSERT INTO `role` VALUES (2, '香香测试', 'xiang', NULL, '2023-03-21 17:51:53', '2023-03-21 17:51:53', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (8, 2, 2);
INSERT INTO `role_menu` VALUES (9, 2, 3);
INSERT INTO `role_menu` VALUES (10, 2, 6);
INSERT INTO `role_menu` VALUES (11, 2, 7);
INSERT INTO `role_menu` VALUES (12, 2, 8);
INSERT INTO `role_menu` VALUES (13, 2, 9);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7083889432750845953 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sort_tag
-- ----------------------------
INSERT INTO `sort_tag` VALUES (7083889427105312768, 7073042872142446592, 7083585521875746816);
INSERT INTO `sort_tag` VALUES (7083889432750845952, 7083888084491825152, 7083585521875746816);

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
  `ord` int NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0正常 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (7083585521875746816, '测试1', NULL, NULL, '测试专用1', 1, 0, '2023-07-28 19:15:55', '2023-07-29 11:37:35');
INSERT INTO `tag` VALUES (7083596148363034624, NULL, NULL, NULL, '测试专用', 1, 0, '2023-07-28 19:58:08', '2023-07-28 19:58:08');
INSERT INTO `tag` VALUES (7083830547545452544, '测试', NULL, NULL, '测试专用', 1, 0, '2023-07-29 11:29:33', '2023-07-29 11:29:33');
INSERT INTO `tag` VALUES (7090452155672305664, '1', NULL, NULL, '1', 1, 0, '2023-08-16 18:01:28', '2023-08-16 18:01:27');
INSERT INTO `tag` VALUES (7090452233346621440, '122', NULL, NULL, '22', 1, 0, '2023-08-16 18:01:46', '2023-08-16 18:01:27');

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
  `head_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像地址',
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
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$uIcjawZNHaiUypPJ8yOqmOCgOmAOHwzimnJW/8h4Tvd7SEl8bhDbS', 'admin', '15099909888', 'https://d-ssl.dtstatic.com/uploads/blog/202302/19/20230219092149_943ab.thumb.300_300_c.jpeg_webp', NULL, 0, '2021-05-31 18:08:43', '2023-04-29 19:05:23', 0);
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
