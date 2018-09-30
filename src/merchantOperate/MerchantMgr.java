package merchantOperate;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class MerchantMgr extends BaseService {

	private void modSql(String a) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", a);
	}

	@Test
	public void merchantMgr() throws Exception {
		modSql(SeleniumConstants.del_mer);
		modSql(SeleniumConstants.del_u);
		entryPage("商户运营", "商城商户管理");
		addMerchant();// 添加商户成功
		searMerchant();// 搜索新增的商户
		modMerchant();// 修改商户信息
		seeDetail();// 查看详情
		resetAndDel("j-resetMerchantPass", "是否将菇凉de店铺的密码重置为初始密码？", "成功 密码重置成功");// 重置密码
		resetAndDel("j-deleteDepartUser", "是否决定删除菇凉de店铺商户?", "成功 删除商户成功");// 删除新建的商户
	}

	private void addMerchant() throws InterruptedException {
		dr.findElement(By.id("addAccountBtn")).click();
		sleep(2000);// 进入创建商户界面
		dr.findElement(By.id("spAccountAdd")).sendKeys("hans111");
		dr.findElement(By.id("spPassAdd")).sendKeys("000000");
		dr.findElement(By.id("spNameAdd")).sendKeys("菇凉de店铺");
		dr.findElement(By.id("servPhoneAdd")).sendKeys("17811110000");
		dr.findElement(By.id("spAddrAdd")).sendKeys("江苏省南京市南京南站");
		sleep(2000);
		dr.findElement(By.id("spAddrBntAdd")).click();
		sleep(2000);
		dr.findElement(By.id("geoAddress")).sendKeys("南京市雨花台区玉兰路98号");
		dr.findElement(By.id("geoAddressBtn")).click();
		sleep(2000);
		dr.findElement(By.xpath("//*[@id='piccBusPointMarker']/table[1]//strong")).click();
		sleep(2000);

		closePrompt("piccBusPointMapPanel", 1);
		removeReadonly("spTimeStartAdd");
		removeReadonly("spTimeEndAdd");

		dr.findElement(By.id("spTimeStartAdd")).sendKeys("10:00");
		dr.findElement(By.id("spTimeEndAdd")).sendKeys("21:00");
		dr.findElement(By.id("spOpenBankAdd")).sendKeys("南京银行");
		dr.findElement(By.id("spOpenBankUserAdd")).sendKeys("张宪森");
		dr.findElement(By.id("spBankAccountAdd")).sendKeys("12345678901111122222");
		dr.findElement(By.id("serverRateAdd")).sendKeys("0.3");
		sleep(2000);
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("userphoto"));
		sleep(4000);
		closePrompt("addDeptUser", 1);
		sleep(5000);
	}

	private void searMerchant() throws InterruptedException {
		dr.findElement(By.id("searchSpName")).sendKeys("菇凉de店铺");
		dr.findElement(By.id("searchServPhone")).sendKeys("17811110000");
		sleep(1500);

		searchTest("searchBtn", "pages");
	}

	private void modMerchant() throws InterruptedException {
		dr.findElement(By.name("j-editDepartUser")).click();
		sleep(2000);
		dr.findElement(By.id("servPhoneEdit")).clear();
		dr.findElement(By.id("servPhoneEdit")).sendKeys("18011112222");
		sleep(1500);

		closePrompt("editDeptUser", 1);
		assertEquals("成功 修改商户信息成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(false, isElementExsit(dr, "//*[@id='mainContentList']//td"));

		dr.findElement(By.id("searchServPhone")).clear();
		dr.findElement(By.id("searchServPhone")).sendKeys("18011112222");
		sleep(1500);

		searchTest("searchBtn", "pages");
	}

	private void seeDetail() throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(3000);

		assertEquals("hans111", dr.findElement(By.id("spAccountInfo")).getAttribute("value"));
		assertEquals("菇凉de店铺", dr.findElement(By.id("spNameInfo")).getAttribute("value"));
		assertEquals("18011112222", dr.findElement(By.id("servPhoneInfo")).getAttribute("value"));
		assertEquals("江苏省南京市南京南站", dr.findElement(By.id("spAddrInfo")).getAttribute("title"));
		assertEquals("南京银行", dr.findElement(By.id("spOpenBankInfo")).getAttribute("value"));
		assertEquals("张宪森", dr.findElement(By.id("spOpenBankUserInfo")).getAttribute("value"));
		assertEquals("12345678901111122222", dr.findElement(By.id("spBankAccountInfo")).getAttribute("value"));
		assertEquals("0.3", dr.findElement(By.id("spServerRateInfo")).getAttribute("value"));

		closePrompt("deptUserInfo", 1);
	}

	private void resetAndDel(String oname, String t1, String t2) throws InterruptedException {
		dr.findElement(By.name(oname)).click();
		sleep(2000);
		assertEquals(t1, dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);
		assertEquals(t2, dr.findElement(By.className("noty_text")).getText());

		if (oname == "j-deleteDepartUser") {
			assertEquals(false, isElementExsit(dr, "//*[@id='mainContentList']//td"));
			resetTest("resetBtn", "searchBtn", "pages");
		}
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('" + outid + "').removeAttribute('readonly');");
	}
}
