package summary;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class BonusStatic extends BaseService {
	public static final String user = "嘉兴人保（金币商城）17061517050380793";
	public static final String phone = "15190479709";

	@Test
	public void bonusStatic() throws Exception {
		entryPage("统计报表", "积分统计");// 进入页面
		dayPointsStatic();// 日积分统计
	}

	private void dayPointsStatic() throws InterruptedException {
		search("publishTimeBeginSearch", "publishTimeEndSearch", "2018-01-01", "2018-01-10");// 搜索具体的数据
		if (isElementExsit(dr, "//div[@id='staticList']//tbody//tr[1]/td[3]/a")) {
			openPrompt("sentOutPointsDetail", "发放积分详情");// 打开发放积分详情
			closePrompt("dayDetailPanel", 1);// 关闭弹框
		}
		if (isElementExsit(dr, "//div[@id='staticList']//tbody//td[4]/a")) {
			openPrompt("receivedPointsDetail", "回收积分详情");// 打开回收积分详情
			closePrompt("dayDetailPanel", 1);// 关闭弹框
		}

		int grantPoint = Integer
				.valueOf(dr.findElement(By.xpath("//td[text()='总计']/following-sibling::td[1]")).getText());

		List<WebElement> elements = dr.findElements(By.xpath("//div[@id='staticList']//tbody//td[3]/a"));
		int toGrantPoints = 0;
		for (WebElement e : elements) {
			int grPo = Integer.valueOf(e.getText());
			toGrantPoints += grPo;
		}
		assertEquals(grantPoint, toGrantPoints);
		System.out.println(grantPoint + "," + toGrantPoints);

		resetTest("restBtn4Day", "searchSubmit4Day", "pages");// 重置搜索
	}

	protected void search(String id1, String id2, String s1, String s2) throws InterruptedException {
		dr.findElement(By.id(id1)).sendKeys(s1);
		dr.findElement(By.id(id2)).sendKeys(s2);
		sleep(2000);
		dr.findElement(By.id("searchSubmit4Day")).click();
		sleep(1500);
	}

	protected void openPrompt(String name, String text) throws InterruptedException {
		dr.findElement(By.name(name)).click();// 打开发放积分详情
		sleep(1500);

		assertEquals(text, dr.findElement(By.id("ui-id-2")).getText());
	}
}
