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
		deleteData();
		prepareData();
	}

	public static void prepareData() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.insert_act);// 插入活动
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.insert_winrecord);// 插入中奖名单
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
		dr.findElement(By.xpath("//td[text()='请你吃大闸蟹']/following-sibling::td[5]/a")).click();
		sleep(1500);
		assertEquals("中奖名单",
				dr.findElement(By.xpath("//div[@id='lotteryListPanel']/preceding-sibling::div/span")).getText());
		assertEquals("请你吃大闸蟹", dr.findElement(By.xpath("//div[@id='mainContentList1']/table/tbody//td[2]")).getText());
		assertEquals("199****5961",
				dr.findElement(By.xpath("//div[@id='mainContentList1']/table/tbody//td[4]")).getText());
		assertEquals("未处理", dr.findElement(By.xpath("//div[@id='mainContentList1']/table/tbody//td[9]")).getText());
	}

	private void operateRecord() throws InterruptedException {
		dr.findElement(By.name("j-handleLottery")).click();// 打开处理框
		sleep(1500);
		dr.findElement(By.id("expressCorp")).sendKeys("顺丰速递");// 输入快递公司
		dr.findElement(By.id("expressNo")).sendKeys("9212141100");// 输入快递单号
		sleep(2000);
		dr.findElement(By.xpath("//div[@id='handleLotteryPanel']/following-sibling::div//button[1]")).click();// 点击处理按钮
		sleep(2000);
		assertEquals("成功 处理成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("已处理", dr.findElement(By.xpath("//div[@id='mainContentList1']/table/tbody//td[9]")).getText());
	}

	private void closeWinRecord() throws InterruptedException {
		((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);",
				dr.findElement(By.xpath("//div[@id='handleLotteryPanel']/following-sibling::div//button")));
		dr.findElement(By.xpath("//div[@id='lotteryListPanel']/following-sibling::div//button")).click();// 关闭
		sleep(2000);
	}

	private static void deleteData() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_act);
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_winrecord);
	}
}
