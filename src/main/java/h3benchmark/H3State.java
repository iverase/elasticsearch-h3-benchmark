package h3benchmark;

import com.uber.h3core.H3Core;
import org.elasticsearch.h3.H3;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
public class H3State {

    double[][] points  = new double[1000][2];
    long[] h3  = new long[1000];
    H3Core h3Core;

    @Setup(Level.Trial)
    public void setupTrial() throws IOException {
        Random random = new Random(1234);
        for (int i = 0; i < points.length; i++) {
            points[i][0] = random.nextDouble() * 180 - 90;   // lat
            points[i][1] = random.nextDouble() * 360 - 180;  // lon
            int res = random.nextInt(16);            // resolution
            h3[i] = H3.geoToH3(points[i][0], points[i][1], res);
        }
        this.h3Core = H3Core.newInstance();
    }
}
