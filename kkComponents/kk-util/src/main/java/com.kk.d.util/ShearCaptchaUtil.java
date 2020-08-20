package com.kk.d.util;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 纯数字验证码
 *
 * @author yangqh
 * @date 2019/12/27
 **/
public class ShearCaptchaUtil extends AbstractCaptcha {
    private static final long serialVersionUID = -5793083610745474459L;


    public static ShearCaptchaUtil createShearCaptcha(int width, int height) {
        return new ShearCaptchaUtil(width, height, 4);
    }


    public static ShearCaptchaUtil createShearCaptcha(int width, int height, int codeCount) {
        return new ShearCaptchaUtil(width, height, codeCount, 4);
    }

    public static ShearCaptchaUtil createShearCaptcha(int width, int height, int codeCount, int thickness) {
        return new ShearCaptchaUtil(width, height, codeCount, thickness);
    }

    public ShearCaptchaUtil(int width, int height) {
        this(width, height, 4);
    }

    public ShearCaptchaUtil(int width, int height, int codeCount) {
        this(width, height, codeCount, 4);
    }

    public ShearCaptchaUtil(int width, int height, int codeCount, int thickness) {
        super(width, height, new RandomGenerator("0123456789", codeCount), thickness);
    }


    @Override
    protected Image createImage(String s) {
        BufferedImage image = new BufferedImage(this.width, this.height, 1);
        Graphics2D g = ImageUtil.createGraphics(image, (Color) ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        g.setFont(this.font);
        int len = this.generator.getLength();
        int charWidth = this.width / (len + 2);

        for (int i = 0; i < len; ++i) {
            g.setColor(ImageUtil.randomColor());
            g.drawString(String.valueOf(code.charAt(i)), (i + 1) * charWidth, this.height - 4);
        }

        this.shear(g, this.width, this.height, Color.white);
        this.drawThickLine(g, 0, RandomUtil.randomInt(this.height) + 1, this.width, RandomUtil.randomInt(this.height) + 1, this.interfereCount, ImageUtil.randomColor());
        return image;
    }


    private void shear(Graphics g, int w1, int h1, Color color) {
        this.shearX(g, w1, h1, color);
        this.shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = RandomUtil.randomInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = RandomUtil.randomInt(2);

        for (int i = 0; i < h1; ++i) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = RandomUtil.randomInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;

        for (int i = 0; i < w1; ++i) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }

    }

    private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {
        g.setColor(c);
        int dX = x2 - x1;
        int dY = y2 - y1;
        double lineLength = Math.sqrt((double) (dX * dX + dY * dY));
        double scale = (double) thickness / (2.0D * lineLength);
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        ddx += ddx > 0.0D ? 0.5D : -0.5D;
        ddy += ddy > 0.0D ? 0.5D : -0.5D;
        int dx = (int) ddx;
        int dy = (int) ddy;
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        xPoints[0] = x1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;
        g.fillPolygon(xPoints, yPoints, 4);
    }
}
