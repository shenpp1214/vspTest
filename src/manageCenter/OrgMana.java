package manageCenter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import baseService.BaseService;
import baseService.SeleniumConstants;

public class OrgMana extends BaseService {
	String sql = "DELETE FROM iov.corp_dept where DEPT_NAME='" + SeleniumConstants.ORANAME + "';";

	@Test
	public void orgMana() throws Exception {
		entryPage("管理中心", "组织机构");// 进入页面
		newOrgMana();// 新增组织机构
		searchSee();// 搜索并校验数据的正确性
		modifyOraMana();// 修改新增的组织机构的联系人并校验新值
		seeDetail();// 查看组织机构详情
		delOrgMana();// 删除新增的组织机构
		delData();
	}

	private void newOrgMana() throws InterruptedException {
		dr.findElement(By.id("orgAdd")).click();
		sleep(2000);// 进入新增界面
		dr.findElement(By.id("parentNameAdd")).click();
		dr.findElement(By.xpath("//input[@id='parentNameAdd']/following-sibling::div//li/a/span")).click();// 选择调入机构
		sleep(2000);// 选择所属机构
		dr.findElement(By.id("departNameAdd")).sendKeys(SeleniumConstants.ORANAME);// 输入机构名称
		dr.findElement(By.id("departLinkerAdd")).sendKeys(SeleniumConstants.ORALINKEDPER);// 输入机构联系人
		dr.findElement(By.id("departTelAdd")).sendKeys(SeleniumConstants.PERPHONE);// 输入联系人号码
		dr.findElement(By.id("displayOrderAdd")).sendKeys(SeleniumConstants.sort);// 输入排序
		sleep(2000);
		dr.findElement(By.id("corplogo")).sendKeys(getTemplatePath("candy2"));// 上传图标
		sleep(4000);
		dr.findElement(By.id("jcrop_btn")).click();// 裁剪
		sleep(9000);

		closePrompt("addDept", 1);// 点击确定新增成功
		assertAndConfim("添加成功");// 校验成功并确认
	}

	private void searchSee() throws InterruptedException {
		dr.findElement(By.id("searchDepartName")).sendKeys(SeleniumConstants.ORANAME);
		dr.findElement(By.id("searchContactPerson")).sendKeys(SeleniumConstants.ORALINKEDPER);
		dr.findElement(By.id("searchContactPhone")).sendKeys(SeleniumConstants.PERPHONE);
		dr.findElement(By.id("searchDepart")).sendKeys("迪纳运营");
		sleep(2000);
		dr.findElement(By.id("searchBtn")).click();// 搜索
		sleep(2000);

		if (!isElementExsit(dr, "//*[@id='mainContentList']/table/tbody/tr[2]")) {
			assertEquals(SeleniumConstants.ORANAME,
					dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[2]")).getText());
			assertEquals("迪纳运营", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[3]")).getText());
			assertEquals(SeleniumConstants.ORALINKEDPER,
					dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[4]")).getText());
			assertEquals(SeleniumConstants.PERPHONE,
					dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[5]")).getText());
			assertEquals("组织部门", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[6]")).getText());
		} else {
			fail();
		}
	}

	private void modifyOraMana() throws InterruptedException {
		dr.findElement(By.name("j-editDepartInfo")).click();// 进入编辑页面
		sleep(2000);
		dr.findElement(By.id("departLinkerEdit")).clear();// 清除原本的值
		dr.findElement(By.id("departLinkerEdit")).sendKeys(SeleniumConstants.modper);// 重新输入联系人
		sleep(2000);

		closePrompt("editDept", 1);// 点击确定修改成功
		assertAndConfim("修改成功");// 校验成功并确认

		if (!isElementExsit(dr, "//*[@id='mainContentList']/table/tbody/tr")) {
			dr.findElement(By.id("searchContactPerson")).clear();// 清除原本的值
			dr.findElement(By.id("searchContactPerson")).sendKeys(SeleniumConstants.modper);// 重新输入联系人
			dr.findElement(By.id("searchBtn")).click();
			sleep(2000);

			assertEquals(SeleniumConstants.modper,
					dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[4]")).getText());
		} else {
			fail();
		}
	}

	private void seeDetail() throws InterruptedException {
		dr.findElement(By.name("j-departDetail")).click();// 进入查看详情界面
		sleep(3000);

		assertEquals("迪纳运营", dr.findElement(By.id("parentNameEdit")).getAttribute("value"));
		assertEquals(SeleniumConstants.ORANAME, dr.findElement(By.id("departNameEdit")).getAttribute("value"));
		assertEquals(SeleniumConstants.modper, dr.findElement(By.id("departLinkerEdit")).getAttribute("value"));
		assertEquals(SeleniumConstants.PERPHONE, dr.findElement(By.id("departTelEdit")).getAttribute("value"));
		assertEquals(SeleniumConstants.sort, dr.findElement(By.id("displayOrderEdit")).getAttribute("value"));

		closePrompt("editDept", 1);// 关闭详情弹框
	}

	private void delOrgMana() throws InterruptedException {
		dr.findElement(By.name("j-delDepartInfo")).click();// 手动删除新增的组织机构
		sleep(2000);
		dr.findElement(By.xpath("//div[@class='noty_buttons']/button[1]")).click();// 点击确定
		sleep(6000);

		assertAndConfim("删除成功");// 校验删除成功
	}

	protected void assertAndConfim(String text) throws InterruptedException {
		assertEquals(text, dr.findElement(By.id("popup_message")).getText());

		dr.findElement(By.id("popup_ok")).click();
		sleep(2000);
	}

	protected void delData() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", sql);
	}
}
