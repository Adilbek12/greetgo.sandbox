package kz.greetgo.sandbox.db.test.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface MigrationTestDao {
  @Insert("INSERT INTO ${tmpClientTableName} " +
    "VALUES (#{recordNo}, #{ciaId}, #{id}, #{surname}, #{name}, #{patronymic}, #{gender}, #{charmName}," +
    "#{birthDate}, #{status}, #{error})")
  void insertClient(@Param("tmpClientTableName") String tmpClientTableName,
                    @Param("recordNo") long recordNo,
                    @Param("ciaId") String ciaId,
                    @Param("id") Long id,
                    @Param("surname") String surname,
                    @Param("name") String name,
                    @Param("patronymic") String patronymic,
                    @Param("gender") String gender,
                    @Param("charmName") String charm_name,
                    @Param("birthDate") Date birth_date,
                    @Param("status") int status,
                    @Param("error") String error);

  @Insert("INSERT INTO ${tmpClientAddressTableName} " +
    "VALUES (#{recordNo}, #{clientRecordNo}, #{type}, #{street}, #{house}, #{flat})")
  void insertClientAddress(@Param("tmpClientAddressTableName") String tmpClientAddressTableName,
                           @Param("recordNo") long recordNo,
                           @Param("clientRecordNo") long clientRecordNo,
                           @Param("type") String type,
                           @Param("street") String street,
                           @Param("house") String house,
                           @Param("flat") String flat);

  @Insert("INSERT INTO ${tmpClientPhoneTableName} " +
    "VALUES (#{recordNo}, #{clientRecordNo}, #{number}, #{type}, #{status})")
  void insertClientPhone(@Param("tmpClientPhoneTableName") String tmpClientPhoneTableName,
                         @Param("recordNo") long recordNo,
                         @Param("clientRecordNo") long clientRecordNo,
                         @Param("number") String number,
                         @Param("type") String type,
                         @Param("status") int status);

  @Insert("INSERT INTO ${tmpClientAccountTableName} " +
    "VALUES (#{recordNo}, #{ciaId}, nextval('client_account_id_seq'), #{clientId}, #{money}, #{accountNumber}, #{registeredAt}, #{status}, #{error})")
  void insertClientAccount(@Param("tmpClientAccountTableName") String tmpClientAccountTableName,
                           @Param("recordNo") long recordNo,
                           @Param("ciaId") String ciaId,
                           @Param("clientId") Long clientId,
                           @Param("money") BigDecimal money,
                           @Param("accountNumber") String accountNumber,
                           @Param("registeredAt") Timestamp registeredAt,
                           @Param("status") int status,
                           @Param("error") String error);

  @Insert("INSERT INTO ${tmpTableName} " +
    "VALUES (#{recordNo}, nextval('client_account_transaction_id_seq'), #{accountId}, #{money}, #{finishedAt}, #{transactionType}, #{accountNumber}, #{status}, #{error})")
  void insertClientAccountTransaction(@Param("tmpTableName") String tmpTableName,
                                      @Param("recordNo") long recordNo,
                                      @Param("accountId") Long accountId,
                                      @Param("money") BigDecimal money,
                                      @Param("finishedAt") Timestamp finishedAt,
                                      @Param("transactionType") String transactionType,
                                      @Param("accountNumber") String accountNumber,
                                      @Param("status") int status,
                                      @Param("error") String error);

  @Delete("DELETE FROM charm WHERE id >= 16")
  void deleteAllTableCharm();

  @Delete("DELETE FROM client")
  void deleteAllTableClient();

  @Delete("DELETE FROM client_addr")
  void deleteAllTableClientAddr();

  @Delete("DELETE FROM client_phone")
  void deleteAllTableClientPhone();

  @Delete("DELETE FROM client_account")
  void deleteAllTableClientAccount();

  @Delete("DELETE FROM transaction_type")
  void deleteAllTableTransactionType();
}