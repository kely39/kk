CREATE TABLE `apple_pay_fail_record` (
  `id` varchar(64) NOT NULL,
  `receipt_data` text,
  `result` text,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `times` int(4) DEFAULT '1' COMMENT '失败次数',
  `err_msg` text,
  `product_type` int(2) DEFAULT NULL COMMENT '商品类型',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_updated_time` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='applePay二次验证失败记录';