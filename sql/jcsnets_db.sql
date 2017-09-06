/*
 Navicat Premium Data Transfer

 Source Server         : wamp_jcs
 Source Server Type    : MySQL
 Source Server Version : 50714
 Source Host           : localhost:3307
 Source Schema         : jcsnets_db

 Target Server Type    : MySQL
 Target Server Version : 50714
 File Encoding         : 65001

 Date: 06/09/2017 10:13:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `password` varchar(128) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `loggedin` tinyint(4) NOT NULL,
  `lastlogin` timestamp(0) DEFAULT NULL,
  `createdat` timestamp(0) NOT NULL,
  `birthday` date NOT NULL,
  `banned` tinyint(1) NOT NULL,
  `banreason` text CHARACTER SET latin1 COLLATE latin1_swedish_ci,
  `gm` tinyint(1) NOT NULL,
  `email` tinytext CHARACTER SET latin1 COLLATE latin1_swedish_ci,
  `cash` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
