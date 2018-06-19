/*******************************************************************************
 *                                                                              
 *  COPYRIGHT (C) 2016 JWRJ Limited - ALL RIGHTS RESERVED.                  
 *                                                                                                                                 
 *  Creation Date: 2016年6月6日                                                      
 *                                                                              
 *******************************************************************************/

package baseService.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import baseService.PropertiesReader;

/**
 * @author shenpp
 *
 */
public class SeleniumExcel1 { 
	/**
	 * 工作薄对象
	 */
	private HSSFWorkbook hwb;
	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	public SeleniumExcel1(File file, String sheetName) throws Exception {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			this.hwb = new HSSFWorkbook(fis);
		} catch (IOException e) {
			throw new IOException("文件 " + file.getName() + "不存在");
		}

		this.sheet = this.hwb.getSheet(sheetName);
		if (this.sheet == null) {
			throw new Exception("sheet " + sheetName + "不存在");
		}
	}

	public String getCellValue(int row, int column) throws Exception {
		if (row > sheet.getLastRowNum()) {
			throw new Exception("sheet中行数少于row");
		}

		Row r = sheet.getRow(row);
		Cell cell = r.getCell(column);
		return ExcelUtils.getCellValue(sheet, cell);
	}

	public HSSFWorkbook gethwb() {
		return hwb;
	}

	public void setHwb(HSSFWorkbook hwb) {
		this.hwb = hwb;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public static void main(String args[]) throws Exception {
		Properties props = new PropertiesReader().load();
		String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "template"
				+ System.getProperty("file.separator") + props.getProperty("organization_import_addr");
		SeleniumExcel1 excel = new SeleniumExcel1(new File(path), "单位信息数据表");
		System.out.println(excel.getCellValue(3, 3));
	}
}
