package summary;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class SalesStatic extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void salesStatic() throws Exception {
		entryPage("统计报表", "销量统计");
		testData();// 校验按各个时间统计时统计详情的数据遍历
		customTime();// 校验跨月自定义时间的统计详情-按月份显示统计
	}

	private void testData() throws InterruptedException {
		clickEle("//button[text()='今天']", 2000);

		List<WebElement> elements = dr.findElements(By.xpath("//*[@id='staticList']//tbody//td[1]"));
		if (dr.findElement(By.id("totalOrder")).getText().equals("0"))
			assertEquals("没有查询到数据", elements.get(0).getText());
		else
			assertEquals(date, elements.get(0).getText());
		assertEquals(1, elements.size());
	}

	private void customTime() throws Exception {
		clickEle("//*[@id='customSet']", 1500);
		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		clearInp("searchBeginDate", "2017-10-30");
		clearInp("searchEndDate", "2017-10-31");
		clickEle("//*[@id='customSetSubmit']", 3000);

		List<WebElement> elements = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[1]"));
		List<WebElement> os = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[2]"));
		List<WebElement> pay = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[3]"));

		int ToOrders = Integer.valueOf(dr.findElement(By.id("totalOrder")).getText());
		double ToMoney = Double.valueOf(dr.findElement(By.id("totalMoney")).getText());
		assertEquals(2, elements.size());
		assertEquals("2017-10-31", elements.get(0).getText());
		assertEquals("2017-10-30", elements.get(1).getText());

		int orders = 0;
		double moneys = 0.0;
		for (WebElement e : os) {
			int order = Integer.valueOf(e.getText());
			orders += order;
		}
		for (WebElement e : pay) {
			double money = Double.valueOf(e.getText());
			moneys += money;
		}
		assertEquals(true, ToOrders == orders);
		assertEquals(true, ToMoney == moneys);

		exportData();// 导出数据并校验excel数据
		assertEquals(os.get(0).getText() + ".0", getCellValue1("Sheet0", 2, 3));
		assertEquals(os.get(1).getText() + ".0", getCellValue1("Sheet0", 3, 3));
		assertEquals(pay.get(0).getText() + ".0", getCellValue1("Sheet0", 2, 4));
		assertEquals(pay.get(1).getText(), getCellValue1("Sheet0", 3, 4));
	}

	protected void exportData() throws Exception {
		clickEle("//*[@id='downloadSummary']", 3000);

		assertEquals("排名", getCellValue1("Sheet0", 1, 1));
		assertEquals("日期", getCellValue1("Sheet0", 1, 2));
		assertEquals("订单总数", getCellValue1("Sheet0", 1, 3));
		assertEquals("订单总金额(元)", getCellValue1("Sheet0", 1, 4));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}

	protected void clearInp(String id, String d) throws InterruptedException {
		dr.findElement(By.id(id)).clear();
		dr.findElement(By.id(id)).sendKeys(d);
		sleep(1500);
	}

	protected void clickEle(String xid, int sec) throws InterruptedException {
		dr.findElement(By.xpath(xid)).click();
		sleep(sec);
	}

}
