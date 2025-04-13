package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    private UserDAO userDAO;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        entityManager = mock(EntityManager.class);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

            boolean result = userDAO.createUser(user);

            assertTrue(result);
            verify(entityManager).persist(user);
            verify(entityManager.getTransaction()).begin();
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

            boolean result = userDAO.updateUser(user);

            assertTrue(result);
            verify(entityManager).merge(user);
            verify(entityManager.getTransaction()).begin();
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
            when(entityManager.find(User.class, 1)).thenReturn(user);

            boolean result = userDAO.deleteUser(1);

            assertTrue(result);
            verify(entityManager).find(User.class, 1);
            verify(entityManager).remove(user);
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testGetUserById() {
        User user = new User();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.find(User.class, 1)).thenReturn(user);

            User result = userDAO.getUserById(1);

            assertNotNull(result);
            assertEquals(user, result);
            verify(entityManager).find(User.class, 1);
        }
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            TypedQuery<User> query = mock(TypedQuery.class);
            when(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
            when(query.setParameter("username", "testUser")).thenReturn(query);
            when(query.getSingleResult()).thenReturn(user);

            User result = userDAO.getUserByUsername("testUser");

            assertNotNull(result);
            assertEquals(user, result);
            verify(query).setParameter("username", "testUser");
            verify(query).getSingleResult();
        }
    }
}