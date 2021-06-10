package com.ebuy.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.salama.service.core.ServiceErrorMsgException;
import com.salama.service.core.net.RequestWrapper;

/**
 * Excel 工具类
 *
 * @author 	Lian
 * @date 	2017/2/13
 * @since 	1.0
 */
public class ExcelUtil {

	/**
	 * 得到表单名称列表
	 *
	 * @param inputExcel
	 * @return
	 * @throws IOException
	 */
	public static List<String> getAllSheetNameList(InputStream inputExcel, boolean isXLSX) throws IOException {
		Workbook workbook = createWorkbook(inputExcel, isXLSX);

		List<String> sheetNameList = new ArrayList<String>();

		int sheetCount = workbook.getNumberOfSheets();

		for(int i = 0; i < sheetCount; i++) {
			sheetNameList.add(workbook.getSheetName(i));
		}

		return sheetNameList;
	}


	/**
	 * 读取行数据
	 *
	 * @param inputExcel	Excel文件数据
	 * @param isXLSX		是否是xlsx
	 * @param sheetIndex	表单索引
	 * @param beginCol		开始列
	 * @param endCol		结束列
	 * @param beginRow		开始行
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readRows(
			InputStream inputExcel, boolean isXLSX,
			int sheetIndex, int beginCol, int endCol, int beginRow) throws IOException {

		Workbook workbook = createWorkbook(inputExcel, isXLSX);

		Sheet sheet = workbook.getSheetAt(sheetIndex);

		int endRow = sheet.getLastRowNum();

		return readRows(sheet, beginCol, endCol, beginRow, endRow);
	}

	/**
	 *
	 * @param inputExcel
	 * @param isXLSX
	 * @param sheetIndex
	 * @param beginCol
	 * @param maxCol
	 * @param beginRow
	 * @return Object could be String, Boolean, Double, java.util.Date
	 * @throws IOException
	 */
	public static List<List<Object>> readRowsAutoDetectEndCol(
			InputStream inputExcel, boolean isXLSX, int sheetIndex,
			int beginCol, int maxCol, int beginRow) throws IOException {

		Workbook workbook = createWorkbook(inputExcel, isXLSX);

		Sheet sheet = workbook.getSheetAt(sheetIndex);

		return readRowsAutoDetectEndCol(sheet, beginCol, maxCol, beginRow);
	}

	public static List<List<Object>> readRowsAutoDetectEndCol(
			Sheet sheet,
			int beginCol, int maxCol, int beginRow) throws IOException {

		int endRow = sheet.getLastRowNum();
		int endCol = maxCol;
		Row row = null;
		Cell cell = null;
		Object cellVal = null;

		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

		row = sheet.getRow(beginRow);
		for (int j = 0; j < maxCol; j++) {
			cell = row.getCell(j);
			cellVal = getCellValue(evaluator, cell);

//			if(cellVal == null || (cellVal.getClass() == String.class && ((String)cellVal).length() == 0)) {
//				endCol = j - 1;
//				break;
//			}
		}

		return readRows(sheet, beginCol, endCol, beginRow, endRow);
	}

	public static List<Object> readRowAutoDetectEndCol(Sheet sheet,
													   int beginCol, int maxCol, int rowIndex) throws UnsupportedEncodingException {
		//int endCol = maxCol;

		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

		Row row = sheet.getRow(rowIndex);
		Cell cell = null;
		Object cellVal = null;
		List<Object> cellValList = new ArrayList<Object>();
		for (int j = 0; j < maxCol; j++) {
			cell = row.getCell(j);
			cellVal = getCellValue(evaluator, cell);

			if(cellVal == null || (cellVal.getClass() == String.class && ((String)cellVal).length() == 0)) {
				//endCol = j - 1;
				break;
			}

			cellValList.add(cellVal);
		}

		return cellValList;
	}

	public static Workbook createWorkbook(File excelFile, boolean isXLSX) throws IOException {
		InputStream inputExcel = null;
		try {
			inputExcel = new FileInputStream(excelFile);
			return createWorkbook(inputExcel, isXLSX);
		} finally {
			inputExcel.close();
		}
	}

	/**
	 * 创建工作簿
	 *
	 * @param inputExcel
	 * @param isXLSX
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWorkbook(InputStream inputExcel, boolean isXLSX) throws IOException {
		if (isXLSX) {
			return new XSSFWorkbook(inputExcel);
		} else {
			return new HSSFWorkbook(inputExcel);
		}
	}

	/**
	 * Include endCol and endRow
	 *
	 * @param sheet
	 * @param beginCol
	 * @param endCol
	 * @param beginRow
	 * @param endRow
	 * @return Object could be String, Boolean, Double, java.util.Date
	 * @throws UnsupportedEncodingException
	 */
	public static List<List<Object>> readRows(
			Sheet sheet,
			int beginCol, int endCol, int beginRow, int endRow) throws UnsupportedEncodingException {

		List<List<Object>> allRows = new ArrayList<List<Object>>();

		Row row = null;

		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

		int i, j;
		List<Object> cellValList = null;
		for(i = beginRow; i <= endRow; i++) {
			row = sheet.getRow(i);

			if(row == null) {
				continue;
			}

			//1 row
			cellValList = readRow(evaluator, row, beginCol, endCol);

			allRows.add(cellValList);
		}

		return allRows;
	}

	public static List<Object> readRow(
			FormulaEvaluator evaluator,
			Row row,
			int beginCol, int endCol) throws UnsupportedEncodingException {
		List<Object> cellValList = new ArrayList<Object>();
		Cell cell = null;
		Object cellVal = null;
		for(int j = beginCol; j <= endCol; j++) {
			cell = row.getCell(j);
			if(cell == null) {
				cellValList.add(null);
			} else {
				cellVal = getCellValue(evaluator, cell);
				cellValList.add(cellVal);
			}
		}

		return cellValList;
	}

	/**
	 *
	 * @param cell
	 * @return Object could be String, Boolean, Double, java.util.Date
	 * @throws UnsupportedEncodingException
	 */
	public static Object getCellValue(
			FormulaEvaluator evaluator,
			Cell cell) throws UnsupportedEncodingException {

		if(cell == null) {
			return null;
		}

		int cellType = evaluator.evaluateFormulaCell(cell);

		if (cellType == Cell.CELL_TYPE_STRING) {
			String value = new String(String.valueOf(cell.getStringCellValue()).getBytes(),"UTF-8");
			return value;
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {

			if (DateUtil.isCellDateFormatted(cell)) {

				return cell.getDateCellValue();
			} else {

				return cell.getNumericCellValue();
			}
		} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {

			return cell.getBooleanCellValue();
		} else if (cellType == Cell.CELL_TYPE_BLANK) {

			return null;
		} else if (cellType == Cell.CELL_TYPE_ERROR) {

			return null;
		} else {
			String value = new String(String.valueOf(cell.toString()).getBytes(),"UTF-8");

			return value;
		}
	}


	private final static String UPLOAD_FILE_PATH ="/WEB-INF/temp/";

	public static File getUploadTempFile(String fileName) {
		File path = new File(fileName);
		if (!path.exists()) {
			path.mkdirs();
		}
		return new File(path, Long.toString(System.currentTimeMillis()).concat(".xlsx"));
	}

	private static File getUploadDir(RequestWrapper request) {
		String dirPath = request.getServletContext().getRealPath(UPLOAD_FILE_PATH);
		return new File(dirPath);
	}

	public static void setDataToExcel2003(FileInputStream inStream, FileOutputStream outStream, List<List<String>> excelData, String sheetName, int startRow, int startColumn) throws ServiceErrorMsgException{
		HSSFWorkbook hssfWorkbook = null;
		HSSFSheet hssfSheet = null;
		HSSFRow hssfRow = null;
		HSSFCell hssfCell = null;
		try
		{
			if(inStream == null){
				hssfWorkbook = new HSSFWorkbook();
			}
			else{
				hssfWorkbook = new HSSFWorkbook(inStream);
			}

			if(!CommonUtil.isNullOrEmpty(sheetName)){
				hssfSheet = hssfWorkbook.getSheet(sheetName);
				if(hssfSheet == null){
					hssfSheet = hssfWorkbook.createSheet(sheetName);
				}
			}
			else{
				hssfSheet = hssfWorkbook.createSheet();
			}
			if(hssfSheet == null){
				throw new ServiceErrorMsgException();
			}

			for(int i = 0; i < excelData.size(); i++){
				hssfRow = hssfSheet.createRow(startRow + i);
				for(int j = 0; j < excelData.get(i).size(); j++){
					hssfCell = hssfRow.createCell(startColumn + j);
					hssfCell.setCellValue(excelData.get(i).get(j));
				}
			}

			hssfWorkbook.write(outStream);

		} catch (IOException e) {
			throw new ServiceErrorMsgException(e);
		} finally {

		}
	}

	public static void setDataToExcel2007(FileInputStream inStream, FileOutputStream outStream, List<List<String>> excelData, String sheetName, int startRow, int startColumn, Hashtable<Integer, Integer> numericColumn) throws ServiceErrorMsgException{
		SXSSFWorkbook workbook = null;
		SXSSFSheet sheet = null;
		Row row = null;
		Cell cell = null;
		try
		{
			if(inStream == null){
				workbook = new SXSSFWorkbook(new XSSFWorkbook());
			}else{
				workbook = new SXSSFWorkbook(new XSSFWorkbook(inStream));
			}

			workbook.setCompressTempFiles(true);

			if(!CommonUtil.isNullOrEmpty(sheetName)){
				sheet = (SXSSFSheet)workbook.getSheet(sheetName);
				if(sheet == null){
					sheet = (SXSSFSheet)workbook.createSheet(sheetName);
				}
			}
			else{
				sheet = (SXSSFSheet)workbook.createSheet();
			}

			if(sheet == null){
				throw new ServiceErrorMsgException();
			}

			((SXSSFSheet)sheet).setRandomAccessWindowSize(100);

			boolean flag = false;
			if (numericColumn != null ) {
				if (numericColumn.size() > 0) {
					flag = true;
				}
			}

			for(int i = 0; i < excelData.size(); i++){
				row = sheet.createRow(startRow + i);
				for(int j = 0; j < excelData.get(i).size(); j++){
					cell = row.createCell(startColumn + j);

					if (flag) {
						if (numericColumn.contains(j)
								&& !CommonUtil.isNullOrEmpty(excelData.get(i).get(j))
								&& CommonUtil.isAmount(excelData.get(i).get(j))){
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);

							cell.setCellValue(Double.parseDouble(excelData.get(i).get(j)));
						} else {
							cell.setCellValue(excelData.get(i).get(j));
						}
					} else {
						cell.setCellValue(excelData.get(i).get(j));
					}

				}
			}

			workbook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			throw new ServiceErrorMsgException(e);
		} finally {

		}
	}

	@SuppressWarnings("rawtypes")
	public static void setDataToExistExcel2007(FileInputStream inStream, FileOutputStream outStream, List<List<String>> excelData, String sheetName, int startRow, int startColumn, Hashtable numericColumn, Hashtable dateColumn, String dateFormat, Hashtable colorRow) throws ServiceErrorMsgException{
		SXSSFWorkbook workbook = null;
		SXSSFSheet sheet = null;
		Row row = null;
		Cell cell = null;
		try
		{
			if(inStream == null){
				workbook = new SXSSFWorkbook(new XSSFWorkbook());
			}else{
				workbook = new SXSSFWorkbook(new XSSFWorkbook(inStream));
			}

			workbook.setCompressTempFiles(true);

			if(!CommonUtil.isNullOrEmpty(sheetName)){
				sheet = (SXSSFSheet)workbook.getSheet(sheetName);
			}
			else {
				sheet = (SXSSFSheet)workbook.createSheet();
			}

			if(sheet == null){
				throw new ServiceErrorMsgException();
			}

			((SXSSFSheet)sheet).setRandomAccessWindowSize(100);

			boolean flag = false;
			if (numericColumn != null && numericColumn.size() > 0 ) {
				flag = true;
			} else if (dateColumn != null && dateColumn.size() > 0) {
				flag = true;
			}

			boolean colorFlag = false;
			if (colorRow != null && colorRow.size() > 0) {
				colorFlag = true;
			}

			CellStyle cellStyle = workbook.createCellStyle();// 建立新的cell样式
			DataFormat df = workbook.createDataFormat();
			if (!CommonUtil.isNullOrEmpty(dateFormat)) {
				cellStyle.setDataFormat(df.getFormat(dateFormat));// 设置cell样式为定制的日期格式
			}

			//填充背景色
			CellStyle cellStyleColor = workbook.createCellStyle();// 建立新的cell样式
			cellStyleColor.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyleColor.setFillForegroundColor(HSSFColor.YELLOW.index);

			for(int i = 0; i < excelData.size(); i++){
				row = sheet.createRow(startRow + i);
				for(int j = 0; j < excelData.get(i).size(); j++){
					cell = row.createCell(startColumn + j);
					//设置某些行高亮显示
					if (colorFlag) {
						if (colorRow != null && colorRow.contains(i)) {
							cell.setCellStyle(cellStyleColor);
						}
					}
					if (flag) {
						if (numericColumn != null && numericColumn.contains(j)
								&& !CommonUtil.isNullOrEmpty(excelData.get(i).get(j))){
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							cell.setCellValue(Double.parseDouble(excelData.get(i).get(j)));

						} else if (dateColumn != null && dateColumn.contains(j)
								&& !CommonUtil.isNullOrEmpty(excelData.get(i).get(j))){
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							cell.setCellStyle(cellStyle);
							cell.setCellValue(Integer.parseInt(excelData.get(i).get(j)));
						} else {
							cell.setCellValue(excelData.get(i).get(j));
						}
					} else {
						cell.setCellValue(excelData.get(i).get(j));
					}
				}
			}

			workbook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			throw new ServiceErrorMsgException(e);
		} finally {

		}
	}


}
