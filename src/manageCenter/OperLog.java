package manageCenter;

import java.util.Date;

import static org.junit.Assert.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class OperLog extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	String beDate = date + " 00:00:00";
	String endDate = date + " 23:59:59";

	@Test
	public void sysSetting() throws InterruptedException {
		entryPage("管理中心", "操作记录");// 进入页面
		searActiveMana();// 搜索活动管理类型
		searDeviceMana();// 搜索设备管理类型
		clickEle("//*[@id='resetBtn']", 1500);// 重置搜索条件
		clickEle("//*[@id='searchBtn']", 1500);// 搜索

		assertEquals(false, dr.findElement(By.id("pages")).isDisplayed());
	}

	private void searActiveMana() throws InterruptedException {
		assertEquals(beDate, dr.findElement(By.id("log_begintime")).getAttribute("value"));
		assertEquals(endDate, dr.findElement(By.id("log_endtime")).getAttribute("value"));

		clearInp("log_username", "admin@dina");
		select("log_sort", "活动管理类型", 1000);// 操作类型
		select("log_type", "全部", 1000);// 具体类型
		select("log_userrole", "总管理员", 1000);// 选择总管理员
		clickSearchBtn("admin@dina", "总管理员", "活动管理类型".substring(0, 4), date);// 搜索
	}

	private void searDeviceMana() throws InterruptedException {
		select("log_sort", "设备管理类型", 1000);// 操作类型
		select("log_type", "调拨设备", 1000);// 具体类型
		clickSearchBtn("admin@dina", "总管理员", "调拨设备", date);// 搜索

		String textbox = dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[5]")).getText();
		if (!textbox.contains("2017112101") || !textbox.contains("调拨"))
			fail();
	}

	protected void clickSearchBtn(String uname, String utype, String otype, String otime) throws InterruptedException {
		clickEle("//*[@id='searchBtn']", 3000);

		assertEquals(uname, dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[1]")).getText());
		assertEquals(utype, dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[2]")).getText());
		assertEquals(otype,
				dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[3]")).getText().substring(0, 4));
		assertEquals(otime,
				dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[4]")).getText().substring(0, 10));
	}
}
