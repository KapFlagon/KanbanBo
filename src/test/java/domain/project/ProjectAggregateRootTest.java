package domain.project;

import exceptions.domain.InvalidTimestampException;
import exceptions.domain.InvalidTitleException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectAggregateRootTest {


    @Test
    public void projectTitleMustBeValid() {
        ProjectAggregateRoot project = new ProjectAggregateRoot();
        String projectTitle = "My new Project title";
        assertAll(
                () -> assertThrows(InvalidTitleException.class, () -> project.setTitle("")),
                () -> assertThrows(InvalidTitleException.class, () -> project.setTitle(" ")),
                () -> assertDoesNotThrow( () -> project.setTitle(projectTitle)),
                () -> assertEquals(project.titleProperty().get(), projectTitle)
        );
    }

    @Test
    public void projectDueDateMustBeAfterCreationDate() {
        ProjectAggregateRoot project = new ProjectAggregateRoot();
        LocalDateTime creationTime = LocalDateTime.of(2022,5,12,10,0);
        project.setCreatedOnTimestamp(creationTime);
        LocalDateTime pastTime = creationTime.minusDays(1);
        LocalDateTime futureTime = creationTime.plusDays(1);

        assertAll(
                () -> assertThrows(InvalidTimestampException.class, () -> project.setDueDateTimestamp(pastTime)),
                () -> assertDoesNotThrow(() -> project.setDueDateTimestamp(futureTime)),
                () -> assertEquals(project.dueDateTimestampProperty().get().getValue(), futureTime)
        );
    }

    @Test
    public void descriptionIsOptional() {
        ProjectAggregateRoot projectAggregateRoot = new ProjectAggregateRoot();
        String projectDescription = "My project description";
        assertAll(
                () -> assertFalse(projectAggregateRoot.descriptionProperty().isPresent()),
                () -> assertThrows(NoSuchElementException.class, () -> projectAggregateRoot.descriptionProperty().get()),
                () -> assertDoesNotThrow(() -> projectAggregateRoot.setDescription(projectDescription)),
                () -> assertTrue(projectAggregateRoot.descriptionProperty().isPresent()),
                () -> assertEquals(projectAggregateRoot.descriptionProperty().get().getValue(), projectDescription)
        );
    }

    @Test
    public void lastChangedTimestampIsCorrect() {
        ProjectAggregateRoot project = new ProjectAggregateRoot();
        LocalDateTime creationTime = LocalDateTime.of(2022,5,12,10,0);
        project.setCreatedOnTimestamp(creationTime);
        LocalDateTime pastTime = creationTime.minusDays(1);
        LocalDateTime futureTime = creationTime.plusDays(1);

        assertAll(
                () -> assertThrows(InvalidTimestampException.class, () -> project.setLastChangedTimestamp(pastTime)),
                () -> assertDoesNotThrow(project::updateLastChangedTimestamp),
                () -> assertDoesNotThrow(() -> project.setLastChangedTimestamp(futureTime)),
                () -> assertEquals(project.lastChangedTimestampProperty().get(), futureTime)
        );
    }



}