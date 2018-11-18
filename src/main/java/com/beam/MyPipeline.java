package com.coinfi.beam.ethereum;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.GenerateSequence;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.GlobalWindows;
import org.apache.beam.sdk.transforms.windowing.Repeatedly;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MyPipeline {

    private static final Logger LOG = LoggerFactory.getLogger(MyPipeline.class);

    public static PCollection<String> buildPipeline(Pipeline p) {
        PCollection<String> mockInput = p.apply("Input", Create.of(Arrays.asList("test1")));

        PCollectionView<Map<String, String>> sideInput = getSideInput(p);

        PCollection<String> output = mockInput
            .apply("UseSideInput", ParDo.of(new UseSideInput(sideInput)).withSideInputs(sideInput));
        
        return output;
    }

    private static PCollectionView<Map<String, String>> getSideInput(
        Pipeline p
    ) {
        GenerateSequence sequence = GenerateSequence.from(0).to(1).withRate(1, Duration.standardHours(1));

        return p
            .apply("GenerateSequence", sequence)
            .apply("GenerateSequenceWindow",
                Window.<Long>into(new GlobalWindows()).triggering(
                    Repeatedly.forever(AfterProcessingTime.pastFirstElementInPane()))
                    .discardingFiredPanes())
            .apply("TransformMap",
                ParDo.of(new TransformToMap()))
            .apply("TransformToView", View.asSingleton());
    }

    interface MyPipelineOptions extends PipelineOptions, StreamingOptions {
        
    }

    public static class TransformToMap extends DoFn<Long, Map<String, String>> {

        @ProcessElement
        public void processElement(ProcessContext c) throws Exception {
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            c.output(map);
        }
    }

    public static class UseSideInput extends DoFn<String, String> {

        private final PCollectionView<Map<String, String>> sideInput;

        public UseSideInput(PCollectionView<Map<String, String>> sideInput) {
            this.sideInput = sideInput;
        }

        @ProcessElement
        public void processElement(ProcessContext c) throws Exception {
            String elem = c.element();

            Map<String, String> map = c.sideInput(this.sideInput);

           LOG.info(map.toString());

            c.output(elem);
        }
    }
}
