import java.util.List;

public class ContentListModel <E extends Content> extends javax.swing.AbstractListModel<E> {
    List<Content> data;
    ContentManager cman;

    public ContentListModel() {
        cman = new ContentManager();
        data = cman.getContent();
    }

    public void setVisible(int visible) {
        cman.setVisible(visible);
        data = cman.getContent();
        fireContentsChanged(this, 0, getSize());
    }

    public void filterTitle(String title) {
        cman.search(title);
        data = cman.getContent();
        fireContentsChanged(this, 0, getSize());
    }

    public int getSize() {
        return data.size();
    }

    public E getElementAt(int i) {
        if (i >= data.size())
            return null;
        return (E) data.get(i);
    }

}
