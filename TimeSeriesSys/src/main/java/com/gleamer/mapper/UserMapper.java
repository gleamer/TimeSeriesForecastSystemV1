package com.gleamer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gleamer.model.User;

@Mapper
public interface UserMapper {
	@Insert("insert into user (email,password,nickname,urole) values (#{email},#{password},#{nickname},#{urole})")
	int adminInsertUser(@Param("email") String email,@Param("password") String password,@Param("nickname") String nickname,@Param("urole") String urole);
	//int insertUser(User user);
	
	@Insert("insert into user (email,password,nickname,urole) values (#{email},#{password},#{nickname},'user')")
	int insertUserRegister(@Param("email") String email,@Param("password") String password,@Param("nickname") String nickname);
	
	@Delete("delete from user where email=#{email}")
	void deleteByEmail(@Param("email") String email);
	
	@Update("update user set password=#{password} where email=#{email}")
	void updateUserPassword(@Param("email") String email,@Param("password") String password);
	//void updateUserPassword(User user);
	
	@Update("update user set email=#{email},password=#{password},nickname=#{nickname},urole=#{urole} where uid=#{uid}")
	void adminUpdateUser(@Param("email") String email,@Param("password") String password,@Param("nickname") String nickname,@Param("urole") String urole,@Param("uid") int uid);
	
	
	@Select("select * from user where email=#{email}")
	User selectUserByEmail(@Param("email") String email);
	
	@Select("select * from user where email=#{email} and password=#{password}")
	User selectUserByEmailAndPassword(@Param("email") String email,@Param("password") String password);
	
	@Select("select * from user")
	List<User> selectUserByAdmin();
	
	
}
