package jsrbActivityMana;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class MarketPosition4App extends BaseService {

	@Test
	public void marketPosition() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_marpos);
		entryPage("运营中心", "营销位管理");
		newMarketPos();// 新增停用的营销位
		editMarketPos();// 编辑(1.校验状态不可编辑 2.更改名称保存)
		operData("启用", "//div[@class='noty_buttons']/button[1]", "成功 启用成功");// 启用
		sortTest();// 排序到第一位
		operData("停用", "//div[@class='noty_buttons']/button[1]", "成功 停用成功");// 停用
		operData("删除", "//div[@class='noty_buttons']/button[1]", "成功 删除成功");// 删除
	}

	private void newMarketPos() throws InterruptedException {
		clickEle("//*[@id='addCategoryBtn']", 1500);// 点击新增按钮
		clearInp("categoryNameAdd", "唤颜面膜");// 输入名称
		select("categoryStatusAdd", "停用", 1000);
		clearInp("linkUrlAdd", "http://www.kiehls.com.cn/");// 输入链接地址

		dr.findElement(By.xpath("//div[@id='addPanel']/table/tbody/tr[2]//input")).sendKeys(getTemplatePath("candy2"));// 上传图片
		new WebDriverWait(dr, 15).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@id='addPanel']/following-sibling::div//button[1]")));
		sleep(1500);
		closePrompt("addPanel", 1, 2000);// 点击确定按钮

		assertEquals("成功 创建营销位成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void editMarketPos() throws InterruptedException {
		clickEle("//td[text()='唤颜面膜']/following-sibling::td/a[2]", 1500);// 点击编辑按钮
		assertEquals(false, dr.findElement(By.id("categoryStatusEdit")).isEnabled());
		assertEquals("0", dr.findElement(By.id("categoryStatusEdit")).getAttribute("value"));

		clearInp("categoryNameEdit", "唤颜面膜oaz");
		closePrompt("editPanel", 1, 2000);
		assertEquals("成功 编辑营销位成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(true, isElementExsit(dr, "//td[text()='唤颜面膜oaz']"));

		select("marketPositionType4Search", "链接发布", 1000);
		clearInp("marketPositionName4Search", "唤颜面膜oaz");
		clickEle("//*[@id='searchBtn']", 1000);
	}

	private void operData(String t0, String x, String t) throws InterruptedException {
		clickEle("//td[text()='唤颜面膜oaz']/following-sibling::td/a[text()='" + t0 + "']", 1500);// 点击启用按钮
		if (t0 == "排序")
			clearInp("categorySort", "1");// 清空重新输入排序1
		clickEle(x, 1500);// 点击确定按钮

		assertEquals(t, dr.findElement(By.className("noty_text")).getText());
	}

	private void sortTest() throws InterruptedException {
		clickEle("//*[@id='resetBtn']", 1000);// 重置搜索条件
		clickEle("//*[@id='searchBtn']", 1500);// 搜索
		operData("排序", "//div[@id='sortPanel']/following-sibling::div//button[1]", "成功 排序成功");

		assertEquals("唤颜面膜oaz", dr.findElement(By.xpath("//div[@id='mainContentList']//tr[1]/td[3]")).getText());
	}

	@After
	public void logoutMarketPos() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}

}
