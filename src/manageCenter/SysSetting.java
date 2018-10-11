package manageCenter;

import static org.junit.Assert.*;

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

		clearInp("contactPerson", "#1sS是");
		clearInp("phone", "1234567890");
		clearInp("fax", "12345678901");
		clearInp("mail", "a@w.c");
		clearInp("address", "#1sS是");
		clearInp("corpTitle", "#1sS是");
		clearInp("note", "#1sS是");
		clearInp("downloadURL", "http://www.baidu.com");
		clearInp("smsSuffix", "#1sS是");
		clickEle("//*[@id='corpSaveBtn']", 3000);
		clickEle("//*[@id='popup_ok']", 3000);
		assertInfo(corpName, provinceId, cityId, "#1sS是", "1234567890", "12345678901", "a@w.c", "#1sS是", "#1sS是",
				"#1sS是", "http://www.baidu.com", "#1sS是");
		resetInfo(corpName, provinceId, cityId);
	}

	protected void resetInfo(String cn, String pi, String ci) throws InterruptedException {
		clearInp("contactPerson", "");
		clearInp("phone", "");
		clearInp("fax", "");
		clearInp("mail", "");
		clearInp("address", "");
		clearInp("corpTitle", "");
		clearInp("note", "");
		clearInp("downloadURL", "");
		clearInp("smsSuffix", "");
		clickEle("//*[@id='corpSaveBtn']", 3000);
		clickEle("//*[@id='popup_ok']", 3000);
		assertInfo(cn, pi, ci, "", "", "", "", "", "", "", "", "");
	}

	protected void assertInfo(String cn, String pi, String ci, String a, String b, String c, String d, String e,
			String f, String g, String h, String i) throws InterruptedException {
		assertEquals(cn, dr.findElement(By.id("corpName")).getAttribute("value"));
		assertEquals(pi, dr.findElement(By.id("provinceId")).getAttribute("value"));
		assertEquals(ci, dr.findElement(By.id("cityId")).getAttribute("value"));
		assertEquals(a, dr.findElement(By.id("contactPerson")).getAttribute("value"));
		assertEquals(b, dr.findElement(By.id("phone")).getAttribute("value"));
		assertEquals(c, dr.findElement(By.id("fax")).getAttribute("value"));
		assertEquals(d, dr.findElement(By.id("mail")).getAttribute("value"));
		assertEquals(e, dr.findElement(By.id("address")).getAttribute("value"));
		assertEquals(f, dr.findElement(By.id("corpTitle")).getAttribute("value"));
		assertEquals(g, dr.findElement(By.id("note")).getAttribute("value"));
		assertEquals(h, dr.findElement(By.id("downloadURL")).getAttribute("value"));
		assertEquals(i, dr.findElement(By.id("smsSuffix")).getAttribute("value"));
	}
}
