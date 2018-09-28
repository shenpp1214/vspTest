package jsrbActivityMana;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class DianOrderMgr extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	public static void modifyData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}

	@Test
	public void dianOrderMgr() throws Exception {
		modifyData(SeleniumConstants.del_vioOrder);
		modifyData(SeleniumConstants.insert_vioOrder);

		searchDatas();
		String tf = dr.findElement(By.id("totalFine")).getText();
		String tsf = dr.findElement(By.id("totalServiceFee")).getText();
		List<WebElement> elements1 = dr.findElements(By.xpath("//*[@id=\"mainContentList\"]/table[1]/tbody/tr/td[6]"));
		List<WebElement> elements2 = dr.findElements(By.xpath("//*[@id=\"mainContentList\"]/table[1]/tbody/tr/td[7]"));
		int allfine = 0, allserfee = 0;
		for (WebElement e : elements1) {
			int f = Integer.valueOf(e.getText());
			allfine += f;
		}
		for (WebElement e : elements2) {
			int sf = Integer.valueOf(e.getText());
			allserfee += sf;
		}
		if (allfine != Integer.valueOf(tf.substring(0, tf.length() - 1))
				|| allserfee != Integer.valueOf(tsf.substring(0, tsf.length() - 1))) {
			fail();
		} else {
			System.out.println(allfine + "," + Integer.valueOf(tf.substring(0, tf.length() - 1)));
			System.out.println(allserfee + "," + Integer.valueOf(tsf.substring(0, tsf.length() - 1)));
		}

		searchOrder("待支付");// 搜索待支付的订单
		modifyData(SeleniumConstants.update_vioOrder101);// 更改订单状态为处理中
		searchOrder("处理中");// 搜索处理中的订单
		closeOrder();// 关闭订单
		searchOrder("关闭");// 搜索关闭订单
		seeDetail("关闭");// 查看订单详情
		modifyData(SeleniumConstants.update_vioOrder102);// 更改订单状态为异常
		searchOrder("异常订单");// 搜索异常订单
		seeDetail("订单异常 支付成功，典典处理失败");// 查看异常订单详情
		modifyData(SeleniumConstants.update_vioOrder200);// 更改订单状态为完成
		searchOrder("已完成");// 搜索已完成订单
		seeDetail("完成");// 查看已完成订单详情
	}

	private void searchDatas() throws InterruptedException {
		entryPage("运营中心", "违章订单管理");
		dr.findElement(By.id("searchUserName")).sendKeys("spp");
		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);
	}

	private void searchOrder(String orderSta) throws Exception {
		dr.findElement(By.linkText(orderSta)).click();
		sleep(1000);
		if (dr.findElement(By.id("searchOrderNo")).getAttribute("value").equals("")) {
			dr.findElement(By.id("searchOrderNo")).sendKeys("c86cef2c4148424799181af219a88247");
			dr.findElement(By.id("searchMobile")).sendKeys("18333333333");
			sleep(1500);
			removeReadonly("searchOrderTimeBegin");
			removeReadonly("searchOrderTimeEnd");
			dr.findElement(By.id("searchOrderTimeBegin")).sendKeys(date + " 00:00:00");
			dr.findElement(By.id("searchOrderTimeEnd")).sendKeys(date + " 23:59:59");
			select("searchChanneSel", "车行者");
			sleep(1500);
			dr.findElement(By.id("searchBtn")).click();// 点击搜索按钮
			sleep(1500);
		}

		assertEquals("共1条数据", dr.findElement(By.id("pages")).getText());
		assertEquals("50元", dr.findElement(By.id("totalFine")).getText());
		assertEquals("23元", dr.findElement(By.id("totalServiceFee")).getText());
		assertEquals("50", dr.findElement(By.xpath("//div[@id='mainContentList']/table[1]/tbody//td[6]")).getText());
		assertEquals("23", dr.findElement(By.xpath("//div[@id='mainContentList']/table[1]/tbody//td[7]")).getText());
	}

	private void closeOrder() throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(1500);
		dr.findElement(By.id("remarkInfo")).sendKeys("关闭订单");
		dr.findElement(By.xpath("//span[text()='关闭订单']")).click();// 点击关闭订单按钮
		sleep(1500);
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();// 点击确定按钮
		sleep(2000);

		assertEquals("成功 修改成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void seeDetail(String staInfo) throws Exception {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(1500);

		if (isElementExsit(dr, "//textarea[@id='remarkInfo']")) {
			assertEquals("关闭订单", dr.findElement(By.id("remarkInfo")).getAttribute("value"));
		} else {
			assertEquals(staInfo, dr.findElement(By.id("orderStatusInfo")).getAttribute("value"));
		}

		assertEquals("c86cef2c4148424799181af219a88247", dr.findElement(By.id("orderIdInfo")).getAttribute("value"));
		assertEquals("车行者", dr.findElement(By.id("channelNameInfo")).getAttribute("value"));

		dr.findElement(By.xpath("//div[@id='deptOrderInfo']/following-sibling::div[9]//button")).click();// 点击取消按钮关闭弹框
		sleep(2000);
	}

	protected void removeReadonly(String outid) {
		// 去除文本框的只读属性
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('" + outid + "').removeAttribute('readonly');");
	}
}
