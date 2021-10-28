package self.camel.demo;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
@Component("xlsMetaDataProcessor")
public class XlsMetaDataProcessor implements Processor {
	private static final Logger LOG = LoggerFactory.getLogger(XlsMetaDataProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		InputStream is = exchange.getIn().getBody(InputStream.class);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow firstRow = sheet.getRow(0);
		LOG.debug("Xls first row:{}",firstRow.toString());
	    String processingFileName = exchange.getIn().getHeader("CamelFileNameOnly",String.class);
	    String tableName = processingFileName.substring(0,processingFileName.lastIndexOf("."));
	    TableMetaDataBean metaDataBean = new TableMetaDataBean(processingFileName);
	    metaDataBean.setTableName(tableName.toUpperCase());
        if(firstRow != null) {
            Iterator<Cell> cells = firstRow.cellIterator();
            while (cells.hasNext()) {
            	XSSFCell cell = (XSSFCell) cells.next();
                int type = cell.getCellType();
                LOG.debug("Xls first row got cell type:{}",type);
                if (type == Cell.CELL_TYPE_STRING) {
                	LOG.debug("[{},{}] = STRING; Value = {}",
                            cell.getRowIndex(), cell.getColumnIndex(),
                            cell.getRichStringCellValue().toString());
                    SqlColumn sqlColumn = new SqlColumn();
                    String headerName = cell.getRichStringCellValue().toString();
                    sqlColumn.setColumnName(headerName+" "
                    		);
                    sqlColumn.setColumnType(SqlColumnType.VARCHAR.getType());
                    metaDataBean.addSqlColumns(sqlColumn);
                    
                } else if (type == Cell.CELL_TYPE_NUMERIC) {
                	LOG.debug("[{},{}] = NUMERIC; Value = {}",
                            cell.getRowIndex(), cell.getColumnIndex(),
                            cell.getRichStringCellValue().toString());
                    SqlColumn sqlColumn = new SqlColumn();
                    String headerName = cell.getRichStringCellValue().toString();
                    sqlColumn.setColumnName(headerName+" ");
                    sqlColumn.setColumnType(SqlColumnType.INT.getType());
                    metaDataBean.addSqlColumns(sqlColumn);
                } else if (type == Cell.CELL_TYPE_BOOLEAN) {
                	LOG.debug("[{},{}] = BOOLEAN; Value = {}",
                            cell.getRowIndex(), cell.getColumnIndex(),
                            cell.getRichStringCellValue().toString());
                } else if (type == Cell.CELL_TYPE_BLANK) {
                	LOG.debug("[{},{}] = BLANK CELL%n",
                            cell.getRowIndex(), cell.getColumnIndex());
                }
            }
        }  
        Gson gson = new Gson();
        String json = gson.toJson(metaDataBean);
        LOG.debug("Table Metadata JSON:{}",json);
        exchange.getIn().setBody(metaDataBean);
    }
}

