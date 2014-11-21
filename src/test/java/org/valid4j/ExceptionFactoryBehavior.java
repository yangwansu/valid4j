package org.valid4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.ExceptionFactories.builder;

public class ExceptionFactoryBehavior {

	@Rule
	public final ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

	@SuppressWarnings("serial")
	public static class OneStringArgumentException extends RuntimeException {
		@SuppressWarnings("unused")
		public OneStringArgumentException(String msg) {
			super(msg);
		}
		@SuppressWarnings("unused")
		public OneStringArgumentException() {
		}
		@SuppressWarnings("unused")
		public OneStringArgumentException(String msg, Throwable t) {
			super(msg, t);
		}
	}

	@SuppressWarnings("serial")
	public static class NoArgumentException extends RuntimeException {
		public NoArgumentException() {
			super();
		}
	}
	
	@SuppressWarnings("serial")
	public static class NoStringArgumentException extends RuntimeException {
		public NoStringArgumentException(Throwable t) {
			super(t);
		}
	}

	@SuppressWarnings("serial")
	public class InnerClassException extends RuntimeException {
		public InnerClassException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static abstract class AbstractException extends RuntimeException {
		public AbstractException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static class NoPublicConstructorException extends RuntimeException {
		@SuppressWarnings("unused")
		private NoPublicConstructorException(String msg) {
			super(msg);
		}

		@SuppressWarnings("unused")
		private NoPublicConstructorException() {
		}
	}

	@SuppressWarnings("serial")
	public static class SelfThrowingException extends RuntimeException {
		public SelfThrowingException(String msg) {
			super(msg);
			throw null;
		}
	}

	@SuppressWarnings("serial")
	public static class SelfThrowingNoArgumentException extends RuntimeException {
		public SelfThrowingNoArgumentException() {
			throw null;
		}
	}

	@Test
	public void shouldBuildExceptionWithPublicOneStringArgumentConstructor() {
		ExceptionFactory<OneStringArgumentException> builder = builder(OneStringArgumentException.class);
		RuntimeException e = builder.newInstance("exception message");
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), equalTo("exception message"));
	}
	
	@Test
	public void shouldBuildExceptionWithNullMessage() {
		ExceptionFactory<OneStringArgumentException> builder = builder(OneStringArgumentException.class);
		RuntimeException e = builder.newInstance(null);
		assertThat(e, instanceOf(OneStringArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}
	
	@Test
	public void shouldBuildExceptionWithNoArgumentConstructor() {
		ExceptionFactory<NoArgumentException> builder = builder(NoArgumentException.class);
		RuntimeException e = builder.newInstance("exception message");
		assertThat(e, instanceOf(NoArgumentException.class));
		assertThat(e.getMessage(), nullValue());
	}

	@Test
	public void shouldRejectExceptionWithNoPublicConstructor() {
		thrown.expect(instanceOf(AssertionError.class));
		thrown.expectMessage("must have a public constructor");
		builder(NoPublicConstructorException.class);
	}

	@Test
	public void shouldRejectInnerClassExceptions() {
		thrown.expect(instanceOf(AssertionError.class));
		builder(InnerClassException.class);
	}

	@Test
	public void shouldRejectAbstractExceptions() {
		thrown.expect(instanceOf(AssertionError.class));
		builder(AbstractException.class);
	}
	
	@Test
	public void shouldRejectExceptionWithNoStringArgumentConstructor() {
		thrown.expect(instanceOf(AssertionError.class));
		builder(NoStringArgumentException.class);
	}

	@Test
	public void shouldFailOnOneArgumentExceptionThatThrowByThemselves() {
		ExceptionFactory<SelfThrowingException> builder = builder(SelfThrowingException.class);
		thrown.expect(instanceOf(AssertionError.class));
		builder.newInstance("exception message");
	}

	@Test
	public void shouldFailOnNoArgumentExceptionThatThrowByThemselves() {
		ExceptionFactory<SelfThrowingNoArgumentException> builder = builder(SelfThrowingNoArgumentException.class);
		thrown.expect(instanceOf(AssertionError.class));
		builder.newInstance("exception message");
	}

}
