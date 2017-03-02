package com.zhengjin.apis.testutils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;


public final class FileUtils {
	
	private static final int BUFFER_SIZE = 1000;
	
	public static String getProjectPath() {
		String path = FileUtils.class.getResource("/").getPath();
		if (path == null || path.length() == 0) {
			Assert.assertTrue("Error, the project path is null or empty!", false);
		}
		
		path = path.substring(1, (path.length() - 1));
		return path.substring(0, path.lastIndexOf("/"));
	}

	// read content from TXT file
	public static String readFileContent(String path) {
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder(BUFFER_SIZE);
		
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, TestConstants.CHARSET_UFT8);
			reader = new BufferedReader(isr);
			
			String tempStr = null;
			while((tempStr = reader.readLine()) != null) {
				content.append(tempStr);
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format("Error, IOException(%s) when read file %s", 
					e.getMessage(), path), false);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
	
	public static List<List<String>> readExcelRows(String filePath, String sheetName) {
		FileInputStream fis = null;
		Workbook excelWorkbook = null;
		List<List<String>> rows = null;
		
		try {
			fis = new FileInputStream(filePath);
			excelWorkbook = new XSSFWorkbook(fis);
			Sheet sheet = excelWorkbook.getSheet(sheetName);

			rows = new ArrayList<>();
			for (int i = 0, lastRowNum = sheet.getLastRowNum(); i < lastRowNum; i++) {
				Row row = sheet.getRow(i);
				String tempStr = row.getCell(TestConstants.COL_RUN_FLAG).getStringCellValue().trim();
				
				if (tempStr != null && "Y".equals(tempStr.toUpperCase())) {
					List<String> cells = new ArrayList<String>();
					for (int j = 0, lastCellNum = row.getLastCellNum(); j < lastCellNum; j++) {
						cells.add(row.getCell(j).getStringCellValue().trim());
					}
					rows.add(cells);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format("Error, the file(%s) is NOT exist!", filePath), false);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format(
					"Error, the IOException(%s) when read excel file %s", 
					e.getMessage(), filePath), false);
		} finally {
			try {
				if (excelWorkbook != null) {
					excelWorkbook.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (rows.size() == 0) {
			Assert.assertTrue(String.format(
					"Error, test cases count is 0 in file(%s) and sheet(%s)!", 
					filePath, sheetName), false);
		}
		return rows;
	}
	
	public static List<String> getSpecifiedRow(List<List<String>> rows, String caseId) {
		for (List<String> row : rows) {
			if (row.get(TestConstants.COL_CASE_ID).trim().equals(caseId)) {
				return row;
			}
		}

		Assert.assertTrue(String.format("Error, the test case(%s) is NOT found!", caseId), false);
		return Collections.emptyList();
	}
	
}
