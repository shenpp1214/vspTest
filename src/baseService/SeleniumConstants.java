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
	public static final String sql2 = "INSERT INTO iov.device_terminal VALUES ('2017112101', 'WC', 'WCBOX', '1', '', '2017112101', NULL, '2017112101', '1', '0', '1', '1', NULL, CURRENT_TIMESTAMP(), '2', 'dina', 'NN00222221N19402', NULL, '0', NULL, NULL, CURRENT_TIMESTAMP(), '0', 'dinacarrier', ' ', '', '', NULL, NULL, '0', '0', '12', CURDATE(), '12', CURDATE(), NULL, NULL, '0', '12', '0', NULL, NULL, '0', '0', '', '');";
	public static final String sql3 = "delete from iov.device where device_id='2017112101';";
	public static final String sql4 = "delete from iov.device_terminal where device_id='2017112101';";
	public static final String insertsql = "INSERT INTO iov.sys_user VALUES ('579da69f460340dca8b76ccb207415a5', '1', 'shenpp', 'shenpp', NULL, '2', NULL, '', '2017-11-23 10:53:02', '15951901290', '', '779230186@qq.com', '1', '670b14728ad9902aecba32e22fa4f6bd', '1', '', NULL, NULL, NULL, NULL, NULL, '3', NULL, '0', NULL, NULL, '0', NULL, NULL, '7', '10', '63', 'dinacarrier', 'dinacarrierD001', '0', NULL, '0', NULL, NULL, NULL, NULL, '0', NULL, '0', '1', '0', NULL, NULL, NULL, '0', '0', NULL, '0', '0', NULL, '47280');";
	public static final String delsql = "DELETE from iov.sys_user where USERNAME='shenpp';";
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
	public static final String username = "shenpp";
	public static final String realname = "张先森";
	public static final String Password = "666666";
	public static final String Email = "778899212@qq.com";
	public static final String mobile = "18361221575";
	// 管理中心 - 组织机构
	public static final String ORANAME = "shenpp专属机构";
	public static final String ORALINKEDPER = "沈培培";
	public static final String PERPHONE = "18361221575";
	public static final String sort = "2";
	public static final String modper = "申萍萍";
	// 商户运营-商城订单管理
	public static final String insert_order = "INSERT INTO poi.t_mall_goods_order VALUES ('59374cadee0c42d993a08dca3c111fef', '54024dc40c574b2799d4f9152e66b808', NULL, 'dinacarrier', '18011014473820691', 'h5', CURRENT_TIMESTAMP(), NULL, '32021801101457138116', '9f6a755724f94272874cb4d91328f60e', '测试Test', 'http://static.cpsdna.com/upload/vmaster/20170802/17080209524220064.JPG', '0.01', '100', '待支付', 'Payment timeout, order cancelled', '', NULL, NULL, '0', CURRENT_TIMESTAMP(), '15371022842', '2018-01-01 00:00:00', '2018-12-31 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL);";
	public static final String update_sql1 = "update poi.t_mall_goods_order set STATUS='101' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql2 = "update poi.t_mall_goods_order set STATUS='200' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql3 = "update poi.t_mall_goods_order set STATUS='103' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql4 = "update poi.t_mall_goods_order set STATUS='202' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String update_sql5 = "update poi.t_mall_goods_order set STATUS='300' where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String delete_order = "DELETE from poi.t_mall_goods_order where id='59374cadee0c42d993a08dca3c111fef';";
	public static final String orderId = "59374cadee0c42d993a08dca3c111fef";
	public static final String userName = "嘉兴人保（金币商城）17121216501910047";
	public static final String mobile1 = "15371022842";
	public static final String goodsName = "测试Test";
	// 商户运营-商城商品管理
	public static final String insert_mer = "INSERT INTO poi.t_mall_merchant VALUES ('54036d2a65bc429b93199a0b0c710d28', '46f6b33cd8ea49fb874da554bca0535b', '菇凉de店铺', NULL, NULL, 'dinacarrier', '0.30', '118.792669', '31.971146', '江苏省南京市南京南站', '17811110000', NULL, NULL, 'http://static.cpsdna.com/upload/vmaster/20180130/18013016401040279.JPG', NULL, '10:00', '21:00', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, '南京银行', '张宪森', '12345678901111122222', NULL, NULL, NULL, NULL, '2', '1', '2', NULL, NULL, '2018-01-30 16:40:12', 'cff0f666404845fa977bccf1b39f0d61', '2018-01-30 16:41:16', 'cff0f666404845fa977bccf1b39f0d61', NULL, NULL, '0', '0', NULL);";
	public static final String insert_cat = "INSERT INTO poi.t_mall_category_type VALUES ('6a41aa2a3c084137bd4b4cde9bf8cb43', '糖果时刻', '1', '1',CURRENT_TIMESTAMP(), 'cff0f666404845fa977bccf1b39f0d61', 'dinacarrier');";
	public static final String insert_gc = "INSERT INTO poi.t_mall_goods_category VALUES ('b28d086873f24f2594fe2d0eb0b2c651', '糖果铺子', 'http://static.cpsdna.com/upload/vmaster/20180211/18021111411310036.JPG', '1', '1', NULL, 'dinacarrier', NULL, '6a41aa2a3c084137bd4b4cde9bf8cb43', 'cff0f666404845fa977bccf1b39f0d61', CURRENT_TIMESTAMP());";
	public static final String del_mer = "DELETE from poi.t_mall_merchant WHERE name='菇凉de店铺';";
	public static final String del_cat = "DELETE FROM poi.t_mall_category_type where NAME='糖果时刻';";
	public static final String del_gc = "DELETE from poi.t_mall_goods_category WHERE name='糖果铺子';";
	public static final String del_goods = "DELETE FROM poi.t_mall_goods where NAME='开心糖果';";
}
