# dataflow-flaky-tests

```bash
> bash run-tests.sh
> grep PCollection result-*.log
```

The issue is reproduced approx. 1 time our of 10.

```
org.apache.beam.sdk.Pipeline$PipelineExecutionException: java.lang.IllegalArgumentException: Empty PCollection accessed as a singleton view. Consider setting withDefault to provide a default value

	at org.apache.beam.runners.direct.DirectRunner$DirectPipelineResult.waitUntilFinish(DirectRunner.java:332)
	at org.apache.beam.runners.direct.DirectRunner$DirectPipelineResult.waitUntilFinish(DirectRunner.java:302)
	at org.apache.beam.runners.direct.DirectRunner.run(DirectRunner.java:197)
	at org.apache.beam.runners.direct.DirectRunner.run(DirectRunner.java:64)
	at org.apache.beam.sdk.Pipeline.run(Pipeline.java:313)
	at org.apache.beam.sdk.testing.TestPipeline.run(TestPipeline.java:350)
	at org.apache.beam.sdk.testing.TestPipeline.run(TestPipeline.java:331)
	at com.beam.MyPipelineTest.testTokenTransfersBasic(MyPipelineTest.java:29)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.apache.beam.sdk.testing.TestPipeline$1.evaluate(TestPipeline.java:319)
	at org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
Caused by: java.lang.IllegalArgumentException: Empty PCollection accessed as a singleton view. Consider setting withDefault to provide a default value
	at org.apache.beam.sdk.transforms.View$SingletonCombineFn.identity(View.java:378)
	at org.apache.beam.sdk.transforms.Combine$BinaryCombineFn.extractOutput(Combine.java:436)
	at org.apache.beam.sdk.transforms.Combine$BinaryCombineFn.extractOutput(Combine.java:384)
	at org.apache.beam.sdk.transforms.Combine$CombineFn.apply(Combine.java:353)
	at org.apache.beam.sdk.transforms.Combine$GroupedValues$1.processElement(Combine.java:2056)
```
