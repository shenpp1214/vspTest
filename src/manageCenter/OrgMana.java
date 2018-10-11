package manageCenter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import baseService.BaseService;
import baseService.SeleniumConstants;

public class OrgMana extends BaseService {

	@Test
	public void orgMana() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_dept);// 删除机构
		entryPage("管理中心", "组织机构");// 进入页面
		newOrgMana();// 新增组织机构
		searchSee();// 搜索并校验数据的正确性
		modifyOraMana();// 修改新增的组织机构的联系人并校验新值
		seeDetail();// 查看组织机构详情
		delOrgMana();// 删除新增的组织机构
	}

	private void newOrgMana() throws InterruptedException {
		clickEle("//*[@id='orgAdd']", 2000);// 进入新增界面
		clickEle("//*[@id='parentNameAdd']", 1000);
		clickEle("//ul[@id='treeSelectAdd']/li/a/span", 2000);// 选择所属机构
		clearInp("departNameAdd", "shenpp专属机构");// 输入机构名称
		clearInp("departLinkerAdd", "沈培培");// 输入机构联系人
		clearInp("departTelAdd", "18361221575");// 输入联系人号码
		clearInp("displayOrderAdd", "2");// 输入排序

		dr.findElement(By.id("corplogo")).sendKeys(getTemplatePath("candy2"));// 上传图标
		sleep(4000);

		clickEle("//*[@id='jcrop_btn']", 9000);// 裁剪
		closePrompt("addDept", 1, 2000);// 点击确定新增成功
		assertEquals("添加成功", dr.findElement(By.id("popup_message")).getText());
		clickEle("//*[@id='popup_ok']", 1500);// 校验成功并确认
	}

	private void searchSee() throws InterruptedException {
		clearInp("searchDepartName", "shenpp专属机构");// 输入机构名称
		clearInp("searchContactPerson", "沈培培");// 输入机构联系人
		clearInp("searchContactPhone", "18361221575");// 输入联系人号码
		clearInp("searchDepart", "迪纳运营");
		clickEle("//*[@id='searchBtn']", 2000);

		assertEquals("shenpp专属机构", dr.findElement(By.xpath("//*[@id='mainContentList']//td[2]")).getText());
		assertEquals("迪纳运营", dr.findElement(By.xpath("//*[@id='mainContentList']//td[3]")).getText());
		assertEquals("沈培培", dr.findElement(By.xpath("//*[@id='mainContentList']//td[4]")).getText());
		assertEquals("18361221575", dr.findElement(By.xpath("//*[@id='mainContentList']//td[5]")).getText());
		assertEquals("组织部门", dr.findElement(By.xpath("//*[@id='mainContentList']//td[6]")).getText());
	}

	private void modifyOraMana() throws InterruptedException {
		clickEle("//*[@name='j-editDepartInfo']", 2000);// 进入编辑页面
		clearInp("departLinkerEdit", "申萍萍");
		closePrompt("editDept", 1, 2000);// 点击确定修改成功
		assertEquals("修改成功", dr.findElement(By.id("popup_message")).getText());
		clickEle("//*[@id='popup_ok']", 1500);// 校验成功并确认

		if (!isElementExsit(dr, "//*[@id='mainContentList']//td")) {
			clearInp("searchContactPerson", "申萍萍");
			clickEle("//*[@id='searchBtn']", 2000);

			assertEquals("申萍萍", dr.findElement(By.xpath("//*[@id='mainContentList']//td[4]")).getText());
		} else {
			fail();
		}
	}

	private void seeDetail() throws InterruptedException {
		clickEle("//*[@name='j-departDetail']", 3000);// 进入查看详情界面

		assertEquals("迪纳运营", dr.findElement(By.id("parentNameEdit")).getAttribute("value"));
		assertEquals("shenpp专属机构", dr.findElement(By.id("departNameEdit")).getAttribute("value"));
		assertEquals("申萍萍", dr.findElement(By.id("departLinkerEdit")).getAttribute("value"));
		assertEquals("18361221575", dr.findElement(By.id("departTelEdit")).getAttribute("value"));
		assertEquals("2", dr.findElement(By.id("displayOrderEdit")).getAttribute("value"));

		closePrompt("editDept", 1, 1500);// 关闭详情弹框
	}

	private void delOrgMana() throws InterruptedException {
		clickEle("//*[@name='j-delDepartInfo']", 1500);// 手动删除新增的组织机构
		clickEle("//div[@class='noty_buttons']/button[1]", 6000);
		assertEquals("删除成功", dr.findElement(By.id("popup_message")).getText());
		clickEle("//*[@id='popup_ok']", 1500);// 校验成功并确认

		if (isElementExsit(dr, "//*[@id='mainContentList']//td"))
			fail();
	}
}
