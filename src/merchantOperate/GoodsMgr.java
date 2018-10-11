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
		operGoods("selectSpBalance", "batchDownBtn", "确认下架吗？", "成功 商品批量下架成功", "下架");
		operGoods("selectSpBalance", "batchUpBtn", "确认上架吗？", "成功 商品批量上架成功", "上架");
		operGoods("j-deleteDepartUser", null, "是否决定删除[开心糖果]商品?", "成功 删除商品成功", null);// 删除新建的商城商品
		resetTest("resetBtn", "searchBtn", "pages");
	}

	private void addGoods(int num2) throws InterruptedException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		String date1 = DateFormatUtils.format(cal, "yyyy-MM-dd");

		clickEle("//*[@id='addGoodsBtn']", 1000);// 进入创建商品界面
		clearInp("goodsNameAdd", "开心糖果");
		clearInp("goodsPriceAdd", "18.5");
		select("goodsTypeAdd", "糖果铺子", 1000);
		select("merchantAdd", "菇凉de店铺", 1000);
		select("goodsPreBookAdd", "至少提前一天预约", 1000);
		removeReadonly("goodsDateStartAdd");
		removeReadonly("goodsDateEndAdd");
		clearInp("goodsDateStartAdd", date);
		clearInp("goodsDateEndAdd", date1);

		dr.findElement(By.xpath("//div[@id='uploadListPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
		sleep(3000);
		dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
		sleep(3000);
		dr.switchTo().frame(dr.findElement(By.xpath("//div[@id='addDeptUser']//iframe")));
		dr.findElement(By.tagName("body")).sendKeys("测试测试测试");
		dr.switchTo().defaultContent();
		closePrompt("addDeptUser", 1, 4000);

		String pages1 = dr.findElement(By.id("pages")).getText();
		int num1 = Integer.valueOf(pages1.substring(1, pages1.length() - 3));
		assertEquals(num2, num1);
	}

	private void searGoods() throws InterruptedException {
		clearInp("searchSpName", "菇凉de店铺");
		clearInp("searchGoodsName", "开心糖果");
		select("searchGoodsType", "糖果铺子", 1000);
		searchTest("searchBtn", "pages");
	}

	private void modGoods() throws InterruptedException {
		clickEle("//*[@name='j-editDepartUser']", 1500);
		select("goodsStyleEdit", " 在线邮寄", 1000);// 更改商品类型：在线邮寄
		closePrompt("editDeptUser", 1, 2000);

		assertEquals("成功 修改商品信息成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void seeDetail() throws InterruptedException {
		clickEle("//*[@name='j-accountDetail']", 1500);

		assertEquals("开心糖果", dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		assertEquals(true, dr.findElement(By.id("isPickUpInfo")).isSelected());

		closePrompt("deptUserInfo", 1, 1500);
	}

	private void operGoods(String n, String duid, String t1, String t2, String t3) throws InterruptedException {
		clickEle("//*[@name='" + n + "']", 1500);
		if (n == "selectSpBalance") {
			clickEle("//*[@id='" + duid + "']", 1500);// 批量
		}

		assertEquals(t1, dr.findElement(By.className("noty_text")).getText());
		clickEle("//div[@class='noty_buttons']/button[1]", 1500);
		assertEquals(t2, dr.findElement(By.className("noty_text")).getText());

		if (n == "selectSpBalance")
			assertEquals("已" + t3 + "", dr.findElement(By.xpath("//*[@id='mainContentList']//td[5]")).getText());
		else
			assertEquals(false, isElementExsit(dr, "//*[@id='mainContentList']//td"));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
