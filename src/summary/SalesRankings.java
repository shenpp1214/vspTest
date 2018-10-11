package summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class SalesRankings extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void salesRankings() throws Exception {
		entryPage("统计报表", "销量排行");
		testData();// 校验按各个时间统计
		customTime();// 校验跨月自定义时间的统计详情-按月份显示统计+销售额测试
	}

	private void testData() throws InterruptedException {
		clickEle("//button[text()='今天']", 2000);

		List<WebElement> elements = dr.findElements(By.xpath("//*[@id='staticList']//tbody//td[1]"));
		if (!dr.findElement(By.xpath("//*[@id='pages']")).isDisplayed())
			assertEquals("没有查询到数据", elements.get(0).getText());
	}

	private void customTime() throws Exception {
		clickEle("//*[@id='customSet']", 1500);
		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		clearInp("searchBeginDate", "2017-10-01");
		clearInp("searchEndDate", "2017-10-31");
		clickEle("//*[@id='customSetSubmit']", 3000);

		String allorders = dr.findElement(By.xpath("//*[@id='staticList']//tr[1]/td[3]")).getText();
		String allmoney = dr.findElement(By.xpath("//*[@id='staticList']//tr[1]/td[4]")).getText();

		exportData();// 导出销量排行
		assertEquals(allorders + ".0", getCellValue1("Sheet0", 2, 3));
		assertEquals(allmoney, getCellValue1("Sheet0", 2, 4));
		orderErgodic();// 进入商品排行校验数据
		assertEquals(true, Integer.valueOf(allorders) == SeleniumConstants.totalOrders);
		assertEquals(true, Double.valueOf(allmoney) == SeleniumConstants.totalPay);

		double[] o = {
				Integer.valueOf(getCellValue1("sheet0", 2, 3).substring(0, getCellValue1("sheet0", 2, 3).length() - 2)),
				Integer.valueOf(getCellValue1("sheet0", 3, 3).substring(0, getCellValue1("sheet0", 3, 3).length() - 2)),
				Integer.valueOf(getCellValue1("sheet0", 4, 3).substring(0, getCellValue1("sheet0", 4, 3).length() - 2)),
				Integer.valueOf(getCellValue1("sheet0", 5, 3).substring(0, getCellValue1("sheet0", 5, 3).length() - 2)),
				Integer.valueOf(getCellValue1("sheet0", 6, 3).substring(0, getCellValue1("sheet0", 6, 3).length() - 2)),
				Double.valueOf(getCellValue1("sheet0", 2, 4)), Double.valueOf(getCellValue1("sheet0", 3, 4)),
				Double.valueOf(getCellValue1("sheet0", 4, 4)), Double.valueOf(getCellValue1("sheet0", 5, 4)),
				Double.valueOf(getCellValue1("sheet0", 6, 4)) };
		if ((o[0] + o[1] + o[2] + o[3] + o[4]) != Integer.valueOf(allorders)
				|| (o[5] + o[6] + o[7] + o[8] + o[9] != Double.valueOf(allmoney)))
			fail();

		clickEle("//a[text()='销售额排行']", 1500);
		exportData();
		assertEquals(allorders + ".0", getCellValue1("sheet0", 2, 3));
		assertEquals(allmoney, getCellValue1("sheet0", 2, 4));
		assertEquals(allorders, dr.findElement(By.xpath("//*[@id='staticList']//tr[1]/td[3]")).getText());
		assertEquals(allmoney, dr.findElement(By.xpath("//*[@id='staticList']//tr[1]/td[4]")).getText());
	}

	protected void orderErgodic() throws Exception {
		clickEle("//a[text()='商品排行']", 2000);
		exportData();// 导出商品排行数据

		List<WebElement> order = dr.findElements(By.xpath("//*[@id='staticList']//tr/td[3]"));
		List<WebElement> money = dr.findElements(By.xpath("//*[@id='staticList']//tr/td[4]"));
		for (WebElement e : order) {
			int or = Integer.valueOf(e.getText());
			SeleniumConstants.totalOrders += or;
		}
		for (WebElement e : money) {
			double mon = Double.valueOf(e.getText());
			SeleniumConstants.totalPay += mon;
		}
	}

	protected void exportData() throws Exception {
		dr.findElement(By.id("downloadSummary")).click();
		sleep(3000);

		assertEquals("排名", getCellValue1("Sheet0", 1, 1));
		assertEquals("订单总数", getCellValue1("Sheet0", 1, 3));
		assertEquals("订单总金额(元)", getCellValue1("Sheet0", 1, 4));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
