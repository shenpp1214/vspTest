package jsrbActivityMana;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class MarketPosition4App extends BaseService {

	@Test
	public void marketPosition() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_marpos);

		entryPage("运营中心", "营销位管理");
		newMarketPos();// 新增营销位
		sortTest();// 排序到第一位
		editMarketPos();// 编辑(1.校验状态不可编辑 2.更改名称保存)
		disEnabled("启用", "成功 停用成功");// 搜索+停用
		disEnabled("停用", "成功 启用成功");// 搜索+启用
		delMarketPos();// 停用重置+删除
	}

	private void newMarketPos() throws InterruptedException {
		dr.findElement(By.id("addCategoryBtn")).click();// 点击新增按钮
		sleep(1500);
		dr.findElement(By.id("categoryNameAdd")).sendKeys("唤颜面膜");// 输入名称
		dr.findElement(By.id("linkUrlAdd")).sendKeys("http://www.kiehls.com.cn/");// 输入链接地址
		dr.findElement(By.xpath("//div[@id='addPanel']/table/tbody/tr[2]//input")).sendKeys(getTemplatePath("candy2"));// 上传图片
		sleep(5000);
		dr.findElement(By.xpath("//div[@id='addPanel']/following-sibling::div//button[1]")).click();// 点击确定按钮
		sleep(2000);

		assertEquals("成功 创建营销位成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void sortTest() throws InterruptedException {
		dr.findElement(By.xpath("//td[text()='唤颜面膜']/following-sibling::td/a[3]")).click();// 点击排序按钮
		sleep(1500);
		dr.findElement(By.id("categorySort")).clear();// 清空排序数字
		dr.findElement(By.id("categorySort")).sendKeys("1");// 重新输入排序1
		sleep(1500);
		dr.findElement(By.xpath("//div[@id='sortPanel']/following-sibling::div//button[1]")).click();// 点击确定按钮
		sleep(2000);

		assertEquals("成功 排序成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("唤颜面膜", dr.findElement(By.xpath("//div[@id='mainContentList']//tr[1]/td[3]")).getText());
	}

	private void editMarketPos() throws InterruptedException {
		dr.findElement(By.xpath("//td[text()='唤颜面膜']/following-sibling::td/a[2]")).click();// 点击编辑按钮
		sleep(1500);

		if (dr.findElement(By.id("categoryStatusEdit")).isEnabled() == true)
			fail();
		else
			dr.findElement(By.id("categoryNameEdit")).sendKeys("oaz");
		dr.findElement(By.xpath("//div[@id='editPanel']/following-sibling::div//button[1]")).click();
		sleep(2000);

		assertEquals("成功 编辑营销位成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("唤颜面膜oaz", dr.findElement(By.xpath("//div[@id='mainContentList']//tr[1]/td[3]")).getText());

		select("marketPositionType4Search", "链接发布");

		dr.findElement(By.id("marketPositionName4Search")).sendKeys("唤颜面膜oaz");
		sleep(1500);
	}

	private void disEnabled(String state, String text) throws InterruptedException {
		select("marketPositionStatus4Search", state);

		dr.findElement(By.id("searchBtn")).click();
		sleep(2000);
		dr.findElement(By.name("j-open")).click();// 点击停用
		sleep(1500);
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();// 点击确定
		sleep(2000);

		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
		assertEquals("没有查询到数据", dr.findElement(By.xpath("//div[@id='mainContentList']//tr/td")).getText());
	}

	private void delMarketPos() throws InterruptedException {
		disEnabled("启用", "成功 停用成功");// 搜索+停用

		dr.findElement(By.id("resetBtn")).click();// 点击重置按钮
		dr.findElement(By.id("searchBtn")).click();// 点击搜索按钮
		sleep(2000);
		dr.findElement(By.xpath("//td[text()='唤颜面膜oaz']/following-sibling::td/a[3]")).click();// 点击删除按钮
		sleep(1500);
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();// 点击确定
		sleep(2000);

		assertEquals("成功 删除成功", dr.findElement(By.className("noty_text")).getText());
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
