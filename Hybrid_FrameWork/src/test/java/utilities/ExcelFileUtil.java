package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil{
	XSSFWorkbook wb;
	//constructor for reading excel path
	public ExcelFileUtil(String Excelpath)throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = new XSSFWorkbook(fi);
	}
	// method for counting no of rows in a sheet
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	// method for reading cell data
	public String getCellData(String sheetname,int row,int coloum) 
		{
			String data = "";
			if(wb.getSheet(sheetname).getRow(row).getCell(coloum).getCellType()==CellType.NUMERIC)
			{
				int celldata =(int)wb.getSheet(sheetname).getRow(row).getCell(coloum).getNumericCellValue();
				data = String.valueOf(celldata);
			}
			else
			{
				data = wb.getSheet(sheetname).getRow(row).getCell(coloum).getStringCellValue();
			}
			return data;
		}
			// methood for writing data
			public void setCellData(String sheetName,int row,int coloum,String status,String WriteExcel)throws Throwable
			{
				// get sheet from the workbook
				XSSFSheet ws = wb.getSheet(sheetName);
				// get row from sheet
				XSSFRow rowNum = ws.getRow(row);
				// create cell
				XSSFCell cell = rowNum.createCell(coloum);
				// write status
				cell.setCellValue(status);
				if (status.equalsIgnoreCase("Pass")) 
				{
					XSSFCellStyle style = wb.createCellStyle();
					XSSFFont font = wb.createFont();
					font.setColor(IndexedColors.GREEN.getIndex());
					font.setBold(true);
					style.setFont(font);
					rowNum.getCell(coloum).setCellStyle(style);
				}
				else if (status.equalsIgnoreCase("Fail")) 
				{
					XSSFCellStyle style = wb.createCellStyle();
					XSSFFont font = wb.createFont();
					font.setColor(IndexedColors.RED.getIndex());
					font.setBold(true);
					style.setFont(font);
					rowNum.getCell(coloum).setCellStyle(style);
				}
				else if (status.equalsIgnoreCase("Blocked"))
				{
					XSSFCellStyle style = wb.createCellStyle();
					XSSFFont font = wb.createFont();
					font.setColor(IndexedColors.BLUE.getIndex());
					font.setBold(true);
					style.setFont(font);
					rowNum.getCell(coloum).setCellStyle(style);
				}
				FileOutputStream fo = new FileOutputStream(WriteExcel);
				wb.write(fo);
			}
	
				}

				
				

