package com.coinfi.beam.ethereum;

import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;


@RunWith(JUnit4.class)
public class MyPipelineTest {

    @Rule
    public TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testTokenTransfersBasic() throws Exception {
        PCollection<String> output = MyPipeline.buildPipeline(p);

        PAssert.that(output).containsInAnyOrder(Arrays.asList("test1"));
        
        p.run().waitUntilFinish();
    }
}
