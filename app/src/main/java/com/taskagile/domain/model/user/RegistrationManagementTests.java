package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationManagementTests {
  private UserRepository repositoryMock;
  private PasswordEncryptor passwordEncryptorMock;
  private RegistrationManagement instance;

  @Before
  public void setUp() {
    repositoryMock = mock(UserRepository.class);
    passwordEncryptorMock = mock(PasswordEncryptor.class);
    instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
  }

  @Test(expected = UsernameExistsException.class)
  public void register_existedUsername_shouldFail() throws RegistrationException {
    String username = "existUsername";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";

    // 이미 존재하는 사용자임을 알려주고자 빈 객체를 반환한다.
    when(repositoryMock.findByUsername(username)).thenReturn(new User());
    instance.register(username, emailAddress, password);
  }

  @Test(expected = EmailAddressExistsException.class)
  public void register_existedEmailAddress_shouldFail() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "exist@taskagile.com";
    String password = "MyPassword!";
    // We just return an empty user object to indicate an existing user
    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
    instance.register(username, emailAddress, password);
  }

  @Test
  public void register_uppercaseEmailAddress_shouldSuccedAndBecomeLowercase() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "Sunny@TaskAgile.com";
    String password = "MyPassword!";

    instance.register(username, emailAddress, password);
    User userToSave = User.create(username, emailAddress.toLowerCase(), password);
    verify(repositoryMock).save(userToSave);
  }

  @Test
  public void register_newUser_shouldSucceed() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";
    String encryptedPassword = "EncryptedPassword";
    User newUser = User.create(username, emailAddress, encryptedPassword);

    // repository mock 설정
    // 사용자가 존재하지 않음을 나타내고자 null값 반환
    when(repositoryMock.findByUsername(username)).thenReturn(null);
    when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
    doNothing().when(repositoryMock).save(newUser);

    // passwordEncryptor mock 설정
    when(passwordEncryptorMock.encrypt(password))
      .thenReturn("EncryptedPassword");

    User savedUser = instance.register(username, emailAddress, password);
    InOrder inOrder = inOrder(repositoryMock);
    inOrder.verify(repositoryMock).findByUsername(username);
    inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
    inOrder.verify(repositoryMock).save(savedUser);
    verify(passwordEncryptorMock).encrypt(password);
    assertEquals("Saved user's password should be encrypted",
      encryptedPassword, savedUser.getPassword());
  }
}
