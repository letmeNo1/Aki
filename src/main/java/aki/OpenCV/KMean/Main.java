package aki.OpenCV.KMean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<float[]>();

        dataSet.add(new float[]{51, 43});
        dataSet.add(new float[]{53, 98});
        dataSet.add(new float[]{64, 43});
        dataSet.add(new float[]{65, 93});
        dataSet.add(new float[]{65,93});
        dataSet.add(new float[]{65,93});
        dataSet.add(new float[]{74,51});
        dataSet.add(new float[]{95,101});
        dataSet.add(new float[]{189,192});
        dataSet.add(new float[]{191,270});
        dataSet.add(new float[]{206,189});
        dataSet.add(new float[]{218,199});
        dataSet.add(new float[]{218,199});
        dataSet.add(new float[]{243,273});
        dataSet.add(new float[]{323,215});
        dataSet.add(new float[]{325,270});
        dataSet.add(new float[]{325,270});
        dataSet.add(new float[]{336,215});
        dataSet.add(new float[]{337,265});
        dataSet.add(new float[]{337,265});
        dataSet.add(new float[]{337,265});
        dataSet.add(new float[]{337,265});
        dataSet.add(new float[]{346,223});
        dataSet.add(new float[]{367,273});
        KMeansRun kRun =new KMeansRun(3, dataSet);

        Set<Cluster> clusterSet = kRun.run();
        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
        for (Cluster cluster : clusterSet) {
            System.out.println(cluster);
            System.out.println(Arrays.toString(cluster.getCenter().getlocalArray()));

        }
    }
}