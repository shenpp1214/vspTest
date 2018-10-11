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
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
	}

	private void searchData() throws InterruptedException {
		clearInp("searchUserName", "shenpp");
		clearInp("searchName", "shenpp");
		clearInp("searchPhone", "15951901290");
		clearInp("searchEmail", "779230186@qq.com");
		select("searchUserSex", "女", 1800);
		select("searchOwnerType", "VSP", 1800);
		searchTest("searchBtn", "pages");// 搜索测试
	}

	protected void setPoints(String text) throws InterruptedException {
		clearInp("changePoints", "200");
		closePrompt("bonusSetPanel", 1, 1500);
		assertEquals("警告 备注不能为空", dr.findElement(By.className("noty_text")).getText());

		clearInp("changePointsRemark", text);
		closePrompt("bonusSetPanel", 1, 2000);
		assertEquals("成功 用户红利设置成功", dr.findElement(By.className("noty_text")).getText());
	}
}
