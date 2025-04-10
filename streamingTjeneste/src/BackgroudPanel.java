import javax.swing.*;
import java.awt.*;

public class BackgroudPanel extends JPanel {
    private Image backgroundImage;

    public BackgroudPanel(String imagePath){
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = backgroundImage.getWidth(null);
            int imageHeight = backgroundImage.getHeight(null);

            double scale = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);

            int scaleWidth = (int) (imageWidth * scale);
            int scaleHeight = (int) (imageHeight * scale);

            int x = (panelWidth - scaleWidth) /2;
            int y = (panelHeight - scaleHeight) / 2;

            g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
        }

    }
}
