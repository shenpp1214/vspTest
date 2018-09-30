package merchantOperate;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
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
		prepareData(SeleniumConstants.delete_order);// 删除数据
		prepareData(SeleniumConstants.insert_order);// 准备数据
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void orderMgr() throws Exception {
		entryPage("商户运营", "商城订单管理");
		everyStaOper("待支付", "待支付", "未消费", "");// 待支付界面
		everyStaOper("关闭订单", "订单取消", "未消费", SeleniumConstants.update_sql1);// 关闭订单界面
		everyStaOper("待消费", "待消费", "未消费", SeleniumConstants.update_sql2);// 待消费界面
		everyStaOper("成功订单", "交易成功", "未消费", SeleniumConstants.update_sql3);// 成功订单
		everyStaOper("待退款", "退款中", "未消费", SeleniumConstants.update_sql4);// 待退款
		everyStaOper("退款完成", "退款成功", "未消费", SeleniumConstants.update_sql5);// 退款完成
		everyStaOper("待发货", "待发货", "未消费", "");// 待发货
		everyStaOper("已发货", "已发货", "未消费", SeleniumConstants.update_sql2);// 已发货
		everyStaOper("全部订单", "交易成功", "未消费", SeleniumConstants.delete_order);// 全部订单查看
		resetTest("resetBtn", "searchBtn", "pages");// 重置
	}

	private void everyStaOper(String t1, String t2, String t3, String sta) throws Exception {
		dr.findElement(By.linkText(t1)).click();
		sleep(1500);
		dr.findElement(By.id("searchOrderNo")).sendKeys("59374cadee0c42d993a08dca3c111fef");
		sleep(1500);

		searchTest("searchBtn", "pages");
		seeDetail(t2, t3);
		if (t1 == "已发货") {
			assertEquals("顺丰", dr.findElement(By.id("expressCompanyInfo")).getAttribute("value"));
			assertEquals("2018012901", dr.findElement(By.id("expressNumberInfo")).getAttribute("value"));
		}
		closePrompt("deptUserInfo", 1);

		switch (t1) {
		case "待支付":
			new WebDriverWait(dr, 15).until(ExpectedConditions.elementToBeClickable(By.className("noty_text")));
			assertEquals("成功 取消订单成功", dr.findElement(By.className("noty_text")).getText());
			sleep(2000);
			break;
		case "待发货":
			dr.findElement(By.id("expressCompanyInfo")).sendKeys("顺丰");
			dr.findElement(By.id("expressNumberInfo")).sendKeys("2018012901");
			sleep(2000);

			closePrompt("deptUserInfo", 1);
			new WebDriverWait(dr, 15).until(ExpectedConditions.elementToBeClickable(By.className("noty_text")));
			assertEquals("成功 发货成功", dr.findElement(By.className("noty_text")).getText());
			sleep(2000);
			break;
		default:
			prepareData(sta);

			dr.findElement(By.id("searchBtn")).click();
			sleep(2000);
			break;
		}
		assertEquals("暂无数据", dr.findElement(By.xpath("//*[@id='mainContentList']//td")).getText());
	}

	protected void seeDetail(String os, String us) throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(3000);

		assertEquals("59374cadee0c42d993a08dca3c111fef", dr.findElement(By.id("orderIdInfo")).getAttribute("value"));
		assertEquals("嘉兴人保（金币商城）17121216501910047", dr.findElement(By.id("userNameInfo")).getAttribute("value"));
		assertEquals("15371022842", dr.findElement(By.id("mobileInfo")).getAttribute("value"));
		assertEquals("测试Test", dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		assertEquals(date, dr.findElement(By.id("createOrderTimeInfo")).getAttribute("value"));
		assertEquals(os, dr.findElement(By.id("orderStateInfo")).getAttribute("value"));
		assertEquals(us, dr.findElement(By.id("useStateInfo")).getAttribute("value"));
	}
}
