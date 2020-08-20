package com.kk.d.util.excel;

import java.util.List;

/**
 * Excel导入行回调
 *
 * @author Leach
 * @since 2016/3/22
 */
public interface ExcelImportRowCallback {

    /**
     * 行数据校验、保存
     *
     * @param row
     * @param cellValues
     * @return
     */
    ExcelImportRowResult checkAndSave(int row, List<Object> cellValues);

}
