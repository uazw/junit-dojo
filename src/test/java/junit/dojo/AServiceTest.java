package junit.dojo;

import org.junit.Before;
import org.junit.Test;

import static junit.dojo.AService.FAKE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class AServiceTest {

    private BService bService;
    private CService cService;
    private AService aService;

    @Before
    public void setUp() throws Exception {
        bService = mock(BService.class);
        cService = mock(CService.class);
        aService = new AService(bService, cService);
    }

    @Test
    public void shouldGenerateNewNameAndCountItGivenAFakeName() {
        String tofu = "tofu";
        when(bService.generateOne()).thenReturn(tofu);

        String result = aService.run(FAKE);

        assertThat(result, is(tofu));
        verify(cService, times(1)).increaseCounter(tofu);
    }

    @Test
    public void shouldOnlyCountGivenANonFakeName() {
        String tofu = "tofu";

        String result = aService.run(tofu);

        assertThat(result, is(tofu));
        verify(bService, never()).generateOne();
        verify(cService, times(1)).increaseCounter(tofu);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfIsNotSafe() {
        aService.mayThrowException(false, true);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfIsNotIdempotent() {
        aService.mayThrowException(true, false);
    }

    @Test
    public void shouldReturnOkIfSafeAndIdempotent() {
        aService.mayThrowException(true, true);
    }
}