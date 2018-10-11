package jsrbActivityMana;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class JsrbActivityMana extends BaseService {
	@BeforeClass
	public static void openBrowerJsrbActMana() throws Exception {
		openBrower("vsp_url");
		prepareData(SeleniumConstants.del_act);
		prepareData(SeleniumConstants.del_winrecord);
		prepareData(SeleniumConstants.insert_act);
		prepareData(SeleniumConstants.insert_winrecord);
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void jsrbActMana() throws InterruptedException {
		entryPage("运营中心", "人保查违章活动");
		assertEquals("活动名称", dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[1]")).getText());
		assertEquals("请你吃大闸蟹",
				dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr[3]/td[2]")).getText());

		openWinRecord();// 进入中奖记录界面
		operateRecord();// 处理未处理的中奖记录
		closeWinRecord();// 关闭中奖记录界面
	}

	private void openWinRecord() throws InterruptedException {
		clickEle("//td[text()='请你吃大闸蟹']/following-sibling::td[5]/a", 1500);

		assertEquals("中奖名单",
				dr.findElement(By.xpath("//div[@id='lotteryListPanel']/preceding-sibling::div/span")).getText());
		assertEquals("请你吃大闸蟹", dr.findElement(By.xpath("//div[@id='mainContentList1']//tr[1]/td[2]")).getText());
		assertEquals("199****5961", dr.findElement(By.xpath("//div[@id='mainContentList1']//tr[1]/td[4]")).getText());
		assertEquals("未处理", dr.findElement(By.xpath("//div[@id='mainContentList1']//tr[1]/td[9]")).getText());
	}

	private void operateRecord() throws InterruptedException {
		clickEle("//*[@name='j-handleLottery']", 1500);// 打开处理框
		clearInp("expressCorp", "顺丰速递");// 输入快递公司
		clearInp("expressNo", "9212141100");// 输入快递单号
		clickEle("//div[@id='handleLotteryPanel']/following-sibling::div//button[1]", 2000);

		assertEquals("成功 处理成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("已处理", dr.findElement(By.xpath("//div[@id='mainContentList1']/table/tbody//td[9]")).getText());
	}

	private void closeWinRecord() throws InterruptedException {
		((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);",
				dr.findElement(By.xpath("//div[@id='handleLotteryPanel']/following-sibling::div//button")));

		clickEle("//div[@id='lotteryListPanel']/following-sibling::div//button", 1500);
	}

	public static void prepareData(String aa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aa);
	}
}
