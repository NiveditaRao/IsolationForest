import java.util.*;

public class itree {

    // int htLimit = (int)Math.log(subsample.size());
    int htLimit;
    Node root;
    int attribute;

    itree (List<List<Double>> subsample,int limit){
        htLimit = limit;
        //System.out.println("You just created a tree.");
        root = new Node();
        createTree(subsample,root,0);
    }

    public Node createTree(List<List<Double>> subsample,Node current,int curHeight){

        int max = subsample.get(0).size(); // size of attributes to get random number
        Random rand = new Random();
        int attribute = rand.nextInt(max);
        this.attribute = attribute;
        List<Double> attrsplit = new ArrayList<>();

        for(int i=0;i<subsample.size();i++) {
            attrsplit.add(subsample.get(i).get(attribute));
        }

        double maxSubsample = Collections.max(attrsplit);
        double minSubsample = Collections.min(attrsplit);
        double split = minSubsample+(maxSubsample-minSubsample)*rand.nextDouble();

        if (curHeight>=htLimit || subsample.size()<=1){
            //System.out.println("reached end");
            return current;
        }
        if (current==null){
            current = new Node(curHeight,split,attribute);
        }

        current.split = split;
        current.attribute = attribute;
        current.curHt = curHeight;
        List<List<Double>> leftSample = new ArrayList<>();
        List<List<Double>> rightSample = new ArrayList<>();

        for(int i=0;i<subsample.size();i++){
            if(subsample.get(i).get(attribute) <= split){
                leftSample.add(subsample.get(i));
            }
            else{
                rightSample.add(subsample.get(i));
            }
        }

        if (rightSample.size()==0){
            rightSample = leftSample;
        }

        if (leftSample.size()==0){
            leftSample = rightSample;
        }

        current.left = createTree(leftSample,current.left,curHeight+1);
        current.right = createTree(rightSample,current.right,curHeight+1);

        return current;
    }


}
