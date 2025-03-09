package DataSource;

import Model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    private UserDAO userDAO;
    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);

        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        try (MockedStatic<DatabaseConnection> mockedStatic = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockEntityManager);
        }
    }

    @Test
    void testCreateUser() {
        User user = new User();
        userDAO.createUser(user);

        verify(mockEntityManager).persist(user);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        userDAO.updateUser(user);

        verify(mockEntityManager).merge(user);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        when(mockEntityManager.find(User.class, 1)).thenReturn(user);

        userDAO.deleteUser(1);

        verify(mockEntityManager).remove(user);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testGetUserById() {
        User user = new User();
        when(mockEntityManager.find(User.class, 1)).thenReturn(user);

        User result = userDAO.getUserById(1);

        assertEquals(user, result);
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("username", "testuser")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(user);

        User result = userDAO.getUserByUsername("testuser");

        assertEquals(user, result);
    }
}