package manageCenter;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class UserAccount extends BaseService {

	@Test
	public void userAccount() throws InterruptedException {
		entryPage("管理中心", "员工账号");
		addUserAccount();// 增加员工账号
		serUserAccount();// 搜索新增的员工账号
		editUserAccount();// 编辑员工账号
		resetPwd();// 重置密码
		seeDetail();// 查看详情
		deleteUser();// 删除新增的账号
	}

	private void addUserAccount() throws InterruptedException {
		dr.findElement(By.id("addAccountBtn")).click();
		sleep(2000);// 进入新增员工账号界面
		dr.findElement(By.id("userNameAdd")).sendKeys(SeleniumConstants.username);
		dr.findElement(By.id("userRealNameAdd")).sendKeys(SeleniumConstants.realname);
		dr.findElement(By.id("userPassAdd")).sendKeys(SeleniumConstants.Password);
		dr.findElement(By.id("userEmailAdd")).sendKeys(SeleniumConstants.Email);
		dr.findElement(By.id("userMobileAdd")).sendKeys(SeleniumConstants.mobile);
		dr.findElement(By.className("picAdd")).sendKeys(getTemplatePath("userphoto"));
		sleep(3000);

		if (!dr.findElement(By.xpath("//*[@id='addDeptUser']/table/tbody/tr[10]/td[2]/input[1]")).isSelected()) {
			dr.findElement(By.xpath("//*[@id='addDeptUser']/table/tbody/tr[10]/td[2]/input[1]")).click();
			sleep(1500);
		}
		closePrompt("addDeptUser", 1);// 点击确定，新建员工账号成功
		assertText1("添加用户账号成功");
		clickAlert();// 点击新建成功的提示框
	}

	private void serUserAccount() throws InterruptedException {
		dr.findElement(By.id("searchUserName")).sendKeys(SeleniumConstants.username);
		dr.findElement(By.id("searchEmail")).sendKeys(SeleniumConstants.Email);
		dr.findElement(By.id("searchName")).sendKeys(SeleniumConstants.realname);
		dr.findElement(By.id("searchPhone")).sendKeys(SeleniumConstants.mobile);

		searchTest("searchBtn", "pages");
	}

	private void editUserAccount() throws InterruptedException {
		moreOperate("j-editDepartUser");// 进入编辑账号界面
		closePrompt("editDeptUser", 1);// 点击确定，编辑成功
		assertText1("修改成功");
		clickAlert();// 点击新建成功的提示框
	}

	private void resetPwd() throws InterruptedException {
		moreOperate("j-resetDepartUserPass");// 进入编辑账号界面
		confirmAssert("是否将" + SeleniumConstants.username + "@dina的密码重置为初始密码？");// 校验提示语+点击确认
		assertText1("密码重置成功");
		clickAlert();// 点击新建成功的提示框
	}

	private void seeDetail() throws InterruptedException {
		moreOperate("j-accountDetail");
		assertText2(SeleniumConstants.username, "userNameInfo");
		assertText2(SeleniumConstants.realname, "userRealNameInfo");
		assertText2(SeleniumConstants.Email, "userEmailInfo");
		assertText2(SeleniumConstants.mobile, "userMobileInfo");
		assertText2(SeleniumConstants.username, "userNameInfo");
		closePrompt("deptUserInfo", 1);// 校验完数据关闭弹框
	}

	private void deleteUser() throws InterruptedException {
		moreOperate("j-deleteDepartUser");
		confirmAssert("是否决定删除" + SeleniumConstants.username + "@dina用户?");
		assertText1("删除用户帐号成功");
		clickAlert();// 点击新建成功的提示框
		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索
	}

	protected void clickAlert() throws InterruptedException {
		dr.findElement(By.id("popup_ok")).click();
		sleep(2000);
	}

	protected void assertText2(String atext, String ppid) {
		assertEquals(atext, dr.findElement(By.id(ppid)).getAttribute("value"));
	}

	protected void assertText1(String atext) {
		assertEquals(atext, dr.findElement(By.id("popup_message")).getText());
	}

	protected void moreOperate(String oname) throws InterruptedException {
		dr.findElement(By.name(oname)).click();
		sleep(2000);
	}

	protected void confirmAssert(String text) throws InterruptedException {
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();
		sleep(2000);
	}
}
