package com.kk.d.util.excel;

/**
 * Excel导出校验异常类
 *
 * @author Leach
 * @since 2016/6/13
 */
public class ExcelExportVerifyException extends RuntimeException {

    public ExcelExportVerifyException() {
        super();
    }

    public ExcelExportVerifyException(String message) {
        super(message);
    }

    public ExcelExportVerifyException(ExcelImportRowResult excelImportRowResult) {
        super(excelImportRowResult.getMessage());
    }

    public ExcelExportVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelExportVerifyException(Throwable cause) {
        super(cause);
    }
}