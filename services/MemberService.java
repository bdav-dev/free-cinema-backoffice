package services;

import java.sql.ResultSet;
import java.util.Optional;

import db.model.Member;
import db.utility.HasDatabaseAccess;
import exceptions.DatabaseException;

public class MemberService implements HasDatabaseAccess {
    private static MemberService instance;

    private MemberService() {
        super();
    }

    public static MemberService getInstance() {
        if (instance == null)
            instance = new MemberService();

        return instance;
    }


    public Optional<Member> getMemberFromAccountUsername(String username) throws DatabaseException {

        ResultSet resultSet = database().executeFetchResultSet(
                ps -> {
                    ps.setInt(1, database().mandt);
                    ps.setString(2, username);
                    ps.setInt(3, database().mandt);
                },
                """
                        SELECT m.*
                        FROM UserAccount AS ua
                        JOIN Member AS m
                            ON ua.memberID = m.memberID
                            AND ua.mandt = ?
                        WHERE ua.username = ?
                            AND m.mandt = ?
                        """);

        return database().single(resultSet, rs -> {
            Member m = new Member();

            m.setMemberID(resultSet.getInt("memberID"))
                    .setFirstName(resultSet.getString("firstName"))
                    .setLastName(resultSet.getString("lastName"))
                    .setDisplayName(resultSet.getString("displayName"))
                    .setActive(resultSet.getInt("active") == 1);

            return m;
        });
    }

}
