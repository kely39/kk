package com.kk.d.util.excel;


/**
 * Excel数据加载回调
 *
 * @author Leach
 * @since 2016/6/13
 */
public abstract class ExcelExportRecordLoadCallback implements ExcelExportRecordLoadCallbackInteface {


    @Override
    public int[] getCellWidths() {
        return new int[getCellTitles().length];
    }

    @Override
    public CellAlignType[] getCellAlignTypes() {
        CellAlignType[] cellAlignTypes = new CellAlignType[getCellTitles().length];
        for (int i = 0; i < cellAlignTypes.length; i++) {
            cellAlignTypes[i] = CellAlignType.LEFT; // 默认左对齐
        }
        return cellAlignTypes;
    }
}
