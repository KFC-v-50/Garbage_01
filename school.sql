/*
 Navicat Premium Data Transfer

 Source Server         : mybatis
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : administration

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 14/12/2023 18:52:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply`  (
  `stu_id` int(0) NOT NULL,
  `course_id` int(0) NOT NULL,
  `situation` int(0) NULL DEFAULT NULL COMMENT '1为批准0为不批准',
  `administrator` int(0) NULL DEFAULT NULL COMMENT '审核人id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `year` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trimesters` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `submit` int(0) NOT NULL,
  PRIMARY KEY (`stu_id`, `course_id`) USING BTREE,
  INDEX `stu_id1`(`stu_id`) USING BTREE,
  INDEX `course_id1`(`course_id`) USING BTREE,
  CONSTRAINT `course_id1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stu_id1` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `classroom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '教室类型',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`classroom`, `university`) USING BTREE,
  INDEX `classroom`(`classroom`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES ('N108', '普通教室', '华中师范大学');
INSERT INTO `classroom` VALUES ('N201', '普通教室', '华中师范大学');
INSERT INTO `classroom` VALUES ('N213', '普通教室', '华中师范大学');
INSERT INTO `classroom` VALUES ('N520', '实验室', '华中师范大学');
INSERT INTO `classroom` VALUES ('N522', '实验室', '华中师范大学');
INSERT INTO `classroom` VALUES ('N524', '实验室', '华中师范大学');

-- ----------------------------
-- Table structure for classroomapply
-- ----------------------------
DROP TABLE IF EXISTS `classroomapply`;
CREATE TABLE `classroomapply`  (
  `courseID` int(0) NOT NULL,
  `classroom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `staff_id` int(0) NOT NULL,
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` int(0) NOT NULL DEFAULT 0 COMMENT '审核状态',
  `id` int(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staffaafaef`(`staff_id`) USING BTREE,
  INDEX `classroomgsgrwg`(`classroom`) USING BTREE,
  INDEX `courseID`(`courseID`) USING BTREE,
  CONSTRAINT `classroomgsgrwg` FOREIGN KEY (`classroom`) REFERENCES `classroom` (`classroom`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `courseID` FOREIGN KEY (`courseID`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `staffaafaef` FOREIGN KEY (`staff_id`) REFERENCES `teacher` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroomapply
-- ----------------------------
INSERT INTO `classroomapply` VALUES (1, 'N201', 3, '华中师范大学', 1, 1);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int(0) NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher_id` int(0) NOT NULL,
  `classroom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` int(0) NOT NULL COMMENT '1为早八 2为早十 3为下二 4为下四',
  `date` int(0) NOT NULL COMMENT '周一到周五',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` int(0) NOT NULL DEFAULT 1 COMMENT '1为开设',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `credit` int(0) NOT NULL COMMENT '学分',
  `volume` int(0) NULL DEFAULT NULL COMMENT '容量',
  PRIMARY KEY (`course_id`, `university`) USING BTREE,
  INDEX `classroom`(`classroom`) USING BTREE,
  INDEX `staff_id`(`teacher_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `classroom` FOREIGN KEY (`classroom`) REFERENCES `classroom` (`classroom`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `staff_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '云计算', 3, 'N201', 1, 3, '个性发展课', '有关云计算方面的知识，熟悉云端开发', 1, '华中师范大学', '计算机学院', 2, 40);
INSERT INTO `course` VALUES (2, '算法', 2, 'N520', 2, 2, '专业必修课', '必修课', 1, '华中师范大学', '计算机学院', 2, 50);
INSERT INTO `course` VALUES (3, 'web程序设计', 3, 'N524', 2, 3, '个性发展课', '学习css html js php', 1, '华中师范大学', '计算机学院', 2, 50);
INSERT INTO `course` VALUES (4, '操作系统原理', 3, 'N522', 4, 3, '专业必修课', '很重要，考研要考', 1, '华中师范大学', '计算机学院', 2, 60);

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum`  (
  `id` int(0) NOT NULL,
  `userType` int(0) NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `id` int(0) NOT NULL,
  `class_name` int(0) NULL DEFAULT NULL,
  `grade` int(0) NULL DEFAULT NULL,
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `university` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`, `university`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `login_user` int(0) NULL DEFAULT NULL COMMENT '登录人id',
  `user_type` int(0) NULL DEFAULT NULL COMMENT '登录人身份',
  `cost_time` bigint(0) NULL DEFAULT NULL COMMENT '耗时，单位“ms”',
  `login_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录还是登出',
  `university` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, '2023-12-13 14:20:16', 1, 4, 2, 'logout', NULL);
INSERT INTO `log` VALUES (2, '2023-12-13 14:25:10', 1, 4, 245, 'login', '华中师范大学');
INSERT INTO `log` VALUES (3, '2023-12-13 14:26:40', 1, 4, 493, 'login', '华中师范大学');
INSERT INTO `log` VALUES (4, '2023-12-13 14:27:54', 1, 4, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (5, '2023-12-13 14:28:01', 1, 4, 14, 'login', '华中师范大学');
INSERT INTO `log` VALUES (6, '2023-12-13 14:28:15', 1, 4, 158, 'login', '华中师范大学');
INSERT INTO `log` VALUES (7, '2023-12-13 14:29:18', 202101, 1, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (8, '2023-12-13 14:46:19', 202102, 1, 711, 'login', '华中师范大学');
INSERT INTO `log` VALUES (9, '2023-12-13 14:46:37', 202102, 1, 5, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (10, '2023-12-13 14:46:55', 202103, 1, 76, 'login', '华中师范大学');
INSERT INTO `log` VALUES (11, '2023-12-13 14:47:07', 202103, 1, 4, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (12, '2023-12-13 14:47:19', 1, 4, 353, 'login', '华中师范大学');
INSERT INTO `log` VALUES (13, '2023-12-13 21:13:58', 202101, 1, 383, 'login', '华中师范大学');
INSERT INTO `log` VALUES (14, '2023-12-13 21:14:36', 202101, 1, 1, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (15, '2023-12-13 21:14:55', 1, 4, 90, 'login', '华中师范大学');
INSERT INTO `log` VALUES (16, '2023-12-13 21:29:02', 1, 4, 1, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (17, '2023-12-13 21:32:37', 3, 2, 87, 'login', '华中师范大学');
INSERT INTO `log` VALUES (18, '2023-12-13 21:32:58', 3, 2, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (19, '2023-12-13 21:33:05', 202101, 1, 89, 'login', '华中师范大学');
INSERT INTO `log` VALUES (20, '2023-12-13 21:33:13', 202101, 1, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (21, '2023-12-13 21:35:47', 6, 2, 88, 'login', '华中师范大学');
INSERT INTO `log` VALUES (22, '2023-12-13 21:35:54', 6, 2, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (23, '2023-12-13 21:36:15', 5, 3, 85, 'login', '华中师范大学');
INSERT INTO `log` VALUES (24, '2023-12-13 21:36:22', 4, 4, 85, 'login', '华中师范大学');
INSERT INTO `log` VALUES (25, '2023-12-13 21:36:25', 4, 4, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (26, '2023-12-14 10:22:13', 3, 2, 479, 'login', '华中师范大学');
INSERT INTO `log` VALUES (27, '2023-12-14 13:58:45', 3, 2, 2, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (28, '2023-12-14 13:59:05', 1, 4, 127, 'login', '华中师范大学');
INSERT INTO `log` VALUES (29, '2023-12-14 14:00:20', 1, 4, 1, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (30, '2023-12-14 14:00:35', 202101, 1, 88, 'login', '华中师范大学');
INSERT INTO `log` VALUES (31, '2023-12-14 14:01:05', 202101, 1, 1, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (32, '2023-12-14 14:01:24', 3, 2, 88, 'login', '华中师范大学');
INSERT INTO `log` VALUES (33, '2023-12-14 14:21:20', 3, 2, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (34, '2023-12-14 14:21:31', 3, 2, 89, 'login', '华中师范大学');
INSERT INTO `log` VALUES (35, '2023-12-14 14:29:46', 3, 2, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (36, '2023-12-14 14:30:06', 202101, 1, 86, 'login', '华中师范大学');
INSERT INTO `log` VALUES (37, '2023-12-14 14:30:22', 202101, 1, 0, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (38, '2023-12-14 14:30:27', 3, 2, 87, 'login', '华中师范大学');
INSERT INTO `log` VALUES (39, '2023-12-14 15:20:08', 3, 2, 3, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (40, '2023-12-14 15:20:15', 1, 4, 125, 'login', '华中师范大学');
INSERT INTO `log` VALUES (41, '2023-12-14 15:36:20', 1, 4, 2, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (42, '2023-12-14 15:36:28', 3, 2, 116, 'login', '华中师范大学');
INSERT INTO `log` VALUES (43, '2023-12-14 16:18:53', 3, 2, 128, 'login', '华中师范大学');
INSERT INTO `log` VALUES (44, '2023-12-14 16:29:51', 3, 2, 1, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (45, '2023-12-14 16:29:56', 1, 4, 101, 'login', '华中师范大学');
INSERT INTO `log` VALUES (46, '2023-12-14 18:31:13', 1, 4, 3, 'logout', '华中师范大学');
INSERT INTO `log` VALUES (47, '2023-12-14 18:31:20', 1, 4, 117, 'login', '华中师范大学');

-- ----------------------------
-- Table structure for selection
-- ----------------------------
DROP TABLE IF EXISTS `selection`;
CREATE TABLE `selection`  (
  `stu_id` int(0) NOT NULL,
  `course_id` int(0) NOT NULL,
  `score` int(0) NULL DEFAULT NULL,
  `year` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '2021-2022' COMMENT '学年',
  `trimesters` int(0) NOT NULL DEFAULT 1 COMMENT '学期',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ordinary` int(0) NULL DEFAULT NULL COMMENT '平时成绩',
  `ending` int(0) NULL DEFAULT NULL COMMENT '期末成绩',
  `status` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`stu_id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of selection
-- ----------------------------
INSERT INTO `selection` VALUES (202101, 1, 90, '2021-2022', 1, '华中师范大学', 85, 96, 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业',
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学校',
  `class` int(0) NULL DEFAULT NULL,
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456',
  `grand` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`, `university`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (202101, '学生1', '计算机科学与技术', '计算机学院', '华中师范大学', 2104, '$2a$10$Mf8SZS416QvQm9TdwOcSve/h7e8D9WdzSBOkqAdFHmGUKuCSk1iq.', 2021);
INSERT INTO `student` VALUES (202102, '学生2', '计算机科学与技术', '计算机学院', '华中师范大学', 2104, '$2a$10$rM/zd7Ko9oHSuKY/zSTL8OLbO6RXKXVPLVNPEM21eY0kWN4Xfe8eu', NULL);
INSERT INTO `student` VALUES (202103, '学生3', '软件工程', '计算机学院', '华中师范大学', 2104, '$2a$10$OaQdxNa42UpSEnuKAEgct.Z/IELfBCsi10ydOOt/JYLO3McR7Sf4e', NULL);
INSERT INTO `student` VALUES (202104, '学生4', '信息安全', '计算机学院', '华中师范大学', 2103, '$2a$10$h.Z4UAjuKd0OrNUU3BDYRuvgMUPp.uKJQGxsou89RPKehhjuTd/MG', NULL);
INSERT INTO `student` VALUES (202105, '学生5', '软件工程', '计算机学院', '华中师范大学', 2101, '$2a$10$Nh8f2ASB4ffj8zJ5lHSIJu9Cv41MmeeyL24tkg8bnFy.FW5oINKlu', NULL);
INSERT INTO `student` VALUES (202106, '学生6', '物联网工程', '计算机学院', '华中师范大学', 2106, '$2a$10$oU.IUpL6ury9rthAU6VprOB1qWst070gWh8xQyAw.uQCTqWkSuTE.', NULL);
INSERT INTO `student` VALUES (202107, '学生7', '软件工程', '计算机学院', '华中师范大学', 2103, '$2a$10$wIqaJ.Ea6KDoS13y32OVL.koo6wI/CO93X6QkzmlCaV9ySf.2eerO', NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `staff_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `class` int(0) NULL DEFAULT NULL COMMENT '管理的班级号',
  `permission` int(0) NOT NULL COMMENT '权限 1为教师 2为教秘 4为班主任 3为辅导员 5为管理员',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge',
  `university` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`staff_id`, `university`) USING BTREE,
  INDEX `staff_id`(`staff_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'admin', NULL, 5, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '计算机学院');
INSERT INTO `teacher` VALUES (2, '班主任', 2104, 4, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '计算机学院');
INSERT INTO `teacher` VALUES (3, '老师', NULL, 1, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '计算机学院');
INSERT INTO `teacher` VALUES (4, '教秘', 21, 2, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '计算机学院');
INSERT INTO `teacher` VALUES (5, '辅导员', 21, 3, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '计算机学院');
INSERT INTO `teacher` VALUES (6, '老师2', NULL, 1, '$2a$10$.BOadyrJ3GxS/03bQXO7demaUGw/tEQSNOh6y7PEzbmt6vYiAdOge', '华中师范大学', '信息管理学院');

SET FOREIGN_KEY_CHECKS = 1;
