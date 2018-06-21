package merchantOperate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;

public class OperatorCategory extends BaseService {
	@BeforeClass
	public static void openBrowerOperatorCategory() throws Exception {
		openBrower("vsp_url");
	}

	@AfterClass
	public static void tearDown() {
		close();
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@After
	public void logoutOperatorCategory() throws InterruptedException {
		logoutVsp();
	}

	@Test
	public void categoryMgr() throws Exception {
		entryPage("商户运营", "栏目管理");
		cateGoryType();// 栏目类别
		cateGoryInfo();// 栏目信息

		searGoods();// 搜索新增的商城商品
		modGoods();// 修改商城商品信息
		seeDetail();// 查看详情
		batchDownUp("batchDownBtn", "下架");// 批量下架
		batchDownUp("batchUpBtn", "上架");// 批量上架
		deleteGoods();// 删除新建的商城商品
	}

	public void cateGoryType() throws InterruptedException {
		entryTabP("栏目类别");
		addcateGoryType();// 新增栏目类别
	}

	private void addcateGoryType() throws InterruptedException {
		dr.findElement(By.id("addCategoryTypeBtn")).click();
		sleep(2000);
		dr.findElement(By.id("categoryTypeNameAdd")).sendKeys("糖果时刻");

		closePrompt("addMerchantCategoryType", 1);
		new WebDriverWait(dr, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("noty_text")));
		assertEquals("成功 创建栏目类别成功", dr.findElement(By.className("noty_text")).getText());
	}

	public void cateGoryInfo() throws InterruptedException {
		entryTabP("栏目信息");
		addcateGory();// 新增栏目信息
	}

	private void entryTabP(String tabtext) throws InterruptedException {
		dr.findElement(By.linkText(tabtext)).click();
		sleep(2000);
	}

	private void addcateGory() throws InterruptedException {
		dr.findElement(By.id("addCategoryBtn")).click();
		sleep(2000);
		dr.findElement(By.id("categoryNameAdd")).sendKeys("糖果铺子");
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//li/div/div//input"))
				.sendKeys(getTemplatePath("candy2"));
		sleep(3000);

		select("typeIdAdd", "糖果时刻");
		closePrompt("addMerchantCategory", 1);
		new WebDriverWait(dr, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("noty_text")));
		assertEquals("成功 创建栏目成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void searGoods() throws InterruptedException {
		dr.findElement(By.id("searchSpName")).sendKeys("菇凉de店铺");
		dr.findElement(By.id("searchGoodsName")).sendKeys("开心糖果");

		select("searchGoodsType", "糖果铺子");
		searchTest("searchBtn", "pages");
	}

	private void modGoods() throws InterruptedException {
		dr.findElement(By.name("j-editDepartUser")).click();
		sleep(2000);

		select("goodsStyleEdit", " 在线邮寄");// 更改商品类型：在线邮寄
		closePrompt("editDeptUser", 1);

		new WebDriverWait(dr, 15).until(ExpectedConditions.presenceOfElementLocated(By.className("noty_text")));
		assertEquals("成功 修改商品信息成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void seeDetail() throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(3000);

		assertEquals("开心糖果", dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		assertEquals(true, dr.findElement(By.id("isPickUpInfo")).isSelected());

		closePrompt("deptUserInfo", 1);
	}

	private void batchDownUp(String duid, String dutext) throws InterruptedException {
		batchDownUp(duid);// 批量下架
		assertEquals("警告 请选择您要" + dutext + "的商品", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.name("selectSpBalance")).click();
		sleep(1500);

		batchDownUp(duid);// 批量下架
		assertEquals("确认" + dutext + "吗？", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);
		assertEquals("成功 商品批量" + dutext + "成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("已" + dutext + "",
				dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[5]")).getText());
	}

	private void deleteGoods() throws InterruptedException {
		dr.findElement(By.name("j-deleteDepartUser")).click();
		sleep(2000);
		assertEquals("是否决定删除[开心糖果]商品?", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);
		assertEquals("成功 删除商品成功", dr.findElement(By.className("noty_text")).getText());

		if (!isElementExsit(dr, "//*[@id='mainContentList']/table/tbody/tr")) {
			resetTest("resetBtn", "searchBtn", "pages");
		} else {
			fail();
		}
	}

	protected void batchDownUp(String bid) throws InterruptedException {
		dr.findElement(By.id(bid)).click();
		sleep(2000);
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
