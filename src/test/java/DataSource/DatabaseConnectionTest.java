package DataSource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    private void setPrivateFieldToNull(Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, null);
    }


    @Test
    void testGetConnection() {
        EntityManager em1 = DatabaseConnection.getConnection();
        assertNotNull(em1, "EntityManager should not be null");

        EntityManager em2 = DatabaseConnection.getConnection();
        assertSame(em1, em2, "EntityManager instances should be the same");
    }

    @Test
    void testGetConnectionWhenEmfIsNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory("DBunit")).thenReturn(mockEmf);

            // Set emf to null to simulate the condition
            setPrivateFieldToNull(DatabaseConnection.class, "emf");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, "EntityManager should not be null");
        }
    }



    @Test
    void testGetConnectionWhenEmIsNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory("DBunit")).thenReturn(mockEmf);

            // Set em to null to simulate the condition
            setPrivateFieldToNull(DatabaseConnection.class, "em");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, "EntityManager should not be null");
        }
    }

    @Test
    void testGetConnectionWhenEmAndEmfAreNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory("DBunit")).thenReturn(mockEmf);

            // Set em and emf to null to simulate the condition
            setPrivateFieldToNull(DatabaseConnection.class, "em");
            setPrivateFieldToNull(DatabaseConnection.class, "emf");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, "EntityManager should not be null");
        }
    }

}