package com.manojkhannakm.materialswing;

import com.manojkhannakm.materialswing.swing.component.MaterialLabel;
import com.manojkhannakm.materialswing.swing.component.MaterialPanel;
import com.manojkhannakm.materialswing.swing.component.button.MaterialButton;
import com.manojkhannakm.materialswing.swing.window.MaterialFrame;

import javax.swing.*;

/**
 * @author Manoj Khanna
 */

public class Test4 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MaterialFrame frame = new MaterialFrame();
//            frame.setMaterialWidth(400);
//            frame.setMaterialHeight(400);
//            frame.setMaterialScaleX(2.0f);
//            frame.setMaterialScaleY(2.0f);
//            frame.setMaterialRotation(0.125f);
//            frame.setMaterialColor(MaterialColor.BLUE_500);
//            frame.setMaterialColor(MaterialColor.TRANSPARENT);
//            frame.setMaterialAlpha(0.5f);
//            frame.setMaterialShape(new Ellipse2D.Float());

//            frame.addMouseListener(new MouseAdapter() {
//
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    new Animator.Builder(TimingSource.getInstance())
//                            .addTarget(MaterialTarget.getToTarget(MaterialProperty.WIDTH, frame, 100))
//                            .addTarget(MaterialTarget.getToTarget(MaterialProperty.HEIGHT, frame, 100))
////                            .addTarget(MaterialTarget.getOfTarget(MaterialProperty.ROTATION, frame, 0.0f, 1.0f))
//                            .setDuration(400, TimeUnit.MILLISECONDS)
//                            .setInterpolator(DecelerationInterpolator.getInstance())
//                            .build()
//                            .start();
//                }
//
//            });

            MaterialPanel panel = new MaterialPanel();
//            panel.setMaterialColor(MaterialColor.INDIGO_500);
//            panel.setMaterialShape(new Ellipse2D.Float());
            frame.setContentPane(panel);
//
            MaterialButton button = new MaterialButton();
//            button.setMaterialX(100);
//            button.setMaterialY(100);
            panel.add(button);

            MaterialLabel label = new MaterialLabel();
            label.setMaterialX(100);
            label.setMaterialY(100);
//            label.setMaterialText("Material Swing");
//            label.setMaterialTextColor(MaterialColor.PINK_500);

//            label.setContent(new TextContent("Material Swing")
//                    .withColor(MaterialColor.PINK_500.withAlpha(0.8f))
//                    .withFont(MaterialFont.ROBOTO_BOLD_ITALIC.withSize(14.0f)));

//            actionButton.setPrimaryImage();

            panel.add(label);

            frame.pack();
            frame.setVisible(true);
        });
    }

}
