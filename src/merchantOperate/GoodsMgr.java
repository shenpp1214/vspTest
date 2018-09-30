package merchantOperate;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class GoodsMgr extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	protected static void prepareData(String pd) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", pd);
	}

	@Test
	public void goodsMgr() throws Exception {
		prepareData(SeleniumConstants.del_mer);
		prepareData(SeleniumConstants.del_u);
		prepareData(SeleniumConstants.del_gc);
		prepareData(SeleniumConstants.del_goods);
		prepareData(SeleniumConstants.insert_mer);
		prepareData(SeleniumConstants.insert_gc);
		entryPage("商户运营", "商城商品管理");

		String pages = dr.findElement(By.id("pages")).getText();
		int num = Integer.valueOf(pages.substring(1, pages.length() - 3));

		addGoods(num + 1);// 添加商城商品成功
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
		dr.findElement(By.xpath("//div[@id='uploadListPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
		sleep(3000);
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
		sleep(3000);

		select("goodsTypeAdd", "糖果铺子");
		select("merchantAdd", "菇凉de店铺");
		select("goodsPreBookAdd", "至少提前一天预约");
		removeReadonly("goodsDateStartAdd");
		removeReadonly("goodsDateEndAdd");

		dr.findElement(By.id("goodsDateStartAdd")).sendKeys(date);
		dr.findElement(By.id("goodsDateEndAdd")).sendKeys(date1);
		sleep(2000);
		dr.switchTo().frame(dr.findElement(By.xpath("//div[@id='addDeptUser']//iframe")));
		dr.findElement(By.tagName("body")).sendKeys("测试测试测试");
		dr.switchTo().defaultContent();
		closePrompt("addDeptUser", 1);
		sleep(3000);

		String pages1 = dr.findElement(By.id("pages")).getText();
		int num1 = Integer.valueOf(pages1.substring(1, pages1.length() - 3));
		assertEquals(num2, num1);
	}

	private void searGoods() throws InterruptedException {
		dr.findElement(By.id("searchSpName")).sendKeys("菇凉de店铺");
		dr.findElement(By.id("searchGoodsName")).sendKeys("开心糖果");
		sleep(1500);

		select("searchGoodsType", "糖果铺子");
		searchTest("searchBtn", "pages");
	}

	private void modGoods() throws InterruptedException {
		dr.findElement(By.name("j-editDepartUser")).click();
		sleep(1500);

		select("goodsStyleEdit", " 在线邮寄");// 更改商品类型：在线邮寄
		closePrompt("editDeptUser", 1);

		assertEquals("成功 修改商品信息成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void seeDetail() throws InterruptedException {
		dr.findElement(By.name("j-accountDetail")).click();
		sleep(1500);

		assertEquals("开心糖果", dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		assertEquals(true, dr.findElement(By.id("isPickUpInfo")).isSelected());

		closePrompt("deptUserInfo", 1);
	}

	private void batchDownUp(String duid, String dutext) throws InterruptedException {
		dr.findElement(By.name("selectSpBalance")).click();
		sleep(1500);
		dr.findElement(By.id(duid)).click();
		sleep(1500);// 批量

		assertEquals("确认" + dutext + "吗？", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);

		assertEquals("成功 商品批量" + dutext + "成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("已" + dutext + "", dr.findElement(By.xpath("//*[@id='mainContentList']//td[5]")).getText());
	}

	private void deleteGoods() throws InterruptedException {
		dr.findElement(By.name("j-deleteDepartUser")).click();
		sleep(2000);

		assertEquals("是否决定删除[开心糖果]商品?", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);

		assertEquals("成功 删除商品成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(false, isElementExsit(dr, "//*[@id='mainContentList']//td"));
		resetTest("resetBtn", "searchBtn", "pages");
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
