import java.util.List;

public class ContentListModel <E extends Content> extends javax.swing.AbstractListModel<E> {
    private List<Content> data;
    private ContentManager cman;
    private boolean lowestFirst;

    public ContentListModel() {
        cman = new ContentManager();
        data = cman.getContent();
    }

    public void loadData() {
        cman.loadData();
    }

    private void updateData() {
        cman.sortRating(lowestFirst);
        //data = cman.getContent();
        fireContentsChanged(this, 0, getSize());
    }

    public void setVisible(int visible) {
        cman.setVisible(visible);
        updateData();
    }

    public void filterTitle(String title) {
        cman.search(title);
        updateData();
    }

    public void filterTitleGenre(String title, String genres) {
        cman.searchGenre(title, genres);
        updateData();
    }

    public void sortRating(boolean lowestFirst) {
        this.lowestFirst = lowestFirst;
        updateData();
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
