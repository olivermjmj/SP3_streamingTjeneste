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

        String path = content.title.replace('\'', '_');
        path = path.replace('&', '_');
        ImageIcon imageIcon = null;
        if (content instanceof Movies) {
            imageIcon = new ImageIcon(new ImageIcon("data/img_movies/"+path+".jpg").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        } else if (content instanceof Series) {
            imageIcon = new ImageIcon(new ImageIcon("data/img_series/"+path+".jpg").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        }

        String title = content.title;
        if (title.length() > 20)
            title = title.substring(0,10)+"...";
        setIcon(imageIcon);
        setText("<html><h1>"+title+"</h1><br>"+content.genres+"<br>"+content.releaseYear+"<br>"+content.rating+"</html>");
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
