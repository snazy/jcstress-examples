package com.github.jwoschitz.jcstress.concurreny;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(id = "-1", expect = ACCEPTABLE, desc = "Object has not been seen")
@Outcome(id = "42", expect = ACCEPTABLE, desc = "Object initialized")
public class VolatileMeansEverythingIsFine {
  static class C {
//    volatile int x;
    int x;
    C() { x = 42; }
  }

  C c;

  @Actor
  void thread1() {
    c = new C();
  }

  @Actor
  void thread2(I_Result r) {
    C c = this.c;
    r.r1 = (c == null) ? -1 : c.x;
  }
}
