package com.manojkhannakm.materialswing;

import com.manojkhannakm.materialswing.layout.MaterialLayout;
import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.swing.component.MaterialSheet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class Test2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JSlider slider = new JSlider(0, 360, 0);
            frame.add(slider, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new MaterialLayout());
            panel.setPreferredSize(new Dimension(600, 600));
            frame.add(panel);

            MaterialSheet sheet1 = new MaterialSheet();
            sheet1.setLayout(new MaterialLayout());
            sheet1.setMaterialX(150);
            sheet1.setMaterialY(150);
            sheet1.setMaterialZ(10);
            sheet1.setMaterialWidth(300);
            sheet1.setMaterialHeight(300);
            sheet1.setMaterialPivotX(0.0f);
            sheet1.setMaterialPivotY(0.0f);
//            sheet1.setMaterialScaleX(2.0f);
//            sheet1.setMaterialScaleY(2.0f);
            sheet1.setMaterialColor(MaterialColor.BLUE_900);
//            sheet1.setMaterialShape(new Ellipse2D.Float());
            sheet1.setOverlayColor(MaterialColor.PINK_900.withAlpha(0.75f));
            panel.add(sheet1);

            MaterialSheet sheet2 = new MaterialSheet();
            sheet2.setLayout(new MaterialLayout());
            sheet2.setMaterialX(50);
            sheet2.setMaterialY(50);
            sheet2.setMaterialZ(10);
            sheet2.setMaterialWidth(200);
            sheet2.setMaterialHeight(200);
            sheet2.setMaterialPivotX(0.0f);
            sheet2.setMaterialPivotY(0.0f);
//            sheet2.setMaterialScaleX(0.5f);
//            sheet2.setMaterialScaleY(0.5f);
            sheet2.setMaterialColor(MaterialColor.BLUE_700);
//            sheet2.setMaterialShape(new Ellipse2D.Float());
            sheet2.setOverlayColor(MaterialColor.PINK_700.withAlpha(0.75f));
            sheet1.add(sheet2);

            MaterialSheet sheet3 = new MaterialSheet();
            sheet3.setLayout(new MaterialLayout());
            sheet3.setMaterialX(50);
            sheet3.setMaterialY(50);
            sheet3.setMaterialZ(10);
            sheet3.setMaterialWidth(100);
            sheet3.setMaterialHeight(100);
            sheet3.setMaterialPivotX(0.0f);
            sheet3.setMaterialPivotY(0.0f);
            sheet3.setMaterialColor(MaterialColor.BLUE_500);
//            sheet3.setMaterialShape(new Ellipse2D.Float());
            sheet3.setOverlayColor(MaterialColor.PINK_500.withAlpha(0.75f));
            sheet2.add(sheet3);

            slider.addChangeListener(e -> {
                int value = slider.getValue();

                sheet1.setMaterialRotation(value / 360.0f);
                sheet2.setMaterialRotation(value / 360.0f * 2.0f);
                sheet3.setMaterialRotation(value / 360.0f * 3.0f);
            });

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            new Timer(16, e -> {
                sheet1.repaint();
                sheet2.repaint();
                sheet3.repaint();
            }).start();
        });
    }

}
