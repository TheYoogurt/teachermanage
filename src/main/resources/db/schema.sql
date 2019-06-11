CREATE TABLE teacher(
  `id` int NOT NULL PRIMARY KEY auto_increment,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL,
  `name` varchar(64) NOT NULL,
  `sex` varchar(6) NOT NULL,
  `address` varchar(255) NOT NULL
);
CREATE TABLE student(
  `id` int NOT NULL PRIMARY KEY auto_increment,
  `create_time` timestamp NULL,
  `update_time` timestamp NULL,
  `name` varchar(64) NULL,
  `sex` varchar(6) NULL,
  `address` varchar(255) NULL,
  `age` int null,
  `teacher_name` varchar(64) NOT NULL
);