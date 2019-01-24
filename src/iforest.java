import java.util.*;
import java.lang.Math;
import java.util.Random;

public class iforest {

    int numOfTrees;
    int subSamplingSize;
    int limitHeight;

    iforest(int treeNum,int subSize){
        numOfTrees = treeNum;
        subSamplingSize = subSize;
        ////System.out.println("you just created a forest");
    }

    public Set<itree> createforest(List<List<Double>> dataSet){
        limitHeight = (int)Math.ceil(Math.log(dataSet.size()));
        List<List<Double>> subsam = new ArrayList<>();
        Set<itree> trees = new HashSet<>();
        for (int i=0;i<numOfTrees;i++){
            subsam = subsamp(dataSet);
            trees.add(new itree(subsam,limitHeight));
        }
        return trees;
    }

    public List<List<Double>> subsamp(List<List<Double>> assets){

        Random rand = new Random();
        List<List<Double>> sets = new ArrayList<>(assets);
        List<List<Double>> sampx = new ArrayList<>();
        for(int j=0;j<subSamplingSize;j++){
            int randIndex = rand.nextInt(sets.size()); // size of attributes to get random number
            sampx.add(sets.get(randIndex));
            sets.remove(randIndex);
        }

        return sampx;
    }
}
