package datasource;

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

    private static final String ENTITY_MANAGER_NOT_NULL = "EntityManager should not be null";
    private static final String DB_UNIT = "DBunit";

    private void setPrivateFieldToNull(Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void testGetConnection() {
        EntityManager em1 = DatabaseConnection.getConnection();
        assertNotNull(em1, ENTITY_MANAGER_NOT_NULL);

        EntityManager em2 = DatabaseConnection.getConnection();
        assertSame(em1, em2, "EntityManager instances should be the same");
    }

    @Test
    void testGetConnectionWhenEmfIsNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory(DB_UNIT)).thenReturn(mockEmf);

            setPrivateFieldToNull(DatabaseConnection.class, "emf");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, ENTITY_MANAGER_NOT_NULL);
        }
    }

    @Test
    void testGetConnectionWhenEmIsNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory(DB_UNIT)).thenReturn(mockEmf);

            setPrivateFieldToNull(DatabaseConnection.class, "em");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, ENTITY_MANAGER_NOT_NULL);
        }
    }

    @Test
    void testGetConnectionWhenEmAndEmfAreNull() throws Exception {
        try (MockedStatic<Persistence> persistenceMockedStatic = Mockito.mockStatic(Persistence.class)) {
            EntityManagerFactory mockEmf = mock(EntityManagerFactory.class);
            EntityManager mockEm = mock(EntityManager.class);
            when(mockEmf.createEntityManager()).thenReturn(mockEm);
            persistenceMockedStatic.when(() -> Persistence.createEntityManagerFactory(DB_UNIT)).thenReturn(mockEmf);

            setPrivateFieldToNull(DatabaseConnection.class, "em");
            setPrivateFieldToNull(DatabaseConnection.class, "emf");

            EntityManager em = DatabaseConnection.getConnection();
            assertNotNull(em, ENTITY_MANAGER_NOT_NULL);
        }
    }
}