package com.manojkhannakm.materialswing;

import com.manojkhannakm.materialswing.layout.MaterialLayout;
import com.manojkhannakm.materialswing.swing.component.MaterialSheet;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class Test1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel sliderPanel = new JPanel();
            sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
            frame.add(sliderPanel, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            panel.setLayout(new MaterialLayout());
            panel.setPreferredSize(new Dimension(400, 400));
            frame.add(panel);

            MaterialSheet sheet = new MaterialSheet();
            sheet.setMaterialX(100);
            sheet.setMaterialY(100);
            panel.add(sheet);

            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("X", 0, 400, 150, e -> sheet.setMaterialX(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Y", 0, 400, 150, e -> sheet.setMaterialY(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Z", 0, 24, 2, e -> sheet.setMaterialZ(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Width", 0, 200, 100, e -> sheet.setMaterialWidth(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Height", 0, 200, 100, e -> sheet.setMaterialHeight(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Translation X", -100, 100, 0, e -> sheet.setMaterialTranslationX(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Translation Y", -100, 100, 0, e -> sheet.setMaterialTranslationY(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Translation Z", -24, 24, 0, e -> sheet.setMaterialTranslationZ(((JSlider) e.getSource()).getValue())));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Pivot X", 0, 100, 50, e -> sheet.setMaterialPivotX(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Pivot Y", 0, 100, 50, e -> sheet.setMaterialPivotY(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Scale X", 0, 200, 100, e -> sheet.setMaterialScaleX(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Scale Y", 0, 200, 100, e -> sheet.setMaterialScaleY(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Rotation", 0, 100, 0, e -> sheet.setMaterialRotation(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));
            sliderPanel.add(getPropertyPanel("Alpha", 0, 100, 100, e -> sheet.setMaterialAlpha(((JSlider) e.getSource()).getValue() / 100.0f)));
            sliderPanel.add(Box.createVerticalStrut(8));

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JPanel getPropertyPanel(String name, int minValue, int maxValue, int value, ChangeListener listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(Box.createHorizontalStrut(8));

        panel.add(new JLabel(name));

        panel.add(Box.createHorizontalStrut(8));

        JSlider slider = new JSlider(minValue, maxValue, value);
        slider.addChangeListener(listener);
        panel.add(slider);

        panel.add(Box.createHorizontalStrut(8));

        return panel;
    }

}
