package com.ktds.admin.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.ktds.admin.user.vo.UserSearchVO;
import com.ktds.admin.user.vo.UserVO;


public class UserDaoImpl implements UserDao {

	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "HEALTH";
	private static final String password = "health";

	@Override
	public List<UserVO> selectAllUser(UserSearchVO userSearchVO) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			StringBuffer query = new StringBuffer();
			query.append(" SELECT 	*	 ");
			query.append(" FROM 	(	 ");
			query.append("  		SELECT	ROWNUM AS RNUM	 ");
			query.append("  				, A.*		 ");
			query.append("  		FROM			  ( ");
			query.append(" 					SELECT		B.BOARD_ID ");
			query.append(" 								, B.WRT_DT ");
			query.append(" 								, AT.ATHRZTN_NM ");
			query.append(" 								, U.USR_NM ");
			query.append(" 					FROM		BOARD B ");
			query.append(" 								, ATHRZTN AT ");
			query.append(" 								, USR U ");
			query.append(" 					WHERE	B.USR_ID = U.USR_ID(+) ");
			query.append(" 					AND		B.ATHRZTN_ID = AT.ATHRZTN_ID(+) ");
			query.append(" 					 		) A ");
			query.append(" 			 WHERE	ROWNUM <= ? ");
			query.append(" 					 ) ");
			query.append(" 	WHERE	RNUM >= ? ");

			stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, userSearchVO.getPager().getEndArticleNumber());
			stmt.setInt(2, userSearchVO.getPager().getStartArticleNumber());
			rs = stmt.executeQuery();

			List<UserVO> boardList = new ArrayList<UserVO>();
			UserVO userVO = null;

			while (rs.next()) {

				userVO = new UserVO();
				userVO.setUserId(rs.getString("BOARD_ID"));
				userVO.setUserName(rs.getString("WRT_DT"));
				userVO.setUserName(rs.getString("USR_NM"));

				userVO.getAuthorizationVO().setAuthoriztaionName(rs.getString("ATHRZTN_NM"));

				boardList.add(userVO);
			}

			return boardList;

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {

			close(conn, stmt, rs);
		}
	}

	@Override
	public int insertNewUser(UserVO newUserVO) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			StringBuffer query = new StringBuffer();
			query.append(" INSERT		INTO	USR ( ");
			query.append(" 						USR_ID ");
			query.append(" 						, USR_NM ");
			query.append(" 						, USR_PWD ");
			query.append(" 						, ATHRZTN_ID ");
			query.append(" 						) ");
			query.append(" VALUES					( ");
			query.append(" 						? ");
			query.append(" 						, ? ");
			query.append(" 						, ? ");
			query.append(" 						, ? ");
			query.append(" 						) ");

			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, newUserVO.getUserId());
			stmt.setString(2, newUserVO.getUserName());
			stmt.setString(3, newUserVO.getUserPassword());
			stmt.setString(4, newUserVO.getAuthrizationId());

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {

			close(conn, stmt, null);
		}
	}

	@Override
	public int selectAllUserCount(UserSearchVO userSearchVO) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			StringBuffer query = new StringBuffer();
			query.append(" SELECT	COUNT(1) CNT      ");
			query.append(" FROM		USR	U            ");
			query.append(" 			, ATHZTN A         ");
			query.append(" WHERE	U.ATHZTN_ID = A.ATHZTN_ID ");
			query.append(" AND		ATHZTN_ID = ? ");

			stmt = conn.prepareStatement(query.toString());

			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("CNT");
			}

			return 0;

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, rs);
		}
	}

	@Override
	public UserVO selectOneUser(String userId) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			StringBuffer query = new StringBuffer();

			query.append(" SELECT	U.USR_ID ");
			query.append(" 			, U.USR_NM ");
			query.append(" 			, U.USR_PW ");
			query.append(" 			, U.ATHRZTN_ID  U_ATHRZTN_ID ");
			query.append(" 			, A.ATHRZTN_ID ");
			query.append(" 			, A.ATHRZTN_NM ");
			query.append(" 			, A.PRNT_ATHRZTN_ID ");
			query.append(" FROM		USR U ");
			query.append(" 			, ATHRZTN A ");
			query.append(" WHERE	U.ATHRZTN_ID = A.ATHRZTN_ID(+) ");
			query.append(" AND		U.USR_ID = ? ");

			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			UserVO userVO = null;

			if (rs.next()) {
				userVO = new UserVO();
				userVO.setUserId(rs.getString("USR_ID"));
				userVO.setUserName(rs.getString("USR_NM"));
				userVO.setUserPassword(rs.getString("USR_PWD"));
				userVO.setAuthorizationId(rs.getString("U_ATHRZTN_ID"));

				userVO.getAuthorizationVO().setAuthorizationId(rs.getString("ATHRZTN_ID"));
				userVO.getAuthorizationVO().setAuthoriztaionName(rs.getString("ATHRZTN_NM"));
				userVO.getAuthorizationVO().setPrntAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
			}

			return userVO;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, rs);
		}
	}

	@Override
	public UserVO selectOneUser(UserVO userVO) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			StringBuffer query = new StringBuffer();

			query.append("	SELECT			U.USR_ID		");
			query.append("					,		U.USR_NM          ");
			query.append("					,		U.USR_PWD         ");
			query.append("					,		U.ATHRZTN_ID	U_ATHRZTN_ID	");
			query.append("					,		A.ATHRZTN_ID         ");
			query.append("					,		A.ATHRZTN_NM         ");
			query.append("					,		A.PRNT_ATHRZTN_ID         ");
			query.append("	FROM			USR U        ");
			query.append("					,	ATHRZTN A        ");
			query.append("	WHERE			U.ATHRZTN_ID = A.ATHRZTN_ID(+)  ");
			query.append("	AND				U.ATHRZTN_ID = 'AT-2017032220-000010'  	");
			query.append("	AND				U.USR_ID = ?  	");
			query.append("	AND				U.USR_PWD = ?  	");

			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, userVO.getUserId());
			rs = stmt.executeQuery();

			if (rs.next()) {
				userVO.setUserId(rs.getString("USR_ID"));
				userVO.setUserName(rs.getString("USR_NM"));
				userVO.setUserPassword(rs.getString("USR_PWD"));
				userVO.setAuthorizationId(rs.getString("U_ATHRZTN_ID"));
				userVO.getAuthorizationVO().setAuthorizationId(rs.getString("ATHRZTN_ID"));
				userVO.getAuthorizationVO().setAuthoriztaionName(rs.getString("ATHRZTN_NM"));
				userVO.getAuthorizationVO().setPrntAuthorizationId(rs.getString("PRNT_ATHRZTN_ID"));
			}

			// Logger("user id = " + userVO.getUserId());

			return userVO;

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, rs);
		}
	}

	@Override
	public int updateUserInfo(UserVO userVO) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			StringBuffer query = new StringBuffer();

			query.append("		UPDATE 		USR           ");
			query.append("		SET		 	");
			query.append("					USR_PWD =	?	");
			query.append("					,	ATHRZTN_ID =	?	");
			query.append("		WHERE		USR_ID	=	?	");

			stmt = conn.prepareStatement(query.toString());

			// stmt.setString(1, userVO.getUserName());
			stmt.setString(1, userVO.getUserPassword());
			stmt.setString(2, userVO.getAuthorizationId());
			stmt.setString(3, userVO.getUserId());

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public int deleteOneUser(String userId) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);

			StringBuffer query = new StringBuffer();
			query.append(" DELETE ");
			query.append(" FROM		USR ");
			query.append(" WHERE	USR_ID = ? ");

			stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, userId);

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public int changeUser(String checkedUserId, String beforeAuthId, String afterAuthId) {

		loadOracle();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			StringBuffer query = new StringBuffer();

			query.append(" UPDATE	USR ");
			query.append(" SET 			");
			query.append("  			ATHRZTN_ID = ? ");

			if (checkedUserId != null && checkedUserId.length() > 0) {
				query.append(" WHERE 	USR_ID = ? ");
			} else {
				if (beforeAuthId == null || beforeAuthId.length() == 0) {
					query.append(" WHERE	ATHRZTN_ID	IS	NULL ");
				} else {
					query.append(" WHERE	ATHRZTN_ID = ?	");
				}
			}

			stmt = conn.prepareStatement(query.toString());

			if (afterAuthId == null || afterAuthId.length() == 0) {
				stmt.setNull(1, Types.VARCHAR);
			} else {
				stmt.setString(1, afterAuthId);
			}

			if (checkedUserId != null && checkedUserId.length() > 0) {
				stmt.setString(2, checkedUserId);
			} else if (beforeAuthId != null && beforeAuthId.length() > 0) {
				stmt.setString(2, beforeAuthId);
			}

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(conn, stmt, null);
		}
	}

	public void loadOracle() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void close(Connection conn, PreparedStatement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
}
