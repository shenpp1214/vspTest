package merchantOperate;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class OrderMgr extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	protected static void prepareData(String pd) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", pd);
	}

	@BeforeClass
	public static void openBrowerOrderMgr() throws Exception {
		openBrower("vsp_url");
		prepareData(SeleniumConstants.insert_order);// 准备数据
	}

	@AfterClass
	public static void tearDown() {
		dr.close();
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void orderMgr() throws Exception {
		toPay();// 待支付界面
		closedOrder();// 关闭订单界面
		toConsume();// 待消费界面
		successOrder();// 成功订单
		toRefund();// 待退款
		refunded();// 退款完成
		toDeliver();// 待发货
		delivered();// 已发货
		allOrders();// 全部订单查看
	}

	private void toPay() throws InterruptedException {
		entryPage("商户运营", "商城订单管理");
		entryTab("待支付");
		searchOrderId();
		seeDetail("待支付", "未消费");
		clickBtn("deptUserInfo", 1);

		new WebDriverWait(dr, 15).until(ExpectedConditions.elementToBeClickable(By.className("noty_text")));
		assertEquals("成功 取消订单成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void closedOrder() throws Exception {
		entryTab("关闭订单");
		searchOrderId();
		seeDetail("订单取消", "未消费");
		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.update_sql1);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void toConsume() throws Exception {
		entryTab("待消费");
		searchOrderId();
		seeDetail("待消费", "未消费");
		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.update_sql2);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void successOrder() throws Exception {
		entryTab("成功订单");
		searchOrderId();
		seeDetail("交易成功", "未消费");
		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.update_sql3);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void toRefund() throws Exception {
		entryTab("待退款");
		searchOrderId();
		seeDetail("退款中", "未消费");
		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.update_sql4);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void refunded() throws Exception {
		entryTab("退款完成");
		searchOrderId();
		seeDetail("退款成功", "未消费");
		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.update_sql5);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void toDeliver() throws Exception {
		entryTab("待发货");
		searchOrderId();
		seeDetail("待发货", "未消费");

		dr.findElement(By.id("expressCompanyInfo")).sendKeys("顺丰");
		dr.findElement(By.id("expressNumberInfo")).sendKeys("2018012901");
		sleep(2000);

		clickBtn("deptUserInfo", 1);
		new WebDriverWait(dr, 15).until(ExpectedConditions.elementToBeClickable(By.className("noty_text")));
		assertEquals("成功 发货成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
	}

	private void delivered() throws Exception {
		entryTab("已发货");
		searchOrderId();
		seeDetail("已发货", "未消费");
		clickBtn("deptUserInfo", 1);
	}

	private void allOrders() throws Exception {
		entryTab("全部订单");
		searchOrderId();
		seeDetail("已发货", "未消费");

		assertEquals("顺丰", dr.findElement(By.id("expressCompanyInfo")).getAttribute("value"));
		assertEquals("2018012901", dr.findElement(By.id("expressNumberInfo")).getAttribute("value"));

		clickBtn("deptUserInfo", 1);
		prepareData(SeleniumConstants.delete_order);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);

		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td")).getText());
		resetTest("resetBtn", "searchBtn", "pages");
	}

	protected void entryTab(String text) throws InterruptedException {
		dr.findElement(By.linkText(text)).click();
		sleep(3000);
	}

	protected void searchOrderId() throws InterruptedException {
		dr.findElement(By.id("searchOrderNo")).sendKeys(SeleniumConstants.orderId);

		sleep(3000);
		searchTest("searchBtn", "pages");
	}

	protected void seeDetail(String os, String us) throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(3000);

		assertEquals(SeleniumConstants.orderId, dr.findElement(By.id("orderIdInfo")).getAttribute("value"));
		assertEquals(SeleniumConstants.userName, dr.findElement(By.id("userNameInfo")).getAttribute("value"));
		assertEquals(SeleniumConstants.mobile1, dr.findElement(By.id("mobileInfo")).getAttribute("value"));
		assertEquals(SeleniumConstants.goodsName, dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		assertEquals(date, dr.findElement(By.id("createOrderTimeInfo")).getAttribute("value"));
		assertEquals(os, dr.findElement(By.id("orderStateInfo")).getAttribute("value"));
		assertEquals(us, dr.findElement(By.id("useStateInfo")).getAttribute("value"));
	}

	protected void clickBtn(String bid, int num) throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='" + bid + "']/following-sibling::div//button[" + num + "]/span")).click();
		sleep(2000);
	}

	@After
	public void logoutOrderMgr() throws InterruptedException {
		logoutVsp();
	}

}
