import java.io.*;
import java.util.*;

public class Main {
    private static final String COMMA_DELIMITER = ",";
    public static void main(String[] args) throws IOException {
        List<List<String>> records = new ArrayList<>();
        List<List<Double>> datapts = new ArrayList<>();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in)
        );
        String s;
        while ( (s = in.readLine( )) != null ) {
            //System.out.println(s);
            String[] values = s.split(COMMA_DELIMITER);
            //System.out.println(values.length);
            List<Double> datapoint = new ArrayList<>();
            for (String str: values){
                try {
                    datapoint.add(Double.parseDouble(str));
                }
                catch (Exception ne){
                    System.out.println("Input format incorrect!");
                    System.exit(1);
                }
            }
            records.add(Arrays.asList(values));
            datapts.add(datapoint);
        }

        try {
            System.out.println(datapts.get(1));
            int firstDim = datapts.get(1).size();
            for(int i = 2; i < datapts.size(); i++){
                if(datapts.get(i).size() != firstDim){
                    System.out.println("Error in input format! Dimensions of data points do not match");
                    System.exit(1);
                }
            }

            double topanamoly = datapts.get(0).get(0);
            if (datapts.get(0).size()!=1){
                System.out.println("Error in input format");
                System.exit(1);
            }
            datapts.remove(0);

            int subsamp = (int) Math.sqrt(datapts.size());
            if (datapts.size() > subsamp) {
                subsamp = subsamp * 2;
            }

            int treenum = (int) Math.sqrt(datapts.size());

            PathLength path = new PathLength(datapts, subsamp, treenum, topanamoly);
            path.calculatePathLength(datapts.get(0));
            List<List<Double>> answer;
            answer = path.topAnamoly();
            //System.out.println("answer "+Arrays.toString(answer.toArray()));
            System.out.println("Top " + (int) topanamoly + " number of anomalies");

            for (List<Double> row : answer) {
                System.out.println(row);
            }
        }
        catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }
}
