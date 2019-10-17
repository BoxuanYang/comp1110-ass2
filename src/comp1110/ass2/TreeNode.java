package comp1110.ass2;

import java.util.ArrayList;

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


    public String getPlacement(){
        return placement;
    }
}
