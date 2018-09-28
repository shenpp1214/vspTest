package manageCenter;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class UserAccount extends BaseService {

	private void modData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}

	@Test
	public void userAccount() throws Exception {
		modData(SeleniumConstants.del_user);// 删除数据
		uAccount();// 员工账号
		modData(SeleniumConstants.del_sa);// 删除sa人员
		modData(SeleniumConstants.insert_sa);// 插入sa账号
		saAccount();// SA账号
	}

	protected void uAccount() throws InterruptedException {
		entryPage("管理中心", "员工账号");
		addUserAccount();// 增加员工账号
		serAccount("searchBtn");// 搜索新增的员工账号
		editAccount("j-editDepartUser", "editDeptUser");// 编辑员工账号
		resetPwd();// 重置密码
		seeDetail();// 查看详情
		delUser("j-deleteDepartUser", "shenpp@dina");// 删除新增的账号
		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索
	}

	protected void saAccount() throws InterruptedException {
		dr.findElement(By.linkText("SA人员管理")).click();
		sleep(1500);

		serAccount("searchSABtn");// 搜索插入的SA数据
		editAccount("j-editSaUser", "editSaUser");// 编辑SA账号
		delUser("j-deleteSaUser", "SA账号11");

		if (isElementExsit(dr, "//td[text()='SA账号11']"))
			fail();
	}

	private void addUserAccount() throws InterruptedException {
		dr.findElement(By.id("addAccountBtn")).click();
		sleep(2000);// 进入新增员工账号界面
		dr.findElement(By.id("userNameAdd")).sendKeys("shenpp");
		dr.findElement(By.id("userRealNameAdd")).sendKeys("张先森");
		dr.findElement(By.id("userPassAdd")).sendKeys("666666");
		dr.findElement(By.id("userEmailAdd")).sendKeys("778899212@qq.com");
		dr.findElement(By.id("userMobileAdd")).sendKeys("18361221575");
		dr.findElement(By.className("picAdd")).sendKeys(getTemplatePath("userphoto"));
		sleep(3000);

		if (!dr.findElement(By.xpath("//*[@id='addDeptUser']//tr[10]/td[2]/input[1]")).isSelected()) {
			dr.findElement(By.xpath("//*[@id='addDeptUser']//tr[10]/td[2]/input[1]")).click();
			sleep(1500);
		}
		closePrompt("addDeptUser", 1);// 点击确定，新建员工账号成功
		assertText1("添加用户账号成功");
		clickAlert("//*[@id='popup_ok']");// 点击新建成功的提示框
	}

	private void serAccount(String sid) throws InterruptedException {
		if (isElementExsit(dr, "//li[@class='active']/a[text()='员工账号']")) {
			dr.findElement(By.id("searchUserName")).sendKeys("shenpp");
			dr.findElement(By.id("searchEmail")).sendKeys("778899212@qq.com");
			dr.findElement(By.id("searchName")).sendKeys("张先森");
			dr.findElement(By.id("searchPhone")).sendKeys("18361221575");
		} else {
			dr.findElement(By.id("searchSAName")).sendKeys("SA账号11");
			dr.findElement(By.id("searchSAPhone")).sendKeys("18311112222");
		}

		sleep(2000);
		searchTest(sid, "pages");
	}

	private void editAccount(String eid, String edid) throws InterruptedException {
		clickAlert("//*[@name='" + eid + "']");// 进入编辑账号界面
		closePrompt(edid, 1);// 点击确定，编辑成功
		assertText1("修改成功");
		clickAlert("//*[@id='popup_ok']");// 点击新建成功的提示框
	}

	private void resetPwd() throws InterruptedException {
		clickAlert("//*[@name='j-resetDepartUserPass']");// 点击重置密码
		confirmAssert("是否将shenpp@dina的密码重置为初始密码？");// 校验提示语+点击确认
		assertText1("密码重置成功");
		clickAlert("//*[@id='popup_ok']");// 点击新建成功的提示框
	}

	private void seeDetail() throws InterruptedException {
		clickAlert("//*[@name='j-accountDetail']");
		assertText2("shenpp", "userNameInfo");
		assertText2("张先森", "userRealNameInfo");
		assertText2("778899212@qq.com", "userEmailInfo");
		assertText2("18361221575", "userMobileInfo");
		closePrompt("deptUserInfo", 1);// 校验完数据关闭弹框
	}

	private void delUser(String did, String cT) throws InterruptedException {
		clickAlert("//*[@name='" + did + "']");
		confirmAssert("是否决定删除" + cT + "用户?");
		assertText1("删除用户帐号成功");
		clickAlert("//*[@id='popup_ok']");// 点击新建成功的提示框
	}

	protected void clickAlert(String cx) throws InterruptedException {
		dr.findElement(By.xpath(cx)).click();
		sleep(2000);
	}

	protected void assertText2(String atext, String ppid) {
		assertEquals(atext, dr.findElement(By.id(ppid)).getAttribute("value"));
	}

	protected void assertText1(String atext) {
		assertEquals(atext, dr.findElement(By.id("popup_message")).getText());
	}

	protected void confirmAssert(String text) throws InterruptedException {
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);
	}
}
