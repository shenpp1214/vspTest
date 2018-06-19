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
		customTime();// 校验跨月自定义时间的统计详情-按月份显示统计
		entrySalesRank();// 销售额测试
	}

	private void testData() throws InterruptedException {
		String[] text = { "今天", "昨天", "本月", "最近七天" };
		for (int i = 0; i < 4; i++) {
			dr.findElement(By.xpath("//button[text()='" + text[i] + "']")).click();
			sleep(2000);

			if (!dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td")).getText().equals("没有查询到数据")) {
				if (!isElementExsit(dr, "//span[@id='pages']"))
					fail();
			}
		}
	}

	private void customTime() throws Exception {
		dr.findElement(By.id("customSet")).click();

		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");

		dr.findElement(By.id("searchBeginDate")).clear();
		dr.findElement(By.id("searchEndDate")).clear();
		dr.findElement(By.id("searchBeginDate")).sendKeys(SeleniumConstants.date1);
		dr.findElement(By.id("searchEndDate")).sendKeys(SeleniumConstants.date2);
		sleep(2000);
		dr.findElement(By.id("customSetSubmit")).click();
		sleep(3000);

		String allorders = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[3]")).getText();
		String allmoney = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[4]")).getText();

		exportData();// 导出销量排行
		assertEquals(allorders + ".0", getCellValue1("Sheet0", 2, 3));
		assertEquals(allmoney, getCellValue1("Sheet0", 2, 4));
		entryGoodsRank();// 进入商品排行校验数据
		if (SeleniumConstants.orders != Double.valueOf(allorders)
				|| SeleniumConstants.moneys != Double.valueOf(allmoney))
			fail();
		exportData();// 导出商品排行数据
		double[] o = { Double.valueOf(getCellValue1("sheet0", 2, 3)), Double.valueOf(getCellValue1("sheet0", 3, 3)),
				Double.valueOf(getCellValue1("sheet0", 4, 3)), Double.valueOf(getCellValue1("sheet0", 5, 3)),
				Double.valueOf(getCellValue1("sheet0", 6, 3)), Double.valueOf(getCellValue1("sheet0", 2, 4)),
				Double.valueOf(getCellValue1("sheet0", 3, 4)), Double.valueOf(getCellValue1("sheet0", 4, 4)),
				Double.valueOf(getCellValue1("sheet0", 5, 4)), Double.valueOf(getCellValue1("sheet0", 6, 4)) };
		if ((o[0] + o[1] + o[2] + o[3] + o[4]) != Double.valueOf(allorders)
				|| (o[5] + o[6] + o[7] + o[8] + o[9] != Double.valueOf(allmoney)))
			fail();
	}

	private void entrySalesRank() throws Exception {
		dr.findElement(By.linkText("销售额排行")).click();
		sleep(1500);

		String saleorder = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[3]")).getText();
		String salemoney = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[4]")).getText();

		exportData();
		assertEquals(saleorder + ".0", getCellValue1("sheet0", 2, 3));
		assertEquals(salemoney, getCellValue1("sheet0", 2, 4));
	}

	protected void entryGoodsRank() throws InterruptedException {
		dr.findElement(By.linkText("商品排行")).click();
		sleep(2000);
		orderErgodic();// 遍历订单和金额取和
	}

	protected void orderErgodic() throws InterruptedException {
		String pages = dr.findElement(By.id("pages")).getText();
		int pagenum = Integer.valueOf(pages.substring(1, pages.length() - 3));
		List<WebElement> order = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[3]"));
		List<WebElement> money = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[4]"));
		for (int i = 0; i < order.size(); i++) {
			double ord = Double.valueOf(order.get(i).getText());
			double mon = Double.valueOf(money.get(i).getText());
			SeleniumConstants.orders += ord;
			SeleniumConstants.moneys += mon;
		}
		if (pagenum != order.size())
			fail();
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
