package org.itsimulator.germes.app.service.transform.impl;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.itsimulator.germes.app.infra.util.ReflectionUtil;
import org.itsimulator.germes.app.infra.util.annotation.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.security.krb5.internal.crypto.Des;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

/**
 * Verifies functionality of the {@link SimpleDTOTransformer}
 * unit
 * @author Morenets
 *
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ReflectionUtil.class)
@RunWith(JMockit.class)
public class CachedFieldProviderTest {
	private FieldProvider provider;

	//@Mocked
	//private final ReflectionUtil reflectionUtil = null;
	
	@Before
	public void setup() {
		provider = new CachedFieldProvider();
	}
	
	@Test
	public void testGetFieldNamesSuccess() {
		List<String> fields = provider.getFieldNames(Source.class, Destination.class);
		assertFalse(fields.isEmpty());
		assertTrue(fields.contains("value"));		
	}

	@Test
	public void testGetFieldNamesCachedSuccess() {
		List<String> fields = provider.getFieldNames(Source.class, Destination.class);
		List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);
		assertFalse(fields.isEmpty());
		assertEquals(fields, fields2);		
	}

    @Test
    public void testGetFieldNamesAreCached(){
	    List<String> fields = provider.getFieldNames(Source.class, Destination.class);
        PowerMockito.mockStatic(ReflectionUtil.class);
        when(ReflectionUtil.findSimilarFields(Source.class, Destination.class)).thenReturn(Collections.emptyList());

        assertTrue(ReflectionUtil.findSimilarFields(Source.class, Destination.class).isEmpty());
        List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);
        assertFalse(fields.isEmpty());
        assertEquals(fields, fields2);
    }

    @Test

	public void testGetFieldNamesAreCachedUsingJMockit(){
    	new Expectations(){
			{
				ReflectionUtil.findSimilarFields(Source.class, Destination.class);
				result = Collections.singletonList("name");
			}
		};

    	List<String> fields = provider.getFieldNames(Source.class, Destination.class);
    	new Expectations(){
			{
				ReflectionUtil.findSimilarFields(Source.class, Destination.class);
				result = Collections.emptyList();
			}
		};

		assertTrue(ReflectionUtil.findSimilarFields(Source.class, Destination.class).isEmpty());
		List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);
		assertFalse(fields.isEmpty());
		assertEquals(fields, fields2);
	}

	public static final class MockReflectionUtil extends MockUp<ReflectionUtil>{
		protected static List<String> fields;

		@Mock
		public static List<String> findSimilarFields(Class<?> clz1, Class<?> clz2){
			return fields;
		}
	}

	@Test
	public void testGetFieldNamesAreCachedUsingMockupsAPI(){
		new MockReflectionUtil();
		MockReflectionUtil.fields = Collections.singletonList("name");
		List<String> fields = provider.getFieldNames(Source.class, Destination.class);

		MockReflectionUtil.fields = Collections.emptyList();

		assertTrue(ReflectionUtil.findSimilarFields(Source.class, Destination.class).isEmpty());
		List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);
		assertFalse(fields.isEmpty());
		assertEquals(fields, fields2);
	}
	
}

class Source {
	int value;
}

class Destination {
	int value;
}
