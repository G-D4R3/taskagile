package com.taskagile.domain.application.commands;

public class RegistrationCommand {
  private String username;
  private String emailAddress;
  private String password;

  public RegistrationCommand(String username, String emailAddress, String password) {
    this.username = username;
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPassword() {
    return password;
  }


  /** Registration Command는 equals와 hashCode 메소드를 가진다.
   * 이것은 테스트에서 모키토가 두 개의 RegistrationCommand 인스턴스를 비교할 때 두 메소드를 활용하기 때문이다.
   * 두 메소드가 없으면 인스턴스가 같은지 비교할 때 객체의 메모리 주소를 활용하며, 메모리 주소가 다르기 때문에 테스트는 실패한다.
   **/

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RegistrationCommand that = (RegistrationCommand) o;
    if (username != null ? !username.equals(that.username) : that.username != null) return false;
    if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
    return password != null ? password.equals(that.password) : that.password == null;
  }

  @Override
  public int hashCode() {
    int result = username != null ? username.hashCode() : 0;
    result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RegistrationCommand{" +
      "username='" + username + '\'' +
      ", emailAddress='" + emailAddress + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
