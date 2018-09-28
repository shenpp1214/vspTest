package customerVehice;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class DeviceMana extends BaseService {

	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	private static void prepareData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}

	@Test
	public void deviceMana() throws Exception {
		prepareData(SeleniumConstants.sql3);// 删除数据
		prepareData(SeleniumConstants.sql4);// 删除数据
		prepareData(SeleniumConstants.sql1);// 准备数据插入
		prepareData(SeleniumConstants.sql2);// 准备数据插入

		entryPage("客户车辆", "设备管理");// 进入设备管理页面
		searchData();// 搜索新增的数据
		deviceDetailAndHis();// 查看设备详情+操作历史
		deviceTrans();// 设备调拨
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
		checkNum();// 校验已绑定+未绑定=所有的数据
	}

	private void searchData() throws InterruptedException {
		removeReadonly("outStartTime");
		removeReadonly("outEndTime");

		dr.findElement(By.id("deviceId")).sendKeys("2017112101");
		dr.findElement(By.id("deviceSn")).sendKeys("2017112101");
		dr.findElement(By.id("outStartTime")).sendKeys(date);
		dr.findElement(By.id("outEndTime")).sendKeys(date);

		select("deviceStatus", "空闲");
		assertEquals("true", dr.findElement(By.id("lpno")).getAttribute("disabled"));
		assertEquals("true", dr.findElement(By.id("userType")).getAttribute("disabled"));
		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void deviceDetailAndHis() throws InterruptedException {
		clickByName("j-deviceDetail");
		assertEquals("2017112101", dr.findElement(By.id("deviceId4Detail")).getText());
		assertEquals("万创控制盒", dr.findElement(By.id("deviceTypeName4Detail")).getText());
		assertEquals("空闲", dr.findElement(By.id("operationStatusDesc4Detail")).getText());
		closePrompt("deviceDetail", 1);
		clickByName("j-deviceHis");
		assertEquals("警告 没有查询到操作历史记录", dr.findElement(By.className("noty_text")).getText());
	}

	private void deviceTrans() throws InterruptedException {
		dr.findElement(By.id("deviceTrans")).click();// 打开设备调拨的弹框
		sleep(2000);
		chickAndAssert("deviceTransBox", 1, "警告 请先选择调入机构！");// 直接点击确定弹出提示框
		dr.findElement(By.id("transInDept")).click();
		dr.findElement(By.xpath("//input[@id='transInDept']/following-sibling::div//li/ul//a/span")).click();// 选择调入机构
		sleep(2000);
		chickAndAssert("deviceTransBox", 1, "警告 没有要调拨的数据！");// 提示要勾选调拨的数据
		dr.findElement(By.id("deviceId4Trans")).sendKeys("2017112101");
		dr.findElement(By.id("searchSubmit4Trans")).click();// 搜索要调拨的数据
		sleep(2000);
		dr.findElement(By.xpath("//*[@id='device4TransListBody']/tr/td[1]/input")).click();// 勾选搜索出来的要调拨的数据
		chickAndAssert("deviceTransBox", 1, "确定要调拨本页已选择的设备？");// 点击确定弹出提示是否确定调拨已选择的设备
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();// 再次确定
		sleep(2000);

		assertEquals("成功 调拨成功！", dr.findElement(By.className("noty_text")).getText());// 提示调拨成功
		closePrompt("deviceTransBox", 2);// 点取消关闭弹框
	}

	private void checkNum() throws InterruptedException {
		entryPage("客户车辆", "设备管理");// 进入设备管理页面

		String text1 = dr.findElement(By.id("sumMessageId")).getText();
		String text2 = dr.findElement(By.id("pages")).getText();
		int real1 = Integer.valueOf(text1.split("，")[0].substring(5, text1.split("，")[0].length() - 1));
		int real2 = Integer.valueOf(text1.split("，")[1].substring(5, text1.split("，")[1].length() - 2));
		int real3 = Integer.valueOf(text2.substring(1, text2.length() - 3));

		if ((real3 - real1 - real2) != 0)
			fail();
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}

	protected void clickByName(String name) throws InterruptedException {
		dr.findElement(By.name(name)).click();
		sleep(2000);
	}

	protected void chickAndAssert(String did, int num, String text) throws InterruptedException {
		closePrompt(did, num);// 直接点确定按钮
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}
}
