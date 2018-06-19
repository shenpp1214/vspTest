package manageCenter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class SysSetting extends BaseService {
	@BeforeClass
	public static void openBrowerSysSetting() throws Exception {
		openBrower("vsp_url");
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void sysSetting() throws InterruptedException {
		entryPage("管理中心", "系统设置");// 进入页面
		modifyInfo();// 修改信息+还原信息
	}

	private void modifyInfo() throws InterruptedException {
		String corpName = dr.findElement(By.id("corpName")).getAttribute("value");
		String provinceId = dr.findElement(By.id("provinceId")).getAttribute("value");
		String cityId = dr.findElement(By.id("cityId")).getAttribute("value");

		assertEquals(false, dr.findElement(By.id("corpDomain")).isEnabled());
		assertEquals(false, dr.findElement(By.id("industry")).isEnabled());
		assertEquals(false, dr.findElement(By.id("subindustry")).isEnabled());

		sendData("contactPerson", "*&12AS是的【】");
		sendData("phone", "1234567890");
		sendData("fax", "12345678901");
		sendData("mail", "798321482@qq.com");
		sendData("address", "*&12AS是的【】*&12AS是的【】");
		sendData("corpTitle", "*&12AS是的【】");
		sendData("note", "*&12AS是的【】");
		sendData("downloadURL", "http://58.215.50.61:22080/vsp/sysSetting.jsp");
		sendData("smsSuffix", "*&12AS是的【】");
		clickConfirm();
		assertInfo(corpName, provinceId, cityId);
		resetInfo(corpName, provinceId, cityId);
	}

	protected void assertInfo(String cn, String pi, String ci) throws InterruptedException {
		assertEquals(cn, dr.findElement(By.id("corpName")).getAttribute("value"));
		assertEquals(pi, dr.findElement(By.id("provinceId")).getAttribute("value"));
		assertEquals(ci, dr.findElement(By.id("cityId")).getAttribute("value"));
		assertEquals("*&12AS是的【】", dr.findElement(By.id("contactPerson")).getAttribute("value"));
		assertEquals("1234567890", dr.findElement(By.id("phone")).getAttribute("value"));
		assertEquals("12345678901", dr.findElement(By.id("fax")).getAttribute("value"));
		assertEquals("798321482@qq.com", dr.findElement(By.id("mail")).getAttribute("value"));
		assertEquals("*&12AS是的【】*&12AS是的【】", dr.findElement(By.id("address")).getAttribute("value"));
		assertEquals("*&12AS是的【】", dr.findElement(By.id("corpTitle")).getAttribute("value"));
		assertEquals("*&12AS是的【】", dr.findElement(By.id("note")).getAttribute("value"));
		assertEquals("http://58.215.50.61:22080/vsp/sysSetting.jsp",
				dr.findElement(By.id("downloadURL")).getAttribute("value"));
		assertEquals("*&12AS是的【】", dr.findElement(By.id("smsSuffix")).getAttribute("value"));
		sleep(2000);
	}

	protected void resetInfo(String cn, String pi, String ci) throws InterruptedException {
		clearData("contactPerson");
		clearData("phone");
		clearData("fax");
		clearData("mail");
		clearData("address");
		clearData("corpTitle");
		clearData("note");
		clearData("downloadURL");
		clearData("smsSuffix");
		clickConfirm();
		assertInfo1(cn, pi, ci);
	}

	protected void assertInfo1(String cn, String pi, String ci) throws InterruptedException {
		assertEquals("", dr.findElement(By.id("contactPerson")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("phone")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("fax")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("mail")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("address")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("corpTitle")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("note")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("downloadURL")).getAttribute("value"));
		assertEquals("", dr.findElement(By.id("smsSuffix")).getAttribute("value"));
		sleep(2000);
	}

	protected void clickConfirm() throws InterruptedException {
		dr.findElement(By.id("corpSaveBtn")).click();
		sleep(5000);
	}

	protected void sendData(String id, String data) throws InterruptedException {
		dr.findElement(By.id(id)).sendKeys(data);
		sleep(1000);
	}

	protected void clearData(String id) throws InterruptedException {
		dr.findElement(By.id(id)).clear();
		sleep(1000);
	}

	@After
	public void closeSysSetting() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}
}
