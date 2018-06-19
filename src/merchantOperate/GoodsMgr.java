package merchantOperate;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
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
import baseService.SeleniumConstants;

public class GoodsMgr extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@BeforeClass
	public static void openBrowerGoodsMgr() throws Exception {
		openBrower("vsp_url");
		prepareData(SeleniumConstants.insert_mer);
		prepareData(SeleniumConstants.insert_cat);
		prepareData(SeleniumConstants.insert_gc);
	}

	protected static void prepareData(String pd) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", pd);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		prepareData(SeleniumConstants.del_mer);
		prepareData(SeleniumConstants.del_cat);
		prepareData(SeleniumConstants.del_gc);
		prepareData(SeleniumConstants.del_goods);
		dr.close();
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void goodsMgr() throws Exception {
		entryPage("商户运营", "商城商品管理");
		String pages = dr.findElement(By.id("pages")).getText();
		String num = pages.substring(1, pages.length() - 3);

		addGoods(Integer.valueOf(num));// 添加商城商品成功
		searGoods();// 搜索新增的商城商品
		modGoods();// 修改商城商品信息
		seeDetail();// 查看详情
		batchDownUp("batchDownBtn", "下架");// 批量下架
		batchDownUp("batchUpBtn", "上架");// 批量上架
		deleteGoods();// 删除新建的商城商品
	}

	private void addGoods(int num2) throws InterruptedException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		String date1 = DateFormatUtils.format(cal, "yyyy-MM-dd");

		dr.findElement(By.id("addGoodsBtn")).click();
		sleep(2000);// 进入创建商品界面
		dr.findElement(By.id("goodsNameAdd")).sendKeys("开心糖果");
		dr.findElement(By.id("goodsPriceAdd")).sendKeys("18.5");
		sleep(1500);

		dr.findElement(By.xpath("//div[@id='uploadListPicDivAdd']//li/div/div//input"))
				.sendKeys(getTemplatePath("candy1"));
		sleep(3000);
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//li/div/div//input"))
				.sendKeys(getTemplatePath("candy1"));
		sleep(3000);
		select("goodsTypeAdd", "糖果铺子");
		select("merchantAdd", "菇凉de店铺");
		select("goodsPreBookAdd", "至少提前一天预约");
		removeReadonly("goodsDateStartAdd");
		removeReadonly("goodsDateEndAdd");

		dr.findElement(By.id("goodsDateStartAdd")).sendKeys(date);
		dr.findElement(By.id("goodsDateEndAdd")).sendKeys(date1);
		sleep(2000);

		dr.switchTo().frame(dr.findElement(By.xpath("//div[@id='addDeptUser']/table//tr[9]/td[2]//div/div[2]/iframe")));
		dr.findElement(By.tagName("body")).sendKeys("测试测试测试");
		dr.switchTo().defaultContent();
		closePrompt("addDeptUser", 1);

		new WebDriverWait(dr, 15).until(ExpectedConditions.presenceOfElementLocated(By.id("pages")));
		String pages1 = dr.findElement(By.id("pages")).getText();
		String num = pages1.substring(1, pages1.length() - 3);

		if (Integer.valueOf(num) - 1 != num2)
			fail();
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

	@After
	public void logoutGoodsMgr() throws InterruptedException {
		logoutVsp();
	}
}
