package com.manojkhannakm.materialswing;

import com.manojkhannakm.materialswing.style.MaterialColor;
import com.manojkhannakm.materialswing.style.MaterialImage;
import com.manojkhannakm.materialswing.swing.component.MaterialPanel;
import com.manojkhannakm.materialswing.swing.component.button.MaterialButton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Manoj Khanna
 */

public class Test3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            MaterialPanel panel = new MaterialPanel();
            panel.setMaterialWidth(400);
            panel.setMaterialHeight(400);
            frame.add(panel, BorderLayout.CENTER);

            MaterialButton button1 = new MaterialButton();
            button1.addActionListener(e -> System.out.println("Button 1"));
            button1.setMaterialX(150);
            button1.setMaterialY(100);
            button1.setMaterialScaleX(2.0f);
            button1.setMaterialScaleY(2.0f);
            button1.setMaterialRotation(0.125f);
            button1.setMaterialColor(MaterialColor.BLUE_500);
            button1.setOverlayColor(MaterialColor.WHITE.withAlpha(0.4f));
            button1.setRippleColor(MaterialColor.WHITE.withAlpha(0.4f));
            button1.setTextColor(MaterialColor.WHITE);
//            button1.setEnabled(false);
            panel.add(button1);

            MaterialButton button2 = new MaterialButton();
            button2.addActionListener(e -> System.out.println("Button 2"));
            button2.setMaterialX(100);
            button2.setMaterialY(200);
            button2.setMaterialZ(0);
            button2.setMaterialColor(MaterialColor.TRANSPARENT);
            button2.setText("MANOJ KHANNA");
            panel.add(button2);

            MaterialButton button3 = new MaterialButton();
            button3.addActionListener(e -> System.out.println("Button 3"));
            button3.setMaterialX(150);
            button3.setMaterialY(300);
            button3.setMaterialZ(6);
            button3.setMaterialWidth(56);
            button3.setMaterialHeight(56);
            button3.setMaterialColor(MaterialColor.PINK_A400);
            button3.setMaterialShape(new Ellipse2D.Float());
            button3.setOverlayColor(MaterialColor.WHITE.withAlpha(0.4f));
            button3.setRippleColor(MaterialColor.WHITE.withAlpha(0.4f));
            button3.setImage(MaterialImage.WINDOW_BACK);
//            button3.setImageScaleX(0.5f);
//            button3.setImageScaleY(0.5f);
//            button3.setImageColor(MaterialColor.INDIGO_500);
            panel.add(button3);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
