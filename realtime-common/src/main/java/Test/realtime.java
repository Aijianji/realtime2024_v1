package Test;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 以application模式部署jar包
 * 和此项目无关
 */
public class realtime {
    @SneakyThrows
    public static void main(String[] args) {

        //        StreamExecutionEnvironment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> streamSource = env.socketTextStream(  "hadoop01", 9999);
        streamSource.print();
        env.execute();

    }
}
