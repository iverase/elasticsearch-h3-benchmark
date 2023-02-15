package h3benchmark;

import org.elasticsearch.h3.H3;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class H3Benchmark {

    @Benchmark
    public void pointToH3Uber(H3State state, Blackhole bh) {
        for (int i = 0; i < state.points.length; i++) {
            for (int res = 0; res <= 15; res++) {
                bh.consume(state.h3Core.latLngToCell(state.points[i][0], state.points[i][1], res));
            }
        }
    }

    @Benchmark
    public void pointToH3Elastic(H3State state, Blackhole bh) {
        for (int i = 0; i < state.points.length; i++) {
            for (int res = 0; res <= 15; res++) {
                bh.consume(H3.geoToH3(state.points[i][0], state.points[i][1], res));
            }
        }
    }

    @Benchmark
    public void h3BoundaryUber(H3State state, Blackhole bh) {
        for (int i = 0; i < state.h3.length; i++) {
            bh.consume(state.h3Core.cellToBoundary(state.h3[i]));
        }
    }

    @Benchmark
    public void h3BoundaryElastic(H3State state, Blackhole bh) {
        for (int i = 0; i < state.h3.length; i++) {
            bh.consume(H3.h3ToGeoBoundary(state.h3[i]));
        }
    }

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
