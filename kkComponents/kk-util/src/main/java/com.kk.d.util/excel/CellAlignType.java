package com.kk.d.util.excel;


import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * Excel单元格内容对齐类型导出对齐类型
 *
 * @author Leach
 * @since 2016/6/13
 */
public enum CellAlignType {

    LEFT(HorizontalAlignment.LEFT),
    CENTER(HorizontalAlignment.CENTER),
    RIGHT(HorizontalAlignment.RIGHT);

    private HorizontalAlignment alignment;

    CellAlignType(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    public HorizontalAlignment getAlignment() {
        return alignment;
    }
}
