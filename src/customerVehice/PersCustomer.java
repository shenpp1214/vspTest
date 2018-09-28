package customerVehice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class PersCustomer extends BaseService {

	@BeforeClass
	public static void openBrowerPersCustomer() throws Exception {
		openBrower("vsp_url");// 打开浏览器最大化
		prepareData(SeleniumConstants.delsql);// 准备数据
		prepareData(SeleniumConstants.insertsql);// 准备数据
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
		assertEquals("个人客户", dr.findElement(By.xpath("//a[@href='./persCustomer.jsp']")).getText());// 校验是否登录成功
	}

	private static void prepareData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}

	@Test
	public void individualCus() throws Exception {
		searchData();// 搜索测试
		openAndHide();// 展开隐藏搜索区域
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
		flip();// 翻页
	}

	private void searchData() throws InterruptedException {
		dr.findElement(By.id("searchUserName")).sendKeys("shenpp");
		dr.findElement(By.id("searchName")).sendKeys("shenpp");
		dr.findElement(By.id("searchPhone")).sendKeys("15951901290");
		dr.findElement(By.id("searchEmail")).sendKeys("779230186@qq.com");

		select("searchUserSex", "女");
		select("searchOwnerType", "VSP");
		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void openAndHide() throws InterruptedException {
		openHide(false);// 隐藏搜索
		openHide(true);// 展开搜索
	}

	protected void setPoints(String text) throws InterruptedException {
		dr.findElement(By.id("changePoints")).sendKeys("200");
		closePrompt("bonusSetPanel", 1);
		assertEquals("警告 备注不能为空", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.id("changePointsRemark")).sendKeys(text);
		closePrompt("bonusSetPanel", 1);
		assertEquals("成功 用户红利设置成功", dr.findElement(By.className("noty_text")).getText());
	}

}
