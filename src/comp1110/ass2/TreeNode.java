package comp1110.ass2;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeNode {
    String placement;
    TreeNode parent;
    ArrayList<TreeNode> children;

    public TreeNode(String placement){
        this.placement = placement;
        this.parent = null;
        this.children = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child){
        this.children.add(child);
        child.parent = this;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(placement);
        buffer.append('\n');
        for (Iterator<TreeNode> it = children.iterator(); it.hasNext();) {
            TreeNode next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }



    public String getPlacement(){
        return placement;
    }
}
