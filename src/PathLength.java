import java.util.*;

import static java.util.stream.Collectors.toMap;

public class PathLength {
    int subSampSize,treeNum;
    Set<itree> forest;
    List<List<Double>> dataPoints;
    double numAnam;

    PathLength(List<List<Double>> dataPoints,int subSampSize,int treeNum,double numAnam){
        iforest myforest = new iforest(treeNum,subSampSize);
        this.forest = myforest.createforest(dataPoints);
        this.dataPoints = dataPoints;
        this.subSampSize = subSampSize;
        this.treeNum = treeNum;
        this.numAnam = numAnam;
    }

    public List<Integer> calculatePathLength(List<Double> x){
        List<Integer> pathlens = new ArrayList<>();
        for(itree t: forest){
            //System.out.println("a new tree is being traversed");
            int count = 1;
            Node traverser = t.root;
            while(traverser!=null){
                if (x.get(traverser.attribute) <= traverser.split){
                    count = count+1;
                    traverser = traverser.left;
                }
                else{
                    count = count+1;
                    traverser = traverser.right;
                }
            }
            pathlens.add(count);
        }
        return pathlens;
    }

    public List<List<Double>> topAnamoly(){
        HashMap<List<Double>, Double> pointScores = new HashMap<>();

        for (List<Double> dps : dataPoints){
            List<Integer> intList = calculatePathLength(dps);
            Double ehx = intList.stream().mapToInt(val -> val).average().orElse(0.0);
            double n = dataPoints.size();
            double cn = (2 * (Math.log(n-1)+0.577215)) - (2 * (n-1)/n);
            double s = Math.pow(2,(-1)*(ehx)/cn);

            pointScores.put(dps, s);
        }

        List<List<Double>> topKAnomalies = getTopPoints(pointScores, (int)numAnam);
        return topKAnomalies;

    }

    public List<List<Double>> getTopPoints(HashMap<List<Double>, Double> pointScoreaMap, int numOfAnomaly){
        List<List<Double>> topAnomalies = new ArrayList<>();

        Map<List<Double>, Double> sorted;
        sorted = pointScoreaMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap :: new)
                );

        for(Map.Entry<List<Double>, Double> e : sorted.entrySet()) {
            List<Double> point = e.getKey();
            topAnomalies.add(point);
        }

        List<List<Double>> tempAnomalies = new ArrayList<>();
        for(int i = 0; i < numOfAnomaly; i++){
            tempAnomalies.add(topAnomalies.get(i));
        }

        return tempAnomalies;
    }

}
