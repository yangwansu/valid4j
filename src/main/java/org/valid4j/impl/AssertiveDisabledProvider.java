package org.valid4j.impl;

import org.valid4j.AssertiveProvider;
import org.valid4j.CheckPolicy;
import org.valid4j.UnreachablePolicy;

/**
 * An assertive policy provider that disables all checks and assertive handling
 */
@SuppressWarnings("unused")
public class AssertiveDisabledProvider implements AssertiveProvider {

  @Override
  public CheckPolicy requirePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public CheckPolicy ensurePolicy() {
    return new NonCheckingPolicy();
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return new DoNothingPolicy();
  }

  private static class DoNothingPolicy implements UnreachablePolicy {
    @Override
    public void neverGetHere(Throwable t, String msg, Object... values) {
      // Do nothing
    }
  }
}
