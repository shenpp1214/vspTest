package customerVehice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class PersCustomer extends BaseService {

	@BeforeClass
	public static void openBrowerPersCustomer() throws Exception {
		openBrower("vsp_url");// 打开浏览器最大化
		prepareData();// 准备数据
	}

	private static void prepareData() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.insertsql);
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
		assertEquals("个人客户", dr.findElement(By.xpath("//a[@href='./persCustomer.jsp']")).getText());// 校验是否登录成功
	}

	@Test
	public void individualCus() throws Exception {
		searchData();// 搜索测试
		openAndHide();// 展开隐藏搜索区域
		openAndSet();// 打开积分并设置
		historySee();// 历史查看
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
		flip();// 翻页
		delData();// 删除插入的数据
	}

	private void searchData() throws InterruptedException {
		dr.findElement(By.id("searchUserName")).sendKeys("shenpp");
		dr.findElement(By.id("searchName")).sendKeys("shenpp");
		dr.findElement(By.id("searchPhone")).sendKeys("15951901290");
		dr.findElement(By.id("searchEmail")).sendKeys("779230186@qq.com");

		select("searchUserSex", "女");
		select("searchOwnerType", "VSP");
		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void openAndHide() throws InterruptedException {
		openHide(false);// 隐藏搜索
		openHide(true);// 展开搜索
	}

	private void openAndSet() throws InterruptedException {
		dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr[1]/td[11]/a")).click();
		sleep(2000);// 打开积分设置对话框

		String oldPoints = dr.findElement(By.id("userPoints")).getText();
		String newPoints = Integer.toString(Integer.parseInt(oldPoints) + 200);

		setPoints("addPoints");// 增加200积分
		assertEquals(newPoints, dr.findElement(By.id("userPoints")).getText());

		select("changePointsType", "减少");
		setPoints("reducePoints");// 减少200积分
		assertEquals(oldPoints, dr.findElement(By.id("userPoints")).getText());
	}

	private void historySee() throws Exception {
		dr.findElement(By.linkText("历史记录")).click();
		sleep(1500);

		assertEquals("减少", dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[1]/td[1]")).getText());
		assertEquals("-200", dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[1]/td[2]")).getText());
		assertEquals("reducePoints",
				dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[1]/td[4]")).getText());
		assertEquals("增加", dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[2]/td[1]")).getText());
		assertEquals("200", dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[2]/td[2]")).getText());
		assertEquals("addPoints",
				dr.findElement(By.xpath("//*[@id='bonusSetHisList']/table/tbody/tr[2]/td[4]")).getText());

		closePrompt("bonusSetPanel", 1);
	}

	private void delData() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.delsql);
	}

	protected void setPoints(String text) throws InterruptedException {
		dr.findElement(By.id("changePoints")).sendKeys("200");
		closePrompt("bonusSetPanel", 1);
		assertEquals("警告 备注不能为空", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.id("changePointsRemark")).sendKeys(text);
		closePrompt("bonusSetPanel", 1);
		assertEquals("成功 用户红利设置成功", dr.findElement(By.className("noty_text")).getText());
	}

}
