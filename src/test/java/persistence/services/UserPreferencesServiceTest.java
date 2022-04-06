package persistence.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import persistence.repositories.UserPreferencesRepository;

import java.util.prefs.BackingStoreException;

import static org.junit.jupiter.api.Assertions.*;

class UserPreferencesServiceTest {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @Captor
    private ArgumentCaptor<String> userPreferenceArgumentCaptor;

    @InjectMocks
    private UserPreferencesService userPreferencesService = new UserPreferencesService();


    @BeforeEach
    void before() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void checkDefaultAutoLoadMostRecentFileValue() {
        assertFalse(userPreferencesService.getAutoOpenMostRecentFileValue());
    }


    @Test
    void changeAutoLoadMostRecentFile() throws BackingStoreException {
        userPreferencesService.setAutoOpenMostRecentFileValue(true);
        Mockito.when(userPreferencesRepository.isAutoOpeningMostRecentFile()).thenReturn(true);
        assertTrue(userPreferencesService.getAutoOpenMostRecentFileValue());
    }
}