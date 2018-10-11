package merchantOperate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		clickEle("//*[@id='addAccountBtn']", 1500);// 进入创建商户界面
		clearInp("spAccountAdd", "hans111");
		clearInp("spPassAdd", "000000");
		clearInp("spNameAdd", "菇凉de店铺");
		clearInp("servPhoneAdd", "17811110000");
		clearInp("spAddrAdd", "江苏省南京市南京南站");
		clickEle("//*[@id='spAddrBntAdd']", 1000);
		clearInp("geoAddress", "南京市雨花台区玉兰路98号");
		clickEle("//*[@id='geoAddressBtn']", 1500);
		clickEle("//*[@id='piccBusPointMarker']/table[1]//strong", 1000);
		closePrompt("piccBusPointMapPanel", 1, 1000);
		removeReadonly("spTimeStartAdd");
		removeReadonly("spTimeEndAdd");
		clearInp("spTimeStartAdd", "10:00");
		clearInp("spTimeEndAdd", "21:00");
		clearInp("spOpenBankAdd", "南京银行");
		clearInp("spOpenBankUserAdd", "张宪森");
		clearInp("spBankAccountAdd", "12345678901111122222");
		clearInp("serverRateAdd", "0.3");

		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("userphoto"));
		sleep(4000);
		closePrompt("addDeptUser", 1, 1000);
		new WebDriverWait(dr, 15).until(ExpectedConditions.elementToBeClickable(By.id("searchSpName")));
		sleep(2000);
	}

	private void searMerchant() throws InterruptedException {
		clearInp("searchSpName", "菇凉de店铺");
		clearInp("searchServPhone", "17811110000");
		searchTest("searchBtn", "pages");
	}

	private void modMerchant() throws InterruptedException {
		clickEle("//*[@name='j-editDepartUser']", 1500);
		clearInp("servPhoneEdit", "18011112222");
		closePrompt("editDeptUser", 1, 2000);

		assertEquals("成功 修改商户信息成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(false, isElementExsit(dr, "//*[@id='mainContentList']//td"));

		clearInp("searchServPhone", "18011112222");
		searchTest("searchBtn", "pages");
	}

	private void seeDetail() throws InterruptedException {
		clickEle("//*[@name='j-accountDetail']", 2000);

		assertEquals("hans111", dr.findElement(By.id("spAccountInfo")).getAttribute("value"));
		assertEquals("菇凉de店铺", dr.findElement(By.id("spNameInfo")).getAttribute("value"));
		assertEquals("18011112222", dr.findElement(By.id("servPhoneInfo")).getAttribute("value"));
		assertEquals("江苏省南京市南京南站", dr.findElement(By.id("spAddrInfo")).getAttribute("title"));
		assertEquals("南京银行", dr.findElement(By.id("spOpenBankInfo")).getAttribute("value"));
		assertEquals("张宪森", dr.findElement(By.id("spOpenBankUserInfo")).getAttribute("value"));
		assertEquals("12345678901111122222", dr.findElement(By.id("spBankAccountInfo")).getAttribute("value"));
		assertEquals("0.3", dr.findElement(By.id("spServerRateInfo")).getAttribute("value"));

		closePrompt("deptUserInfo", 1, 1500);
	}

	private void resetAndDel(String oname, String t1, String t2) throws InterruptedException {
		clickEle("//*[@name='" + oname + "']", 2000);
		assertEquals(t1, dr.findElement(By.className("noty_text")).getText());
		clickEle("//div[@class='noty_buttons']/button[1]", 2000);
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
