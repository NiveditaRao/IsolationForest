
public class Node {
    int curHt;
    Node left;
    Node right;
    double split;
    int attribute;

    Node(){
        right = null;
        left = null;
    }

    Node (int curHt,double split,int attribute){
        this.attribute = attribute;
        this.split = split;
        this.curHt = curHt;
        right = null;
        left = null;
    }
}
