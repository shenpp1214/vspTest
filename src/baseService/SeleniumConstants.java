package baseService;

public class SeleniumConstants {
	// 结算管理参数
	public static final String balanceNo = "170930105000530354";// 结算管理 - 结算单号
	public static final String searchMonth = "2017-08";// 结算管理 - 年月
	public static final String merchantname = "spp小店铺大开心";// 结算管理(商户结算-商户名称)
	public static final String balanceNo1 = "170930105000745199";// 商户结算 - 结算单号
	public static final String fee1 = "6.66";// 修改的金额
	public static final String reason = "修改金额测试";// 修改原因
	public static final String serialNum = "2017123456481001";// 修改原因
	// sql语句
	public static final String sql1 = "INSERT INTO iov.device VALUES ('2017112101', NULL, NULL, '1', '2017112101', '2017112101', '', '', '0', '1', '1', NULL, NULL, NULL, NULL, NULL, 'WC', '1', '1', '0', '1', '0', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP(), NULL, 'NN00222221N19402', '1', '1', NULL, NULL, NULL, '0', '0', NULL, 'dinacarrier', ' ', NULL, NULL, '0', '0', '0');";
	public static final String sql2 = "INSERT INTO iov.device_terminal VALUES ('2017112101', 'WC', 'WCBOX', '1', '', '2017112101', NULL, '2017112101', '1', '0', '1', '1', NULL, CURRENT_TIMESTAMP(), '2', 'dina', 'NN00222221N19402', NULL, '0', NULL, NULL, CURRENT_TIMESTAMP(), '0', 'dinacarrier', ' ', '', '', NULL, NULL, '0', '0', '12', CURDATE(), '12', CURDATE(), NULL, NULL, '0', '12', '0', NULL, NULL, '0', '0', '', '0', NULL, NULL);";
	public static final String sql3 = "delete from iov.device where device_id='2017112101';";
	public static final String sql4 = "delete from iov.device_terminal where device_id='2017112101';";
	public static final String insertsql = "INSERT INTO iov.sys_user VALUES ('579da69f460340dca8b76ccb207415a5', '1', 'shenpp', 'shenpp', NULL, '2', NULL, '', '2017-11-23 10:53:02', '15951901290', '', '779230186@qq.com', '1', '670b14728ad9902aecba32e22fa4f6bd', '1', '', NULL, NULL, NULL, NULL, NULL, '3', NULL, '0', NULL, NULL, '0', NULL, NULL, '7', '10', '63', 'dinacarrier', 'dinacarrierD001', '0', NULL, '0', NULL, NULL, NULL, NULL, '0', NULL, '0', '1', '0', NULL, NULL, NULL, '0', '0', NULL, '0', '0', NULL, '47280');";
	public static final String delsql = "DELETE from iov.sys_user where USERNAME='shenpp' AND subscriberid='579da69f460340dca8b76ccb207415a5';";
	// 积分统计界面参数
	public static final String date = "2017-11-26";
	public static final String user = "嘉兴人保（金币商城）17061517050380793";
	public static final String phone = "15190479709";
	// 销量排行参数+销量统计
	public static final String date1 = "2017-10-01";
	public static final String date2 = "2017-10-31";
	public static final String date3 = "2017-10";
	public static double orders = 0.0;
	public static double moneys = 0.0;
	// 功能点击统计
	public static final String beDate = "2017-05-17";
	// 管理中心-操作记录
	public static final String log_username = "admin@dina";
	public static final String log_sort1 = "活动管理类型";
	public static final String log_type1 = "全部";
	public static final String usertype = "总管理员";
	public static final String log_sort2 = "设备管理类型";
	public static final String log_type2 = "调拨设备";
	// 管理中心-员工账号
	public static final String del_user = "DELETE from iov.sys_user where realname='张先森';";
	public static final String del_sa = "DELETE FROM iov.sa_account WHERE sa_id='03f2c40bdaaa451db972371973f6c750';";
	public static final String insert_sa = "INSERT INTO poi.t_mall_category_type VALUES ('6932ba21177a45458c09da63ad3b2b03', '糖果时刻', '1', '1', CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', 'dinacarrier');";
	// 管理中心 - 组织机构
	public static final String del_dept = "DELETE FROM iov.corp_dept where DEPT_NAME='shenpp专属机构';";
	// 商户运营-商城订单管理
	public static final String insert_order = "INSERT INTO poi.t_mall_goods_order VALUES ('59374cadee0c42d993a08dca3c111fef', '54024dc40c574b2799d4f9152e66b808', NULL, 'dinacarrier', '18011014473820691', 'h5', CURRENT_TIMESTAMP(), NULL, '32021801101457138116', '9f6a755724f94272874cb4d91328f60e', '测试Test', 'http://static.cpsdna.com/upload/vmaster/20170802/17080209524220064.JPG', '0.01', '100', '待支付', 'Payment timeout, order cancelled', '', NULL, NULL, '0', CURRENT_TIMESTAMP(), '15371022842', '2018-01-01 00:00:00', '2018-12-31 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL);";
	public static final String update_sql1 = "update poi.t_mall_goods_order set STATUS='101' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql2 = "update poi.t_mall_goods_order set STATUS='200' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql3 = "update poi.t_mall_goods_order set STATUS='103' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql4 = "update poi.t_mall_goods_order set STATUS='202' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql5 = "update poi.t_mall_goods_order set STATUS='300' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String delete_order = "DELETE from poi.t_mall_goods_order where id='59374cadee0c42d993a08dca3c111fef';";
	// 商户运营-商城商户管理
	public static final String del_mer = "DELETE from poi.t_mall_merchant WHERE name='菇凉de店铺';";
	public static final String del_u = "DELETE FROM iov.sys_user WHERE username='hans111';";
	// 商户运营-商城商品管理
	public static final String insert_mer = "INSERT INTO poi.t_mall_merchant VALUES ('54036d2a65bc429b93199a0b0c710d28', '46f6b33cd8ea49fb874da554bca0535b', '菇凉de店铺', NULL, NULL, 'dinacarrier', '0.30', '118.792669', '31.971146', '江苏省南京市南京南站', '17811110000', NULL, NULL, 'http://static.cpsdna.com/upload/vmaster/20180130/18013016401040279.JPG', NULL, '10:00', '21:00', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, '南京银行', '张宪森', '12345678901111122222', NULL, NULL, NULL, NULL, '2', '1', '2', NULL, NULL, CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', NULL, NULL, '0', '0', NULL);";
	public static final String insert_gc = "INSERT INTO poi.t_mall_goods_category VALUES ('b28d086873f24f2594fe2d0eb0b2c651', '糖果铺子', 'http://static.cpsdna.com/upload/vmaster/20180211/18021111411310036.JPG', '1', '1', NULL, 'dinacarrier', NULL, '6a41aa2a3c084137bd4b4cde9bf8cb43', 'cff0f666404845fa977bccf1b39f0d61', CURRENT_TIMESTAMP());";
	public static final String insert_goods = "INSERT INTO poi.t_mall_goods VALUES ('6acc72d49b13469ea3fe10f4d5ce8776', '开心糖果', '', '54036d2a65bc429b93199a0b0c710d28', 'dinacarrier', '13.80', '0', 'b28d086873f24f2594fe2d0eb0b2c651', '1', 'http://static.cpsdna.com/upload/vmaster/20180930/18093009343073211.JPG', 'http://static.cpsdna.com/upload/vmaster/20180930/18093009342673210.JPG', '', '<p>测试测试哦</p>', NULL, NULL, CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', CURRENT_TIMESTAMP(), NULL, '', '0', '1', '0', '0', '0', '0', '0', 'ALL', NULL, '0', '0', NULL, '0.00');";
	public static final String del_gc = "DELETE from poi.t_mall_goods_category WHERE name='糖果铺子';";
	public static final String del_goods = "DELETE FROM poi.t_mall_goods where NAME='开心糖果';";
	// 商户运营 - 栏目管理
	public static final String del_ca = "DELETE FROM poi.t_mall_category_type where NAME LIKE'糖果时刻%';";
	public static final String del_goodsca = "DELETE FROM poi.t_mall_goods_category where NAME like'解忧糖%';";
	public static final String insert_ca = "INSERT INTO poi.t_mall_category_type VALUES ('6932ba21177a45458c09da63ad3b2b03', '糖果时刻', '1', '1', '2018-09-29 17:04:20', 'cff0f666404845fa977bccf1b39f0d61', 'dinacarrier');";
	// 运营中心 - 人保
	public static final String insert_act = "INSERT INTO iov.t_activity_info VALUES ('jspicc_winbigcrab_201809', '请你吃大闸蟹', CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(), '', NULL, '1', NULL, '0', '0', NULL, '1');";
	public static final String insert_winrecord = "INSERT INTO iov.t_lottery_record VALUES ('fake2018092510', 'fake2018092510', '199****5961', '', 'jspicc_winbigcrab_201809', '2', '2', NULL, '1',CURRENT_TIMESTAMP(), '0', '0000-00-00 00:00:00', CURRENT_TIMESTAMP(), '1');";
	public static final String del_act = "DELETE FROM iov.t_activity_info WHERE activity_name='请你吃大闸蟹';";
	public static final String del_winrecord = "DELETE FROM iov.t_lottery_record WHERE activity_id='jspicc_winbigcrab_201809';";
	// 运营中心 - 违章订单
	public static final String insert_vioOrder = "INSERT INTO iov.t_violation_order VALUES ('c86cef2c4148424799181af219a88247', NULL, '17011811315060252', '0', '苏A00088', NULL, 'spp', '18333333333', '100', '32.022858', '118.780060', 'VE9wNkc3Q3JqdWlKSml6Z0dCb29CUnVCLzNFTzZicG9MZWlxV1JTaUtTdz0K', NULL, '待支付', '23.00', '23.00', '20.00', '1', '73.00', '50.00', NULL, CURRENT_TIMESTAMP(), 'xfinder4personal', CURRENT_TIMESTAMP(), NULL, NULL, NULL, NULL, '32451706081047320016', NULL, '123', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());";
	public static final String del_vioOrder = "DELETE FROM iov.t_violation_order WHERE rec_id='c86cef2c4148424799181af219a88247';";
	public static final String update_vioOrder101 = "UPDATE iov.t_violation_order set order_status='101' WHERE rec_id='c86cef2c4148424799181af219a88247';";
	public static final String update_vioOrder102 = "UPDATE iov.t_violation_order set order_status='102' WHERE rec_id='c86cef2c4148424799181af219a88247';";
	public static final String update_vioOrder200 = "UPDATE iov.t_violation_order set order_status='200' WHERE rec_id='c86cef2c4148424799181af219a88247';";
	// 运营中心 - 渠道管理
	public static final String del_channel = "DELETE FROM iov.t_violation_channel WHERE name='新增渠道名称';";
	// 运营中心-汽车后服务
	public static final String insert_category = "INSERT INTO iov.app_display_column_category VALUES ('18092711225090641', '百宝箱', '1', 'dinacarrier', '1', '1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', 'cff0f666404845fa977bccf1b39f0d61', NULL);";
	public static final String del_category = "DELETE from iov.app_display_column_category WHERE NAME='百宝箱';";
	public static final String del_column = "DELETE from iov.app_display_column WHERE column_name='测试Test';";
	// 营销位管理
	public static final String del_marpos = "DELETE FROM iov.app_market_column WHERE column_name='唤颜面膜oaz';";
}
