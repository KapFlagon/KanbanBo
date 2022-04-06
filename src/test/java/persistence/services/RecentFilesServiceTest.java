package persistence.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import persistence.repositories.RecentFilesRepository;

import java.nio.file.Path;
import java.util.prefs.BackingStoreException;

import static org.junit.jupiter.api.Assertions.*;

class RecentFilesServiceTest {


    @Mock
    private RecentFilesRepository recentFilesRepository;

    @Captor
    private ArgumentCaptor<ObservableList<Path>> pathListArgumentCaptor;

    @InjectMocks
    private RecentFilesService recentFilesService = new RecentFilesService();

    private Path dummyPath01 = Path.of("d://MyFile_01.txt");
    private Path dummyPath02 = Path.of("d://MyFile_02.txt");
    private Path dummyPath03 = Path.of("d://MyFile_03.txt");
    private Path dummyPath04 = Path.of("d://MyFile_04.txt");
    private Path dummyPath05 = Path.of("d://MyFile_05.txt");
    private Path dummyPath06 = Path.of("d://MyFile_06.txt");

    @BeforeEach
    void before() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }


    private ObservableList<Path> verifyPathListSave() throws BackingStoreException {
        Mockito.verify(recentFilesRepository).saveRecentFilePaths(pathListArgumentCaptor.capture());
        return pathListArgumentCaptor.getValue();
    }


    private ObservableList<Path> verifyPathListSave(int timesExecuted) throws BackingStoreException {
        Mockito.verify(recentFilesRepository, Mockito.times(timesExecuted)).saveRecentFilePaths(pathListArgumentCaptor.capture());
        return pathListArgumentCaptor.getValue();
    }


    @Test
    void getRecentFilePaths() {
        ObservableList<Path> recentFilesListCopy = FXCollections.observableArrayList(recentFilesService.getRecentFilePaths());

        assertAll(
                () -> assertNotNull(recentFilesService),
                () -> assertEquals(recentFilesListCopy, recentFilesService.getRecentFilePaths())
        );
    }


    @Test
    void addOneNewRecentFilePath() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);

        ObservableList<Path> capturedPathList = verifyPathListSave();

        assertAll(
                () -> assertNotEquals(0, capturedPathList.size()),
                () -> assertEquals(1, capturedPathList.size()),
                () -> assertEquals(dummyPath01, capturedPathList.get(0))
        );
    }


    @Test
    void addTwoNewRecentFilePath() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);
        recentFilesService.addRecentFilePath(dummyPath02);

        ObservableList<Path> capturedPathList = verifyPathListSave(2);

        assertAll(
                () -> assertNotEquals(0, capturedPathList.size()),
                () -> assertEquals(2, capturedPathList.size()),
                () -> assertEquals(dummyPath02, capturedPathList.get(0)),
                () -> assertEquals(dummyPath01, capturedPathList.get(1))
        );
    }

    @Test
    void moveExistingRecentFilePath() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);
        recentFilesService.addRecentFilePath(dummyPath02);
        recentFilesService.addRecentFilePath(dummyPath03);
        recentFilesService.addRecentFilePath(dummyPath01);

        ObservableList<Path> capturedPathList = verifyPathListSave(4);

        assertAll(
                () -> assertEquals(3, capturedPathList.size()),
                () -> assertEquals(dummyPath01, capturedPathList.get(0)),
                () -> assertEquals(dummyPath03, capturedPathList.get(1)),
                () -> assertEquals(dummyPath02, capturedPathList.get(2))
        );
    }

    @Test
    void removeRecentFilePath() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);
        recentFilesService.removeRecentFilePath(dummyPath01);

        ObservableList<Path> capturedPathList = verifyPathListSave(2);

        assertAll(
                () -> assertNotEquals(1, capturedPathList.size()),
                () -> assertEquals(0, capturedPathList.size())
        );
    }

    @Test
    void clearRecentFilePaths() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);
        recentFilesService.addRecentFilePath(dummyPath02);
        recentFilesService.addRecentFilePath(dummyPath03);

        recentFilesService.clearRecentFilePaths();

        ObservableList<Path> capturedPathList = verifyPathListSave(4);

        assertAll(
                () -> assertNotEquals(3, capturedPathList.size()),
                () -> assertEquals(0, capturedPathList.size())
        );
    }

    @Test
    void onlyFiveRecentFiles() throws BackingStoreException {
        recentFilesService.addRecentFilePath(dummyPath01);
        recentFilesService.addRecentFilePath(dummyPath02);
        recentFilesService.addRecentFilePath(dummyPath03);
        recentFilesService.addRecentFilePath(dummyPath04);
        recentFilesService.addRecentFilePath(dummyPath05);
        recentFilesService.addRecentFilePath(dummyPath06);

        ObservableList<Path> capturedPathList = verifyPathListSave(6);

        assertAll(
                () -> assertNotEquals(6, capturedPathList.size()),
                () -> assertEquals(5, capturedPathList.size())
        );
    }
}