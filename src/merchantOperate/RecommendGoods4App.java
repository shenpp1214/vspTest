package merchantOperate;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class RecommendGoods4App extends BaseService {
	protected static void prepareData(String pd) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", pd);
	}

	@Test
	public void RecGoods() throws Exception {
		prepareData(SeleniumConstants.del_mer);
		prepareData(SeleniumConstants.del_gc);
		prepareData(SeleniumConstants.del_goods);
		prepareData(SeleniumConstants.insert_mer);
		prepareData(SeleniumConstants.insert_gc);
		prepareData(SeleniumConstants.insert_goods);
		entryPage("商户运营", "推荐商品管理");
		newRecGoods();// 新建停用的推荐商品
		operateTest("编辑", "编辑推荐商品");// 编辑停用的商品
		operateTest("启用", "启用");// 启用
		operateTest("排序", "排序");// 排序
		operateTest("停用", "停用");// 停用
		operateTest("删除", "删除");// 删除
		serRest();// 搜索+重置
	}

	private void newRecGoods() throws InterruptedException {
		dr.findElement(By.id("addCategoryBtn")).click();// 点击新建按钮
		sleep(1500);
		dr.findElement(By.id("categoryNameAdd")).sendKeys("每天都要开心");// 输入名称
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("candy2"));// 上传图片
		sleep(3500);
		dr.findElement(By.id("chooseGoodBtnAdd")).click();// 点击选择商品
		sleep(1500);

		searchGoods("searchGoodsName", "开心糖果", "searchBtn4Goods", "pages4Good");// 搜索商品

		dr.findElement(By.name("j-choose")).click();// 点击选择按钮
		sleep(1500);

		select("categoryStatusAdd", "停用");// 选择停用
		closePrompt("addPanel", 1);// 点击确定按钮

		assertEquals("成功 创建成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void operateTest(String n, String t) throws InterruptedException {
		String page = dr.findElement(By.id("pages")).getText();
		int p = Integer.valueOf(page.substring(1, page.length() - 3));
		for (int i = 2; i <= p; i++) {
			if (!isElementExsit(dr, "//td[text()='每天都要开心']/following-sibling::td"))
				select("pageSelect", String.valueOf(i));
			else
				break;
		} // 判断新增数据在第几页

		dr.findElement(By.xpath("//td[text()='每天都要开心']/following-sibling::td/a[text()='" + n + "']")).click();// 点击编辑按钮
		sleep(1500);
		if (n.equals("编辑")) {
			closePrompt("editPanel", 1);
		} else if (n.equals("排序")) {
			dr.findElement(By.id("categorySort")).clear();
			dr.findElement(By.id("categorySort")).sendKeys("1");

			sleep(2000);
			closePrompt("sortPanel", 1);

			assertEquals("每天都要开心", dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[3]")).getText());
			assertEquals("开心糖果", dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[4]")).getText());
			assertEquals("菇凉de店铺", dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[5]")).getText());
		} else {
			dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
			sleep(2000);
		}
		assertEquals("成功 " + t + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void serRest() throws InterruptedException {
		String name = dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[3]")).getText();

		searchGoods("goodsName4Search", name, "searchBtn", "pages");
		resetTest("resetBtn", "searchBtn", "pages");
	}

	private void searchGoods(String id1, String t1, String id2, String id3) throws InterruptedException {
		dr.findElement(By.id(id1)).sendKeys(t1);
		sleep(1500);
		dr.findElement(By.id(id2)).click();
		sleep(2000);

		assertEquals("共1条数据", dr.findElement(By.id(id3)).getText());
	}

	@After
	public void logoutOperatorCategory() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}
}
