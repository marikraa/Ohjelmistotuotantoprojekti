package DataSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnection() {
        EntityManager em1 = DatabaseConnection.getConnection();
        assertNotNull(em1, "EntityManager should not be null");

        EntityManager em2 = DatabaseConnection.getConnection();
        assertSame(em1, em2, "EntityManager instances should be the same");
    }

    @Test
    void testGetConnectionWhenEmfIsNull() {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory("DBunit")).thenReturn(mockEmf);

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, "EntityManager should not be null");
        }
    }

    @Test
    void testGetConnectionWhenExceptionThrown() {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory("DBunit")).thenThrow(new RuntimeException("Test Exception"));

            assertThrows(RuntimeException.class, () -> DatabaseConnection.getConnection(), "Expected RuntimeException to be thrown");
        }
    }
}