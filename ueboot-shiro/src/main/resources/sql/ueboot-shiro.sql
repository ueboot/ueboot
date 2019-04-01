/*
 Navicat Premium Data Transfer

 Source Server         : 47.100.242.77-mysql8
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 47.100.242.77:3306
 Source Schema         : ueboot-shiro

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 01/03/2019 16:46:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `level` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_code` varchar(255) DEFAULT NULL,
  `parent_path` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `type` enum('机构','部门') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_code` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(40) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(40) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK73ogamir4f5eqt48s14524sdt` (`resource_code`),
  KEY `FKaoemqa19twbb9o8e5xq6am0c7` (`role_code`),
  KEY `FKcv8ki936tcaenkg3s8oc8j52n` (`resource_id`),
  KEY `FKk9ru2110pc5m5ja96jh0dth0j` (`role_id`),
  CONSTRAINT `FKcv8ki936tcaenkg3s8oc8j52n` FOREIGN KEY (`resource_id`) REFERENCES `sys_resources` (`id`),
  CONSTRAINT `FKk9ru2110pc5m5ja96jh0dth0j` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=413 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (327, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 3, 5);
INSERT INTO `sys_permission` VALUES (328, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 4, 5);
INSERT INTO `sys_permission` VALUES (329, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 9, 5);
INSERT INTO `sys_permission` VALUES (330, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 10, 5);
INSERT INTO `sys_permission` VALUES (331, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 20, 5);
INSERT INTO `sys_permission` VALUES (332, NULL, NULL, 'root', '2018-10-15 08:43:27', 'root', '2018-10-15 08:43:27', 21, 5);
INSERT INTO `sys_permission` VALUES (333, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 3, 2);
INSERT INTO `sys_permission` VALUES (334, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 4, 2);
INSERT INTO `sys_permission` VALUES (335, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 8, 2);
INSERT INTO `sys_permission` VALUES (336, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 9, 2);
INSERT INTO `sys_permission` VALUES (337, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 10, 2);
INSERT INTO `sys_permission` VALUES (338, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 20, 2);
INSERT INTO `sys_permission` VALUES (339, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 21, 2);
INSERT INTO `sys_permission` VALUES (340, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 5, 2);
INSERT INTO `sys_permission` VALUES (341, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 11, 2);
INSERT INTO `sys_permission` VALUES (342, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 12, 2);
INSERT INTO `sys_permission` VALUES (343, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 13, 2);
INSERT INTO `sys_permission` VALUES (344, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 22, 2);
INSERT INTO `sys_permission` VALUES (345, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 23, 2);
INSERT INTO `sys_permission` VALUES (346, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 24, 2);
INSERT INTO `sys_permission` VALUES (347, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 6, 2);
INSERT INTO `sys_permission` VALUES (348, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 14, 2);
INSERT INTO `sys_permission` VALUES (349, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 15, 2);
INSERT INTO `sys_permission` VALUES (350, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 16, 2);
INSERT INTO `sys_permission` VALUES (351, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 27, 2);
INSERT INTO `sys_permission` VALUES (352, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 30, 2);
INSERT INTO `sys_permission` VALUES (353, NULL, NULL, 'root', '2018-10-15 08:43:41', 'root', '2018-10-15 08:43:41', 29, 2);
INSERT INTO `sys_permission` VALUES (388, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 3, 1);
INSERT INTO `sys_permission` VALUES (389, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 4, 1);
INSERT INTO `sys_permission` VALUES (390, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 8, 1);
INSERT INTO `sys_permission` VALUES (391, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 9, 1);
INSERT INTO `sys_permission` VALUES (392, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 10, 1);
INSERT INTO `sys_permission` VALUES (393, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 20, 1);
INSERT INTO `sys_permission` VALUES (394, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 21, 1);
INSERT INTO `sys_permission` VALUES (395, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 5, 1);
INSERT INTO `sys_permission` VALUES (396, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 11, 1);
INSERT INTO `sys_permission` VALUES (397, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 12, 1);
INSERT INTO `sys_permission` VALUES (398, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 13, 1);
INSERT INTO `sys_permission` VALUES (399, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 22, 1);
INSERT INTO `sys_permission` VALUES (400, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 23, 1);
INSERT INTO `sys_permission` VALUES (401, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 24, 1);
INSERT INTO `sys_permission` VALUES (402, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 6, 1);
INSERT INTO `sys_permission` VALUES (403, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 14, 1);
INSERT INTO `sys_permission` VALUES (404, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 15, 1);
INSERT INTO `sys_permission` VALUES (405, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 16, 1);
INSERT INTO `sys_permission` VALUES (406, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 27, 1);
INSERT INTO `sys_permission` VALUES (407, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 28, 1);
INSERT INTO `sys_permission` VALUES (408, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 30, 1);
INSERT INTO `sys_permission` VALUES (409, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 31, 1);
INSERT INTO `sys_permission` VALUES (410, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 34, 1);
INSERT INTO `sys_permission` VALUES (411, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 29, 1);
INSERT INTO `sys_permission` VALUES (412, NULL, NULL, 'root', '2018-10-23 23:38:40', 'root', '2018-10-23 23:38:40', 35, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `rank_` bigint(20) DEFAULT NULL,
  `resource_type` enum('菜单组','菜单','功能','其他') DEFAULT NULL,
  `theme_json` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKglxcbhp7kn357vaor242fuj8c` (`parent_id`),
  CONSTRAINT `FKglxcbhp7kn357vaor242fuj8c` FOREIGN KEY (`parent_id`) REFERENCES `sys_resources` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
BEGIN;
INSERT INTO `sys_resources` VALUES (3, b'1', '权限管理', NULL, 1, '菜单组', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_resources` VALUES (4, b'1', '用户管理', 'ueboot:user:read', 999, '菜单', NULL, '/ueboot/shiro/User', NULL, NULL, 'root', '2018-09-08 09:55:51', '权限管理', 3);
INSERT INTO `sys_resources` VALUES (5, b'1', '角色管理', 'ueboot:role:read', 998, '菜单', NULL, '/ueboot/shiro/Role', NULL, NULL, 'root', '2018-09-08 09:55:58', '权限管理', 3);
INSERT INTO `sys_resources` VALUES (6, b'1', '资源管理', 'ueboot:resources:read', 997, '菜单', NULL, '/ueboot/shiro/Resources', NULL, NULL, 'root', '2018-09-08 09:56:05', '权限管理', 3);
INSERT INTO `sys_resources` VALUES (8, b'1', '查询', 'ueboot:user:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '用户管理', 4);
INSERT INTO `sys_resources` VALUES (9, b'1', '添加与修改', 'ueboot:user:save', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '用户管理', 4);
INSERT INTO `sys_resources` VALUES (10, b'1', '删除', 'ueboot:user:delete', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '用户管理', 4);
INSERT INTO `sys_resources` VALUES (11, b'1', '查询', 'ueboot:role:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (12, b'1', '添加与修改', 'ueboot:role:save', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (13, b'1', '删除', 'ueboot:role:delete', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (14, b'1', '查询', 'ueboot:resources:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '资源管理', 6);
INSERT INTO `sys_resources` VALUES (15, b'1', '添加与修改', 'ueboot:resources:save', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '资源管理', 6);
INSERT INTO `sys_resources` VALUES (16, b'1', '删除', 'ueboot:resources:delete', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '资源管理', 6);
INSERT INTO `sys_resources` VALUES (20, b'1', '角色查询', 'ueboot:role:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '用户管理', 4);
INSERT INTO `sys_resources` VALUES (21, b'1', '分配角色', 'ueboot:userRole:save', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '用户管理', 4);
INSERT INTO `sys_resources` VALUES (22, b'1', '资源列表查询', 'ueboot:resources:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (23, b'1', '权限查询', 'ueboot:userRole:read', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (24, b'1', '权限分配', 'ueboot:userRole:save', NULL, '功能', NULL, NULL, NULL, NULL, NULL, NULL, '角色管理', 5);
INSERT INTO `sys_resources` VALUES (27, b'1', '有三级菜单', NULL, NULL, '菜单组', NULL, NULL, 'root', '2018-09-08 09:05:51', 'root', '2018-09-08 09:05:51', '权限管理', 3);
INSERT INTO `sys_resources` VALUES (28, b'1', 'FormGrid', 'a', 2, '菜单', NULL, '/crud', 'root', '2018-09-08 09:06:17', 'root', '2018-09-08 09:24:13', '有三级菜单', 27);
INSERT INTO `sys_resources` VALUES (29, b'1', '三级菜单组', NULL, 2, '菜单组', NULL, NULL, 'root', '2018-09-08 09:06:43', 'root', '2018-09-08 09:06:43', '权限管理', 3);
INSERT INTO `sys_resources` VALUES (30, b'1', 'Tree', 'b', 5, '菜单', NULL, '/tree', 'root', '2018-09-08 09:56:44', 'root', '2018-09-08 09:56:44', '有三级菜单', 27);
INSERT INTO `sys_resources` VALUES (31, b'1', 'FormDemo', '2', NULL, '菜单', NULL, '/FormDemo', 'root', '2018-09-13 21:34:50', 'root', '2018-09-13 21:34:50', '有三级菜单', 27);
INSERT INTO `sys_resources` VALUES (34, b'1', 'treeSelect', 'treeSelect', NULL, '菜单', NULL, '/treeSelect', 'root', '2018-09-28 15:42:56', 'root', '2018-09-28 15:43:32', '有三级菜单', 27);
INSERT INTO `sys_resources` VALUES (35, b'1', 'crud', 'crud', NULL, '菜单', NULL, '/crud', 'root', '2018-09-28 19:52:03', 'root', '2018-09-28 19:52:03', '三级菜单组', 29);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_plpigyqwsqfn7mn66npgf9ftp` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, NULL, NULL, NULL, NULL, b'1', NULL, '最高用户权限', '超级管理员', NULL, NULL);
INSERT INTO `sys_role` VALUES (2, NULL, NULL, NULL, NULL, b'1', NULL, NULL, '业务管理员', NULL, NULL);
INSERT INTO `sys_role` VALUES (5, 'root', '2018-10-01 13:01:09', 'root', '2018-10-01 13:01:09', b'1', NULL, 'NAME_TEST', 'NAME_TEST', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `credential_expired_date` datetime DEFAULT NULL,
  `is_locked` bit(1) DEFAULT NULL,
  `role_names` varchar(255) DEFAULT NULL,
  `role_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, NULL, 'root', '2018-10-13 23:50:51', NULL, 'd71e762f824bd6f39c505fa6804a3a20f3dc7e2fd8b4a0a5691bfd5326c061e2d84a0ee306a864955599f92c03840c5db3706a21484e8a0ee35539951e1f042b', 'root', '2019-03-30 14:18:21', b'0', '超级管理员,业务管理员', '1,2,');
INSERT INTO `sys_user` VALUES (2, NULL, NULL, 'root', '2018-10-13 23:50:46', NULL, 'd02db260d58a26d777bfa1c8dd41b2a0492024e64cd69c3401223dcea60c8b3d2a44276748e0de9e763b630e84ef1a362b8ed370a9849a2346df769a25c3f8f3', 'admin', '2019-03-30 17:42:45', b'0', '', '');
INSERT INTO `sys_user` VALUES (6, NULL, NULL, NULL, '2018-09-04 20:33:24', NULL, '5c83a5d1967a3d317daeb97a6ec6bd16d508d1f595c6f32acaa24b760556afbbf7565ee87205bf313d0e6956ff6e26121a3a454e155a5cff118f77dc78963730', 'test', '2019-03-04 20:33:24', b'0', '', '');
INSERT INTO `sys_user` VALUES (7, NULL, '2018-09-04 20:33:13', 'root', '2018-10-13 23:50:28', NULL, '487aa1bb7ee37abe3b4f7c696b7fdc95246b9f3059da58f9b84ee6fe9e294ce02d895b0ef9a78acce65224ff439e042043075b78f1d4c3c26f22b8037982eef1', 'test2', '2019-03-04 20:45:38', b'0', '', '');
INSERT INTO `sys_user` VALUES (9, 'root', '2018-09-30 18:48:37', 'root', '2018-10-01 13:01:54', NULL, 'c3e12da9af6b5b518ea96e77b313550f40885c73198ac67f0b57b1af90f0f67950b7f32c57180529e18081847c1b4640a459b6358abf6cbe48591119bb1bce19', '111', '2019-03-30 18:48:37', b'0', 'NAME_TEST', '5,');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (32, 5, 9, 'root', '2018-10-01 13:01:54', 'root', '2018-10-01 13:01:54');
INSERT INTO `sys_user_role` VALUES (33, 1, 1, 'root', '2018-10-13 23:50:51', 'root', '2018-10-13 23:50:51');
INSERT INTO `sys_user_role` VALUES (34, 2, 1, 'root', '2018-10-13 23:50:51', 'root', '2018-10-13 23:50:51');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
