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
    public Component getListCellRendererComponent(JList<? extends Content> list, Content country, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/Psycho.jpg").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));;

        setIcon(imageIcon);
        setText(country.title);
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
