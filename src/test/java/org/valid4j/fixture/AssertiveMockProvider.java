package org.valid4j.fixture;

import org.valid4j.AssertiveProvider;
import org.valid4j.CheckPolicy;
import org.valid4j.UnreachablePolicy;

/**
 * Customized assertive policy provider in order to test customized behavior.
 */
public class AssertiveMockProvider implements AssertiveProvider {

  public static CheckPolicy requirePolicy = null;
  public static CheckPolicy ensurePolicy = null;
  public static UnreachablePolicy neverGetHerePolicy = null;

  @Override
  public CheckPolicy requirePolicy() {
    return requirePolicy;
  }

  @Override
  public CheckPolicy ensurePolicy() {
    return ensurePolicy;
  }

  @Override
  public UnreachablePolicy neverGetHerePolicy() {
    return neverGetHerePolicy;
  }
}
