# clj-1472-repro

## Repro

- Create an uberjar with `lein uberjar`
- Test the uberjar:

```
$ java -jar target/clj-1472-repro-0.1.0-SNAPSHOT-standalone.jar
Is 1 a valid int?: true
```

- Compile the uberjar with GraalVM with GraalVM 19.3.1 java8:

```
$ /Users/borkdude/Downloads/graalvm-ce-java8-19.3.1/Contents/Home/bin/native-image \
  --initialize-at-build-time --no-server --no-fallback \
  -jar target/clj-1472-repro-0.1.0-SNAPSHOT-standalone.jar -H:Name=clj-1472-repro
[clj-1472-repro:42960]    classlist:   7,079.42 ms
[clj-1472-repro:42960]        (cap):   2,297.70 ms
[clj-1472-repro:42960]        setup:   5,290.23 ms
[clj-1472-repro:42960]   (typeflow):  48,311.32 ms
[clj-1472-repro:42960]    (objects):  14,797.53 ms
[clj-1472-repro:42960]   (features):     855.37 ms
[clj-1472-repro:42960]     analysis:  65,064.90 ms
Error: Unsupported features in 2 methods
Detailed message:
Error: com.oracle.svm.hosted.substitute.DeletedElementException: Unsupported method java.lang.ClassLoader.defineClass(String, byte[], int, int) is reachable: The declaring class of this element has been substituted, but this element is not present in the substitution class
To diagnose the issue, you can add the option --report-unsupported-elements-at-runtime. The unsupported element is then reported at run time when it is accessed the first time.
Trace:
	at parsing clojure.lang.DynamicClassLoader.defineClass(DynamicClassLoader.java:46)
Call path from entry point to clojure.lang.DynamicClassLoader.defineClass(String, byte[], Object):
	at clojure.lang.DynamicClassLoader.defineClass(DynamicClassLoader.java:45)
	at clojure.core$get_proxy_class.invokeStatic(core_proxy.clj:288)
	at clojure.core$get_proxy_class.doInvoke(core_proxy.clj:276)
	at clojure.lang.RestFn.applyTo(RestFn.java:137)
	at clj_1472_repro.core.main(Unknown Source)
	at com.oracle.svm.core.JavaMainWrapper.runCore(JavaMainWrapper.java:151)
	at com.oracle.svm.core.JavaMainWrapper.run(JavaMainWrapper.java:186)
	at com.oracle.svm.core.code.IsolateEnterStub.JavaMainWrapper_run_5087f5482cc9a6abc971913ece43acb471d2631b(generated:0)
Error: unbalanced monitors: mismatch at monitorexit, 3|LoadField#lockee__5436__auto__ != 96|LoadField#lockee__5436__auto__
Call path from entry point to clojure.spec.gen.alpha$dynaload$fn__2628.invoke():
	at clojure.spec.gen.alpha$dynaload$fn__2628.invoke(alpha.clj:21)
	at clojure.lang.AFn.run(AFn.java:22)
	at java.lang.Thread.run(Thread.java:748)
	at com.oracle.svm.core.thread.JavaThreads.threadStartRoutine(JavaThreads.java:497)
	at com.oracle.svm.core.posix.thread.PosixJavaThreads.pthreadStartRoutine(PosixJavaThreads.java:193)
	at com.oracle.svm.core.code.IsolateEnterStub.PosixJavaThreads_pthreadStartRoutine_e1f4a8c0039f8337338252cd8734f63a79b5e3df(generated:0)
Original exception that caused the problem: org.graalvm.compiler.code.SourceStackTraceBailoutException$1: unbalanced monitors: mismatch at monitorexit, 3|LoadField#lockee__5436__auto__ != 96|LoadField#lockee__5436__auto__
	at clojure.spec.gen.alpha$dynaload$fn__2628.invoke(alpha.clj:22)
Caused by: org.graalvm.compiler.core.common.PermanentBailoutException: unbalanced monitors: mismatch at monitorexit, 3|LoadField#lockee__5436__auto__ != 96|LoadField#lockee__5436__auto__
	at org.graalvm.compiler.java.BytecodeParser.bailout(BytecodeParser.java:3800)
	at org.graalvm.compiler.java.BytecodeParser.genMonitorExit(BytecodeParser.java:2711)
	at org.graalvm.compiler.java.BytecodeParser.processBytecode(BytecodeParser.java:5181)
	at org.graalvm.compiler.java.BytecodeParser.iterateBytecodesForBlock(BytecodeParser.java:3286)
	at org.graalvm.compiler.java.BytecodeParser.processBlock(BytecodeParser.java:3093)
	at org.graalvm.compiler.java.BytecodeParser.build(BytecodeParser.java:977)
	at org.graalvm.compiler.java.BytecodeParser.buildRootMethod(BytecodeParser.java:871)
	at org.graalvm.compiler.java.GraphBuilderPhase$Instance.run(GraphBuilderPhase.java:84)
	at org.graalvm.compiler.phases.Phase.run(Phase.java:49)
	at org.graalvm.compiler.phases.BasePhase.apply(BasePhase.java:197)
	at org.graalvm.compiler.phases.Phase.apply(Phase.java:42)
	at org.graalvm.compiler.phases.Phase.apply(Phase.java:38)
	at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:221)
	at com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:340)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.doParse(MethodTypeFlow.java:310)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureParsed(MethodTypeFlow.java:300)
	at com.oracle.graal.pointsto.flow.MethodTypeFlow.addContext(MethodTypeFlow.java:107)
	at com.oracle.graal.pointsto.flow.SpecialInvokeTypeFlow.onObservedUpdate(InvokeTypeFlow.java:421)
	at com.oracle.graal.pointsto.flow.TypeFlow.notifyObservers(TypeFlow.java:343)
	at com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:385)
	at com.oracle.graal.pointsto.flow.SourceTypeFlowBase.update(SourceTypeFlowBase.java:121)
	at com.oracle.graal.pointsto.BigBang$2.run(BigBang.java:511)
	at com.oracle.graal.pointsto.util.CompletionExecutor.lambda$execute$0(CompletionExecutor.java:171)
	at java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1402)
	at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
	at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
	at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
	at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)

Error: Use -H:+ReportExceptionStackTraces to print stacktrace of underlying exception
Error: Image build request failed with exit status 1
```
