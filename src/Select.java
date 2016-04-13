

import java.util.ArrayList;

public class Select {

    public boolean isSelected2(ArrayList<Node> shapes, int x) {
        boolean flag = false;
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getShape().getShape().intersects(x, 250, 12, 12)) {
                flag = true;
            }
        }
        return flag;
    }

    public int isSelected(ArrayList<Node> shapes, int x) {
        boolean flag = false;
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getShape().getShape().intersects(x, 250, 12, 12)) {
                return i;
            }
        }
        return 0;
    }
}