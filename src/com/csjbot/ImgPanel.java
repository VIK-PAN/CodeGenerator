package com.csjbot;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 *
 * @Title: ImgPanel.java
 * @Project: csjbot-code-generator
 * @Package: com.csjbot
 * @Description: 扩展swing自带的JPanel使得其能插入图片
 * @Company: 苏州穿山甲机器人股份有限公司
 * @author: 钟磊
 * @date: 2018年3月27日 下午5:13:32
 * @version: V1.0.0
 */
public class ImgPanel extends JPanel {

	private static final long serialVersionUID = -8638106397500756721L;

	private Image img;

	public ImgPanel(Image img) {
		this.img = img;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.img, 0x0, 0x0, this.getWidth(), this.getHeight(), this);
	}

}
