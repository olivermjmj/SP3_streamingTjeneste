import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ContentRenderer extends JLabel implements ListCellRenderer<Content> {

    public ContentRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Content> list, Content content, int index, boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/"+content.title+".jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));;

        setIcon(imageIcon);
        setText("<html><h1>"+content.title+"</h1><br>"+content.yearOfRelease+"<br>"+content.rating+"</html>");
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
