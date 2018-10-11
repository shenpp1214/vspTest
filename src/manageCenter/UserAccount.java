package manageCenter;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class UserAccount extends BaseService {

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
		operateUser("编辑", null, "editDeptUser", "修改成功");// 编辑员工账号
		operateUser("密码重置", "是否将shenpp@dina的密码重置为初始密码？", null, "密码重置成功");// 重置密码
		seeDetail();// 查看详情
		operateUser("删除", "是否决定删除shenpp@dina用户?", null, "删除用户帐号成功");// 删除新增的账号
		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索
	}

	protected void saAccount() throws InterruptedException {
		clickEle("//a[text()='SA人员管理']", 1500);
		serAccount("searchSABtn");// 搜索插入的SA数据
		operateUser("编辑", null, "editSaUser", "修改成功");// 编辑SA账号
		operateUser("删除", "是否决定删除SA账号11用户?", null, "删除用户帐号成功");

		if (isElementExsit(dr, "//td[text()='SA账号11']"))
			fail();
	}

	private void addUserAccount() throws InterruptedException {
		clickEle("//*[@id='addAccountBtn']", 2000);// 进入新增员工账号界面
		clearInp("userNameAdd", "shenpp");
		clearInp("userRealNameAdd", "张先森");
		clearInp("userPassAdd", "666666");
		clearInp("userEmailAdd", "778899212@qq.com");
		clearInp("userMobileAdd", "18361221575");

		dr.findElement(By.className("picAdd")).sendKeys(getTemplatePath("userphoto"));
		sleep(3000);
		if (!dr.findElement(By.xpath("//*[@id='addDeptUser']//tr[10]/td[2]/input[1]")).isSelected()) {
			dr.findElement(By.xpath("//*[@id='addDeptUser']//tr[10]/td[2]/input[1]")).click();
			sleep(1500);
		}

		closePrompt("addDeptUser", 1, 2000);// 点击确定，新建员工账号成功
		assertEquals("添加用户账号成功", dr.findElement(By.id("popup_message")).getText());
		clickEle("//*[@id='popup_ok']", 1000);// 点击新建成功的提示框
	}

	private void serAccount(String sid) throws InterruptedException {
		if (isElementExsit(dr, "//li[@class='active']/a[text()='员工账号']")) {
			clearInp("searchUserName", "shenpp");
			clearInp("searchEmail", "778899212@qq.com");
			clearInp("searchName", "张先森");
			clearInp("searchPhone", "18361221575");
		} else {
			clearInp("searchSAName", "SA账号11");
			clearInp("searchSAPhone", "18311112222");
		}
		searchTest(sid, "pages");
	}

	private void operateUser(String t, String dT, String edid, String t1) throws InterruptedException {
		clickEle("//*[@title='" + t + "']", 1500);

		if (t == "编辑") {
			closePrompt(edid, 1, 2000);// 点击确定，编辑成功
		} else {
			assertEquals(dT, dr.findElement(By.className("noty_text")).getText());
			clickEle("//div[@class='noty_buttons']/button[1]", 2000);
		}
		assertEquals(t1, dr.findElement(By.id("popup_message")).getText());

		clickEle("//*[@id='popup_ok']", 1000);
	}

	private void seeDetail() throws InterruptedException {
		clickEle("//*[@name='j-accountDetail']", 1000);

		assertEquals("shenpp", dr.findElement(By.id("userNameInfo")).getAttribute("value"));
		assertEquals("张先森", dr.findElement(By.id("userRealNameInfo")).getAttribute("value"));
		assertEquals("778899212@qq.com", dr.findElement(By.id("userEmailInfo")).getAttribute("value"));
		assertEquals("18361221575", dr.findElement(By.id("userMobileInfo")).getAttribute("value"));
		closePrompt("deptUserInfo", 1, 1500);// 校验完数据关闭弹框
	}

	private void modData(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}
}
