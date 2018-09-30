package merchantOperate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class OperatorCategory extends BaseService {
	protected static void prepareData(String pd) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", pd);
	}

	@Test
	public void categoryMgr() throws Exception {
		prepareData(SeleniumConstants.del_ca);
		prepareData(SeleniumConstants.del_goodsca);
		prepareData(SeleniumConstants.insert_sa);
		cateGoryInfo();// 栏目信息
		cateGoryType();// 栏目类别
	}

	protected void cateGoryInfo() throws Exception {
		entryPage("商户运营", "栏目管理");
		addData("addCategoryBtn", "categoryNameAdd", "解忧糖", "categoryStatusAdd", "addMerchantCategory", "栏目");// 新增栏目
		serData();// 搜索数据
		editData("categoryNameEdit", "editMerchantCategory", "");// 编辑栏目
		modSta("//td[text()='糖果时刻']/following-sibling::td/a[1]", "启用");// 启用
		modSta("//td[text()='糖果时刻']/following-sibling::td/a[1]", "停用");// 停用
		modSta("//td[text()='糖果时刻']/following-sibling::td/a[3]", "删除栏目");// 删除
		prepareData(SeleniumConstants.del_ca);
	}

	protected void cateGoryType() throws InterruptedException {
		entryTabP("栏目类别");
		addData("addCategoryTypeBtn", "categoryTypeNameAdd", "糖果时刻", "categoryTypeStatusAdd", "addMerchantCategoryType",
				"栏目类别");// 新增栏目类别
		editData("categoryTypeNameEdit", "editMerchantCategoryType", "类别");// 编辑栏目类别
		modSta("//td[text()='糖果时刻12']/following-sibling::td/a[1]", "启用");// 启用
		modSta("//td[text()='糖果时刻12']/following-sibling::td/a[1]", "停用");// 停用
		modSta("//td[text()='糖果时刻12']/following-sibling::td/a[3]", "删除栏目类别");// 删除
	}

	private void addData(String nid, String na, String t1, String sid, String aid, String t2)
			throws InterruptedException {
		dr.findElement(By.id(nid)).click();
		sleep(1500);
		dr.findElement(By.id(na)).sendKeys(t1);

		if (isElementExsit(dr, "//li[@class='active']/a[text()='栏目信息']")) {
			dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
			sleep(3000);
			select("typeIdAdd", "糖果时刻");
		}

		select(sid, "停用");
		closePrompt(aid, 1);

		assertEquals("成功 创建" + t2 + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void serData() throws InterruptedException {
		dr.findElement(By.id("searchSpName")).sendKeys("解忧糖");
		sleep(1000);
		dr.findElement(By.id("searchBtn")).click();
		sleep(1500);
	}

	private void editData(String id1, String id2, String t) throws InterruptedException {
		dr.findElement(By.xpath("//td[text()='糖果时刻']/following-sibling::td/a[2]")).click();
		sleep(1500);
		dr.findElement(By.id(id1)).sendKeys("12");
		sleep(1500);

		closePrompt(id2, 1);
		assertEquals("成功 编辑栏目" + t + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void modSta(String mx, String text) throws InterruptedException {
		dr.findElement(By.xpath(mx)).click();
		sleep(1500);
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(3000);

		assertEquals("成功 " + text + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void entryTabP(String tabtext) throws InterruptedException {
		dr.findElement(By.linkText(tabtext)).click();
		sleep(1500);
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
