package summary;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class BonusStatic extends BaseService {

	@Test
	public void bonusStatic() throws Exception {
		entryPage("统计报表", "积分统计");// 进入页面
		dayPointsStatic();// 日积分统计
		perPointsRank();// 个人积分排行
	}

	private void dayPointsStatic() throws InterruptedException {
		search("publishTimeBeginSearch", "publishTimeEndSearch", SeleniumConstants.date, SeleniumConstants.date,
				"searchSubmit4Day", "pages");// 搜索具体的数据
		openPrompt("sentOutPointsDetail", "发放积分详情");// 打开发放积分详情
		closePrompt("dayDetailPanel", 1);// 关闭弹框
		openPrompt("receivedPointsDetail", "回收积分详情");// 打开回收积分详情
		closePrompt("dayDetailPanel", 1);// 关闭弹框

		String grantPoint = dr.findElement(By.name("sentOutPointsDetail")).getText();
		String receivePoint = dr.findElement(By.name("receivedPointsDetail")).getText();
		assertEquals(grantPoint, dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[2]/td[2]")).getText());
		assertEquals(receivePoint, dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[2]/td[3]")).getText());

		resetTest("restBtn4Day", "searchSubmit4Day", "pages");// 重置搜索
	}

	private void perPointsRank() throws InterruptedException {
		dr.findElement(By.linkText("个人积分排行")).click();
		sleep(2000);// 进入个人积分排行tab页

		// int total = 0;
		// String user1 =
		// dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[2]")).getText();
		// String phone1 =
		// dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[3]")).getText();
		// String totalpoints =
		// dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[4]/a")).getText();
		//
		// search("userName4RankSearch", "mobile4RankSearch", user1, phone1,
		// "searchBtn4Rank", "pages");
		// openPrompt("rankDetail");// 打开
		//
		// String num = dr.findElement(By.id("rankDetailPages")).getText();
		// int numData = Integer.valueOf(num.substring(1, num.length() - 3));
		// List<WebElement> elements =
		// dr.findElements(By.xpath("//*[@id='rankDetailList']/table/tbody/tr/td[7]"));
		// for (int i = 0; i < elements.size(); i++) {
		// int points = Integer.valueOf(elements.get(i).getText());
		// total += points;
		// }
		// if (total != Integer.valueOf(totalpoints) || numData != elements.size())
		// fail();
		// closePrompt("rankDetailPanel", 1);
		// resetTest("restBtn4Rank", "searchBtn4Rank", "pages");// 重置搜索
	}

	protected void search(String id1, String id2, String s1, String s2, String sid, String pid)
			throws InterruptedException {
		dr.findElement(By.id(id1)).sendKeys(s1);
		dr.findElement(By.id(id2)).sendKeys(s2);
		sleep(2000);
		searchTest(sid, pid);
	}

	protected void openPrompt(String name, String text) throws InterruptedException {
		dr.findElement(By.name(name)).click();// 打开发放积分详情
		sleep(1500);

		assertEquals(text, dr.findElement(By.id("ui-id-2")).getText());
	}
}
