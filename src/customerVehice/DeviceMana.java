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
		checkNum();// 校验已绑定+未绑定=所有的数据
	}

	private void searchData() throws InterruptedException {
		removeReadonly("outStartTime");
		removeReadonly("outEndTime");
		clearInp("deviceId", "2017112101");
		clearInp("deviceSn", "2017112101");
		clearInp("outStartTime", date);
		clearInp("outEndTime", date);
		select("deviceStatus", "空闲", 1500);

		assertEquals("true", dr.findElement(By.id("lpno")).getAttribute("disabled"));
		assertEquals("true", dr.findElement(By.id("userType")).getAttribute("disabled"));

		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void deviceDetailAndHis() throws InterruptedException {
		clickEle("//*[@name='j-deviceDetail']", 2000);

		assertEquals("2017112101", dr.findElement(By.id("deviceId4Detail")).getText());
		assertEquals("万创控制盒", dr.findElement(By.id("deviceTypeName4Detail")).getText());
		assertEquals("空闲", dr.findElement(By.id("operationStatusDesc4Detail")).getText());

		closePrompt("deviceDetail", 1, 1500);
		clickEle("//*[@name='j-deviceHis']", 2000);

		assertEquals("警告 没有查询到操作历史记录", dr.findElement(By.className("noty_text")).getText());
	}

	private void deviceTrans() throws InterruptedException {
		clickEle("//*[@id='deviceTrans']", 2000);// 打开设备调拨的弹框
		chickAndAssert("deviceTransBox", 1, "警告 请先选择调入机构！");// 直接点击确定弹出提示框
		clickEle("//*[@id='transInDept']", 1000);
		clickEle("//ul[@id='treeTransIn']//li/ul/li[1]//span", 2000);// 选择调入机构
		chickAndAssert("deviceTransBox", 1, "警告 没有要调拨的数据！");// 提示要勾选调拨的数据
		clearInp("deviceId4Trans", "2017112101");
		clickEle("//*[@id='searchSubmit4Trans']", 2000);// 搜索要调拨的数据
		clickEle("//*[@id='device4TransListBody']//td[1]/input", 1000);// 勾选搜索出来的要调拨的数据
		chickAndAssert("deviceTransBox", 1, "确定要调拨本页已选择的设备？");// 点击确定弹出提示是否确定调拨已选择的设备
		clickEle("//div[@class='noty_buttons']/button[1]", 2000);// 再次确定

		assertEquals("成功 调拨成功！", dr.findElement(By.className("noty_text")).getText());// 提示调拨成功

		closePrompt("deviceTransBox", 2, 1500);// 点取消关闭弹框
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
	}

	private void checkNum() throws InterruptedException {
		entryPage("客户车辆", "设备管理");// 进入设备管理页面

		String text1 = dr.findElement(By.id("sumMessageId")).getText();
		String text2 = dr.findElement(By.id("pages")).getText();
		int real1 = Integer.valueOf(text1.split("，")[0].substring(5, text1.split("，")[0].length() - 1));
		int real2 = Integer.valueOf(text1.split("，")[1].substring(5, text1.split("，")[1].length() - 2));
		int real3 = Integer.valueOf(text2.substring(1, text2.length() - 3));

		assertEquals(true, real1 + real2 == real3);
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('" + outid + "').removeAttribute('readonly');");
	}

	protected void chickAndAssert(String did, int num, String text) throws InterruptedException {
		closePrompt(did, num, 2000);// 直接点确定按钮
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}

	private static void prepareData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}
}
