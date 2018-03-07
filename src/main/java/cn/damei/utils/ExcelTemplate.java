package cn.damei.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public final class ExcelTemplate {
	/**
	 * 数据行标识
	 */
	public final static String DATA_LINE = "datas";
	/**
	 * 默认样式标识
	 */
	public final static String DEFAULT_STYLE = "defaultStyles";
	/**
	 * 行样式标识
	 */
	public final static String STYLE = "styles";
	/**
	 * 插入序号样式标识
	 */
	public final static String SER_NUM = "sernums";
	private Workbook wb;
	private Sheet sheet;
	/**
	 * 数据的初始化列数
	 */
	private int dataInitColIndex;
	/**
	 * 数据的初始化行数
	 */
	private int dataInitRowIndex;
	/**
	 * 当前列数
	 */
	private int curColIndex;
	/**
	 * 当前行数
	 */
	private int curRowIndex;
	/**
	 * 当前行对象
	 */
	private Row curRow;
	/**
	 * 最后一行的数据
	 */
	private int lastRowIndex;
	/**
	 * 默认样式 ,如果没有设置默认样式，则默认样式就是datas单元格的样式
	 */
	private CellStyle defaultStyle;

	/**
	 * 默认行高，等于datas单元格所在行的行高
	 */
	private float defaultRowHeight;
	/**
	 * 存储各列所对于的样式,可以分别设置各列的样式
	 */
	private Map<Integer, CellStyle> colStyleMap;
	/**
	 * 序号的列
	 */
	private int serColIndex;

	//1、读取相应的模板文档
	/**
	 * 从classpath路径下读取相应的模板文件
	 */
	public ExcelTemplate readTemplateByClasspath(String clsPath) {
		try {
			wb = WorkbookFactory.create(getClass().getClassLoader().getResourceAsStream(clsPath));
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板格式有错，！请检查");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板不存在！请检查");
		}
		return this;
	}

	/**
	 * 将文件写到相应的路径下
	 * 
	 * @param filepath
	 */
	public void writeToFile(String filepath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filepath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("写入的文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入数据失败:" + e.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将文件写到某个输出流中
	 * 
	 * @param os
	 */
	public void wirteToStream(OutputStream os) {
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入流失败:" + e.getMessage());
		}
	}

	/**
	 * 从某个路径来读取模板
	 * 
	 * @param fileAbsolutePath
	 */
	public ExcelTemplate readTemplateByPath(String fileAbsolutePath) {
		try {
			File file = new File(fileAbsolutePath);
			wb = WorkbookFactory.create(file);
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板格式有错，！请检查");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板不存在！请检查");
		}
		return this;
	}

	/**
	 * 创建相应的元素，基于String类型
	 * 
	 * @param value
	 */
	public void createCell(String value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_STRING);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(int value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(Date value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(double value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(boolean value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		c.setCellValue(value);
		curColIndex++;
	}

	public void createCell(Calendar value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}

	/**
	 * 设置某个元素的样式
	 * 
	 * @param c
	 */
	private void setCellStyle(Cell c) {
		if (colStyleMap.containsKey(curColIndex)) {
			c.setCellStyle(colStyleMap.get(curColIndex));
		} else {
			c.setCellStyle(defaultStyle);
		}
	}

	/**
	 * 创建新行，在使用时只要添加完一行，需要调用该方法创建
	 */
	public void createNewRow() {
		if (lastRowIndex > curRowIndex && curRowIndex != dataInitRowIndex) {
			sheet.shiftRows(curRowIndex, lastRowIndex, 1, true, true);
			lastRowIndex++;
		}
		curRow = sheet.createRow(curRowIndex);
		curRow.setHeightInPoints(defaultRowHeight);
		curRowIndex++;
		curColIndex = dataInitColIndex;
	}

	/**
	 * 如果需要插入序号，则最后调用此方法、会自动找相应的序号标示的位置完成插入
	 */
	public void insertSerFinally() {
		int seq = 1;
		Row row = null;
		Cell c = null;
		for (int i = dataInitRowIndex; i < curRowIndex; i++) {
			row = sheet.getRow(i);
			c = row.createCell(serColIndex);
			setCellStyle(c);
			c.setCellValue(seq++);
		}
	}

	/**
	 * 根据map替换相应的常量，通过Map中的值来替换#开头的值
	 * 
	 * @param datas
	 */
	public void replaceFinalData(Map<String, String> datas) {
		if (datas != null) {
			Properties props = new Properties();
			props.putAll(datas);
			replaceFinalData(props);
		}
	}

	/**
	 * 基于Properties的替换，依然也是替换#开始的
	 * 
	 * @param prop
	 */
	public void replaceFinalData(Properties prop) {
		if (prop != null) {
			for (Row row : sheet) {
				for (Cell c : row) {
					if (c.getCellType() != Cell.CELL_TYPE_STRING)
						continue;
					String str = c.getStringCellValue().trim();
					if (str.startsWith("#")) {
						String replacekey = str.substring(1);
						if (prop.containsKey(replacekey)) {
							c.setCellValue(prop.getProperty(replacekey));
						}
					}
				}

			}
		}
	}

	private void initTemplate() {
		sheet = wb.getSheetAt(0);
		initConfigData();
		lastRowIndex = sheet.getLastRowNum();
	}

	/**
	 * 初始化数据信息
	 */
	private void initConfigData() {
		boolean findData = false;
		boolean findSer = false;
		for (Row row : sheet) {
			if (findData)
				break;
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
					findSer = true;
				} else if (str.equals(DATA_LINE)) {
					dataInitColIndex = c.getColumnIndex();
					dataInitRowIndex = row.getRowNum();
					curColIndex = dataInitColIndex;
					curRowIndex = dataInitRowIndex;
					defaultStyle = c.getCellStyle();
					defaultRowHeight = row.getHeightInPoints();
					findData = true;
					initStyles();
					break;
				}
			}
		}
		if (!findSer) {
			initSer();
		}
	}

	/**
	 * 初始化序号位置
	 */
	private void initSer() {
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
				}
			}
		}
	}

	/**
	 * 初始化样式信息
	 */
	private void initStyles() {
		colStyleMap = new HashMap<Integer, CellStyle>();
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(DEFAULT_STYLE)) {
					defaultStyle = c.getCellStyle();
				} else if (str.equals(STYLE)) {
					colStyleMap.put(c.getColumnIndex(), c.getCellStyle());
				}
			}
		}
	}
}
