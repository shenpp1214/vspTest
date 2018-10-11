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
		prepareData(SeleniumConstants.insert_ca);
		cateGoryInfo();// 栏目信息
		cateGoryType();// 栏目类别
	}

	protected void cateGoryInfo() throws Exception {
		entryPage("商户运营", "栏目管理");
		addData("addCategoryBtn", "categoryNameAdd", "解忧糖", "categoryStatusAdd", "addMerchantCategory", "栏目");// 新增栏目
		clearInp("searchSpName", "解忧糖");
		clickEle("//*[@id='searchBtn']", 1500);
		operData(2, "", "categoryNameEdit", "解忧糖", "editMerchantCategory", "编辑栏目");// 编辑栏目
		operData(1, "", null, null, null, "启用");// 启用
		operData(1, "", null, null, null, "停用");// 停用
		operData(3, "", null, null, null, "删除栏目");// 删除
		prepareData(SeleniumConstants.del_ca);
	}

	protected void cateGoryType() throws InterruptedException {
		clickEle("//a[text()='栏目类别']", 1000);
		addData("addCategoryTypeBtn", "categoryTypeNameAdd", "糖果时刻", "categoryTypeStatusAdd", "addMerchantCategoryType",
				"栏目类别");// 新增栏目类别
		operData(2, "", "categoryTypeNameEdit", "糖果时刻", "editMerchantCategoryType", "编辑栏目类别");// 编辑栏目类别
		operData(1, "12", null, null, null, "启用");// 启用
		operData(1, "12", null, null, null, "停用");// 停用
		operData(3, "12", null, null, null, "删除栏目类别");// 删除
	}

	private void addData(String nid, String na, String t1, String sid, String aid, String t2)
			throws InterruptedException {
		clickEle("//*[@id='" + nid + "']", 1500);
		clearInp(na, t1);

		if (isElementExsit(dr, "//li[@class='active']/a[text()='栏目信息']")) {
			dr.findElement(By.xpath("//div[@id='uploadGoodsPicDivAdd']//input")).sendKeys(getTemplatePath("candy1"));
			sleep(3000);
			select("typeIdAdd", "糖果时刻", 1500);
		}

		select(sid, "停用", 1000);
		closePrompt(aid, 1, 1500);

		assertEquals("成功 创建" + t2 + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void operData(int n, String n1, String i1, String t1, String i2, String t) throws InterruptedException {
		clickEle("//td[text()='糖果时刻" + n1 + "']/following-sibling::td/a[" + n + "]", 1500);

		if (n == 2) {
			clearInp(i1, "" + t1 + "12");
			closePrompt(i2, 1, 1500);
		} else {
			clickEle("//div[@class='noty_buttons']/button[1]", 3000);
		}

		assertEquals("成功 " + t + "成功", dr.findElement(By.className("noty_text")).getText());
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('" + outid + "').removeAttribute('readonly');");
	}
}
