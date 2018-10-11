package manageCenter;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;

public class ServiceProvider extends BaseService {
	@BeforeClass
	public static void openBrowerSysSetting() throws Exception {
		openBrower("vsp_url");
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void serviceProvider() throws InterruptedException {
		entryPage("管理中心", "服务商管理");
		newPOI();// 新增poi商家
		searchPOI();// 搜索校验新增数据
		editPOI();// 编辑poi商家
		seeDetail();// 查看商家详情并校验相关数据
		delPOi();// 删除新创建的POI商家
		openExpandSer();// 展开收起搜索条件校验
	}

	private void newPOI() throws InterruptedException {
		clickEle("//*[@id='addAccountBtn']", 2000);// 点击创建POI商家按钮
		clearInp("spNameAdd", "#￥21We是的");
		clearInp("servPhoneAdd", "13181112220");
		clearInp("spInfoAdd", "一切以工作为主");// 输入商家名称+服务电话+商家详情
		select("spTypeAdd", "汽车救援", 1500);// 选择服务类型
		mapSelect();// 地图选取地址
		closePrompt("addDeptUser", 1, 2000);// 点击新增框下面的确定按钮

		assertEquals("警告 商家名称不能含有非法字符!", dr.findElement(By.className("noty_text")).getText());

		clearInp("spNameAdd", "21We是的");
		closePrompt("addDeptUser", 1, 2000);

		assertEquals("成功 添加服务商成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void searchPOI() throws InterruptedException {
		clearInp("searchSpName", "21We是的");
		clearInp("searchServPhone", "13181112220");
		select("searchSpTypeList", "汽车救援", 1000);
		select("searchStateList", "待审核 ", 1000);
		clickEle("//*[@id='searchBtn']", 2500);

		assertEquals("共1条数据", dr.findElement(By.id("pages")).getText());
		assertEquals("21We是的", dr.findElement(By.xpath("//*[@id='mainContentList']//tr/td[2]")).getText());
		assertEquals("汽车救援", dr.findElement(By.xpath("//*[@id='mainContentList']//tr/td[3]")).getText());
		assertEquals("13181112220", dr.findElement(By.xpath("//*[@id='mainContentList']//tr/td[6]")).getText());
		assertEquals("运营商VSP", dr.findElement(By.xpath("//*[@id='mainContentList']//tr/td[9]")).getText());
	}

	private void editPOI() throws InterruptedException {
		clickEle("//*[@name='j-editDepartUser']", 2000);
		uploadPng("uploadLogoDivEdit", "candy1");
		uploadPng("uploadSpPicDivEdit", "candy2");
		closePrompt("editDeptUser", 1, 2000);

		assertEquals("成功 修改服务商信息成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void seeDetail() throws InterruptedException {
		String addr = dr.findElement(By.xpath("//*[@id='mainContentList']//tr/td[7]")).getText();

		clickEle("//*[@name='j-accountDetail']", 3000);
		assertEquals("21We是的", dr.findElement(By.id("spNameInfo")).getAttribute("value"));
		assertEquals("13181112220", dr.findElement(By.id("servPhoneInfo")).getAttribute("value"));
		assertEquals("一切以工作为主", dr.findElement(By.id("spInfoInfo")).getAttribute("value"));
		assertEquals(addr, dr.findElement(By.id("spAddrInfo")).getAttribute("value"));
		assertEquals("服务商详情", dr.findElement(By.id("ui-id-2")).getText());
		assertEquals("商家基本信息", dr.findElement(By.xpath("//div[@id='deptUserInfo']/h3")).getText());
		closePrompt("deptUserInfo", 1, 1500);// 关闭详情对话框
	}

	private void delPOi() throws InterruptedException {
		clickEle("//*[@name='j-deleteDepartUser']", 1500);
		assertEquals("是否决定删除21We是的服务商?", dr.findElement(By.className("noty_text")).getText());

		clickEle("//div[@class='noty_buttons']/button[1]", 2000);
		assertEquals("成功 删除服务商成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(false, dr.findElement(By.id("pages")).isDisplayed());
		resetTest("resetBtn", "searchBtn", "pages");
	}

	private void openExpandSer() throws InterruptedException {
		clickEle("//div[@id='searchPanel']/following-sibling::div/span", 1500);
		assertEquals(false, dr.findElement(By.id("searchPanel")).isDisplayed());

		clickEle("//div[@id='searchPanel']/following-sibling::div/span", 1500);
		assertEquals(true, dr.findElement(By.id("searchPanel")).isDisplayed());
	}

	protected void mapSelect() throws InterruptedException {
		clickEle("//*[@id='spAddrBntAdd']", 1000);
		clearInp("geoAddress", "江苏省南京市秦淮区弓箭坊40号");// 输入地址
		clickEle("//*[@id='geoAddressBtn']", 1500);// 点击搜索
		clickEle("//div[@id='piccBusPointMarker']/table[1]//tr[2]/td[2]", 1500);// 选取搜索出来的第一个地址
		closePrompt("piccBusPointMapPanel", 1, 2000);// 点击地图选取中的确定按钮
	}

	protected void uploadPng(String pid, String file) throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='" + pid + "']//div/div//input")).sendKeys(getTemplatePath(file));
		new WebDriverWait(dr, 15).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@id='editDeptUser']/following-sibling::div//button[1]")));
		sleep(1500);
	}

	@After
	public void logoutServiceProvider() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}

}
